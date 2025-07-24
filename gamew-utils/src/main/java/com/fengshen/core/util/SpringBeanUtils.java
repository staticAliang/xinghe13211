package com.fengshen.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 
* @ClassName: SpringBeanUtils 
* @Description: 手动获取Bean的方法
* @author 彭联伟
* @since 1.0
 */
@Service
public class SpringBeanUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	
	public static Object getBean(String beanName) {
		Object obj = applicationContext.getBean(beanName);
		return obj;
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationcontext) throws BeansException {
		
		SpringBeanUtils.applicationContext = applicationcontext;
		
	}
	
	/**
	 * 根据类对象获取实例
	 * @param classzz
	 * @return T 任意对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<?> classzz) {
		
		return (T) applicationContext.getBean(classzz);
	}
}
