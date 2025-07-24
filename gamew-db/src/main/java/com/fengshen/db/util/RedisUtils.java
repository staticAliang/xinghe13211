package com.fengshen.db.util;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RedisUtils {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	public static long DEFAULT_EXPIRE = 86400L;
	public static long NOT_EXPIRE = -1L;

	public void set(String key, Object object) {
		this.set(key, object, 86400L);
	}
	
	public void set(String key, String object) {
		redisTemplate.opsForValue().set(key, object);
	}
	
	public void set(String key, String object, long expire) {
		try {
			ValueOperations<String, String> opsForValue = (ValueOperations<String, String>) this.redisTemplate
					.opsForValue();
			if (expire == -1L) {
				opsForValue.set( key, object);
			} else {
				opsForValue.set(key, object, expire, TimeUnit.SECONDS);
			}
		} catch (Exception e) {
		}
	}
	
	public void set(String key, String object, long expire, TimeUnit time) {
		try {
			ValueOperations<String, String> opsForValue = (ValueOperations<String, String>) this.redisTemplate
					.opsForValue();
			if (expire == -1L) {
				opsForValue.set( key, object);
			} else {
				opsForValue.set(key, object, expire, time);
			}
		} catch (Exception e) {
		}
	}

	public void set(String key, Object object, long expire) {
		try {
			ValueOperations<String, String> opsForValue = (ValueOperations<String, String>) this.redisTemplate
					.opsForValue();
			if (expire == -1L) {
				opsForValue.set((String) key, (String) this.toJson(object));
			} else {
				opsForValue.set((String) key, (String) this.toJson(object), expire, TimeUnit.SECONDS);
			}
		} catch (Exception e) {
		}
	}

	public void setSecond(String key, Object object, long expire) {
		try {
			ValueOperations<String, String> opsForValue = (ValueOperations<String, String>) this.redisTemplate
					.opsForValue();
			if (expire == -1L) {
				opsForValue.set((String) key, (String) this.toJson(object));
			} else {
				opsForValue.set((String) key, (String) this.toJson(object), expire, TimeUnit.MILLISECONDS);
			}
		} catch (Exception e) {
		}
	}

	public <T> T get(String key, Class<T> clazz, long expire) {
		ValueOperations<String, String> opsForValue = (ValueOperations<String, String>) this.redisTemplate
				.opsForValue();
		String value = (String) opsForValue.get((Object) key);
		if (expire != -1L) {
			this.redisTemplate.expire((String) key, expire, TimeUnit.SECONDS);
		}
		return (value == null) ? null : this.fromJson(value, clazz);
	}

	public <T> T get(String key, Class<T> clazz) {
		return this.get(key, clazz, -1L);
	}

	public String get(String key, long expire) {
		ValueOperations<String, String> opsForValue = (ValueOperations<String, String>) this.redisTemplate
				.opsForValue();
		String value = (String) opsForValue.get((Object) key);
		if (expire != -1L) {
			this.redisTemplate.expire((String) key, expire, TimeUnit.SECONDS);
		}
		return value;
	}

	public String get(String key) {
		return this.get(key, -1L);
	}

	public void delete(String key) {
		this.redisTemplate.delete((String) key);
	}
	public void deleteAll() {
		Set<String> keys = redisTemplate.keys("*");
		this.redisTemplate.delete(keys);
	}

	private String toJson(Object object) throws JsonProcessingException {
		if (object instanceof Integer || object instanceof Long || object instanceof Float || object instanceof Double
				|| object instanceof Boolean || object instanceof String) {
			return String.valueOf(object);
		}
		return JSONObject.toJSONString(object);
	}

	private <T> T fromJson(String json, Class<T> clazz) {
		return JSONObject.parseObject(json, clazz);
	}

	/**
	 * @Description: 获取自增长值
	 * @param key key
	 * @return
	 */
	public int getIncr(String key) {
		if (get(key) == null) {
			setIncr(key, 10);
		}
		RedisAtomicInteger entityIdCounter = new RedisAtomicInteger(key, redisTemplate.getConnectionFactory());
		int increment = entityIdCounter.incrementAndGet();
		return increment;
	}

	/**
	 * @Description: 获取自增长值
	 * @param key key
	 * @return
	 */
	public int getIncr2(String key) {
		if (get(key) == null) {
			setIncr(key, 0);
		}
		RedisAtomicInteger entityIdCounter = new RedisAtomicInteger(key, redisTemplate.getConnectionFactory());
		int increment = entityIdCounter.incrementAndGet();
		return entityIdCounter==null?-1:increment;
	}
	
	public void setHash(String key, String hashKey, Object value) {
		HashOperations<String, Object, Object> opsForValue = this.redisTemplate
				.opsForHash();
		opsForValue.put(key, hashKey, value);
	}
	
	public String getHash(String key, String hashKey) {
		HashOperations<String, Object, Object> opsForValue = this.redisTemplate
				.opsForHash();
		Object object = opsForValue.get(key, hashKey);
		return object == null?null:object.toString();
	}
	
	public List<Object> getHashs(String key) {
		HashOperations<String, Object, Object> opsForValue = this.redisTemplate
				.opsForHash();
		List<Object> multiGet = opsForValue.values(key);
		return multiGet;
	}
	
	public HashOperations<String, Object, Object> getHashObj() {
		HashOperations<String, Object, Object> opsForValue = this.redisTemplate
				.opsForHash();
		return opsForValue;
	}
	
	/**
	 * 批量获取value
	 * 
	 * @param prefix 前缀
	 * @return
	 */
	public List<String> getListKey(String prefix) {
		Set<String> keys = redisTemplate.keys("*");
		List<String> values = redisTemplate.opsForValue().multiGet(keys);
		return values;
	}

	/**
	 * @Description: 初始化自增长值
	 * @param key   key
	 * @param value 当前值
	 */
	public void setIncr(String key, int value) {
		RedisAtomicInteger counter = new RedisAtomicInteger(key, redisTemplate.getConnectionFactory());
		counter.set(value);
	}

	public Set<String> getKeys(String key) {
		Set<String> keys = redisTemplate.keys(key);

		return keys;
	}

	@Bean
	public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
				Object.class);
		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
		objectMapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		return jackson2JsonRedisSerializer;
	}
}
