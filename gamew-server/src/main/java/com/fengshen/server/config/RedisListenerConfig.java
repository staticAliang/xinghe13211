package com.fengshen.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.TaskManagementConfigUtils;

/**
 * redis配置
 * 
 *
 */
@Configuration
public class RedisListenerConfig {

	@Bean
	public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }
	
	@Bean(name = TaskManagementConfigUtils.ASYNC_ANNOTATION_PROCESSOR_BEAN_NAME)
	public AsyncAnnotationBeanPostProcessor myAsyncAdvisor() {
	    AsyncAnnotationBeanPostProcessor asyncAdvisor = new AsyncAnnotationBeanPostProcessor();
	    asyncAdvisor.setExceptionHandler((ex, method, params) -> {
	    	ex.printStackTrace();
	    });
	    return asyncAdvisor;
	}
}
