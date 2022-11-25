package com.innotium.npouch.configuration;

import static java.util.stream.Collectors.toList;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.google.common.collect.Lists;
import com.innotium.npouch.annotation.LoginNotRequire;
import com.innotium.npouch.dto.DtoRole;
import com.innotium.npouch.model.enums.UserRole;
import com.innotium.npouch.service.CustomAuthenticationProvider;

@Configuration
@EnableWebMvc
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Value("${authInfo.headerKey}")
	private String headerKey;

	@Value("${authInfo.headerId}")
	private String headerId;
	
	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;
	
	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		List<String> loginNotRequireURLs = getAnnotatedResources(LoginNotRequire.class);
		http.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
			.and()
		.cors()
			.and()
		.authorizeRequests()
			.antMatchers(loginNotRequireURLs.toArray(new String[loginNotRequireURLs.size()])).permitAll()
			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			.antMatchers("/api/account/login").permitAll()
			//.antMatchers("/api/**").hasAnyAuthority(UserRole.MANAGER.getName(), UserRole.GROUP.getName(), UserRole.USER.getName())
			.antMatchers("/api/**").permitAll()
			.anyRequest().authenticated();
	}
	
	// CORS 허용 적용
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private List<String> getAnnotatedResources(Class annotation) {
		List<String> urlList = Lists.newArrayList();
		Map<RequestMappingInfo, HandlerMethod> methods = requestMappingHandlerMapping.getHandlerMethods();
		for (Map.Entry<RequestMappingInfo, HandlerMethod> item : methods.entrySet()) {
			RequestMappingInfo mappingInfo = item.getKey();
			HandlerMethod handlerMethod = item.getValue();

			Method method = handlerMethod.getMethod();
			Class clazz = method.getDeclaringClass();
			
			if (clazz.isAnnotationPresent(annotation) || method.isAnnotationPresent(annotation)) {
				urlList.addAll(mappingInfo.getPatternsCondition().getPatterns());
			}
		}

		return urlList.stream().map(x -> x.replaceAll("\\{.*?\\}", "*")).collect(toList());
	}

	/* custom auth */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthenticationProvider);
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public HeaderHttpSessionIdResolver headerHttpSessionIdResolver() {
		return new HeaderHttpSessionIdResolver(headerKey);
	}
}
