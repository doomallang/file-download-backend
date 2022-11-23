package com.innotium.npouch.configuration.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConnection;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@Configuration
@EnableRedisHttpSession
public class RedisConfig {

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private Integer port;

	@Value("${spring.redis.password}")
	private String password;

	@Value("${spring.redis.database}")
	private Integer database;

	@Value("${spring.redis.timeout}")
	private Integer timeout;

	@Value("${server.session.timeout}")
	private Integer sessionTimeout;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisStandardaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandardaloneConfiguration.setHostName(host);
		redisStandardaloneConfiguration.setPort(port);
		redisStandardaloneConfiguration.setPassword(password);
		
		return new LettuceConnectionFactory(redisStandardaloneConfiguration);
	}
}