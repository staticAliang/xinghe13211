package com.fengshen.web.interceptor;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ManageInterceptorAdapter implements WebMvcConfigurer {
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		log.info("拦截-所有权限");
		registry.addInterceptor(new ManageCoreAuthorityInterceptor()).addPathPatterns("/**")
				.excludePathPatterns("/sys/login.html","/sys/user/logout", "/sys/user/login", "/static/**");		
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations(
				"file:./static/","classpath:/classes/static/","classpath:/static/");
	}
}
