package com.fengshen.db.redis;

import org.springframework.cache.annotation.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.connection.lettuce.*;
import org.springframework.data.redis.connection.*;
import java.lang.reflect.*;
import org.springframework.cache.*;
import org.springframework.data.redis.serializer.*;
import java.time.*;
import org.springframework.data.redis.cache.*;
import org.springframework.data.redis.core.*;
import org.springframework.cache.interceptor.*;
import java.io.*;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {
	@Bean
	public RedisSerializer<Object> jackson2JsonRedisSerializer() {
		final Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer((Class) Object.class);
		final ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		serializer.setObjectMapper(mapper);
		return (RedisSerializer<Object>) serializer;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(final LettuceConnectionFactory lettuceConnectionFactory) {
		final RedisTemplate<String, Object> template = (RedisTemplate<String, Object>) new RedisTemplate();
		final StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		template.setKeySerializer((RedisSerializer) stringRedisSerializer);
		template.setHashKeySerializer((RedisSerializer) stringRedisSerializer);
		template.setDefaultSerializer((RedisSerializer) this.jackson2JsonRedisSerializer());
		template.setConnectionFactory((RedisConnectionFactory) lettuceConnectionFactory);
		template.afterPropertiesSet();
		return template;
	}

	@Bean
	public StringRedisTemplate stringRedisTemplate(final LettuceConnectionFactory lettuceConnectionFactory) {
		final StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory((RedisConnectionFactory) lettuceConnectionFactory);
		return template;
	}

	@Bean
	public SimpleKeyGenerator keyGenerator() {
		return new SimpleKeyGenerator() {
			public Object generate(final Object target, final Method method, final Object... params) {
				final StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(".").append(method.getName());
				final StringBuilder paramsSb = new StringBuilder();
				for (final Object param : params) {
					if (param != null) {
						paramsSb.append(param.toString());
					}
				}
				if (paramsSb.length() > 0) {
					sb.append("_").append((CharSequence) paramsSb);
				}
				return sb.toString();
			}
		};
	}

	@Bean
	public CacheManager cacheManager(final LettuceConnectionFactory lettuceConnectionFactory) {
		final Jackson2JsonRedisSerializer<Object> redisSerializer = (Jackson2JsonRedisSerializer<Object>) new Jackson2JsonRedisSerializer(
				(Class) Object.class);
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		redisSerializer.setObjectMapper(objectMapper);
		final RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(
						RedisSerializationContext.SerializationPair.fromSerializer((RedisSerializer) redisSerializer));
		cacheConfiguration.entryTtl(Duration.ofDays(1L));
		final RedisCacheManager redisCacheManager = RedisCacheManager
				.builder((RedisConnectionFactory) lettuceConnectionFactory).cacheDefaults(cacheConfiguration).build();
		return (CacheManager) redisCacheManager;
	}

	@Bean
	public HashOperations hashOperations(final RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForHash();
	}
}
