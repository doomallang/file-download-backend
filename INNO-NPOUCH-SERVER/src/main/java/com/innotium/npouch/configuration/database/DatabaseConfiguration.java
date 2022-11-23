package com.innotium.npouch.configuration.database;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(basePackages= {"com.innotium.npouch.repository"}, sqlSessionFactoryRef="sqlSessionFactory")
@EnableTransactionManagement
public class DatabaseConfiguration {
	private final String TYPE_ALIASES_PACKAGE = "com.innotium.npouch.model, com.innotium.npouch.dto";
	
	@Bean(name = "hikariConfig")
	@ConfigurationProperties(prefix="datasource.mariadb")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	@Bean(name = "dataSource")
	@Primary
	public DataSource dataSource() {
		HikariConfig hikariConfig = hikariConfig();
		try {
			hikariConfig.setPassword(hikariConfig.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("=========== DATA SOURCE ERROR [MAINDB]");
		}

		return new HikariDataSource(hikariConfig());	
	}
	
	@Bean(name = "sqlSessionFactory")
	@Primary
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource DataSource, ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
		sqlSessionFactoryBean.setDataSource(DataSource);
		org.apache.ibatis.session.Configuration prop = new org.apache.ibatis.session.Configuration();
		prop.setMapUnderscoreToCamelCase(true);
		prop.setLazyLoadingEnabled(true);
		prop.setUseGeneratedKeys(true);
		sqlSessionFactoryBean.setConfiguration(prop);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:repository/mysql/*.xml"));
		sqlSessionFactoryBean.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);

		return sqlSessionFactoryBean.getObject();
	}

	@Bean(name = "sqlSessionTemplate")
	@Primary
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
