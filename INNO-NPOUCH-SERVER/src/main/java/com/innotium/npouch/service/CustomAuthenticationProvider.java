package com.innotium.npouch.service;

import java.util.Collection;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.innotium.npouch.dto.AuthAccount;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		AuthAccount authAccount;
		String username = authentication.getName();
		String password = "";
		password = Objects.toString(authentication.getCredentials()).trim();
		
		Collection<? extends GrantedAuthority> authorities;
		try {
			authAccount = (AuthAccount)customUserDetailsService.loadUserByUsername(username);
			if (!password.equals(authAccount.getPassword())) {
				throw new BadCredentialsException("SERVER.MESSAGE.NO_DATA_MATCHES_THE_INFOMATION_YOU_ENTERED");
			}
			authorities = authAccount.getAuthorities();
		} catch (UsernameNotFoundException e) {
			throw new UsernameNotFoundException(e.getMessage());
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		authorities = authAccount.getAuthorities();
		
		return new UsernamePasswordAuthenticationToken(authAccount, password, authorities);
	}

	@Override
	public boolean supports(Class<?> arg0) {

		return true;
	}
}
