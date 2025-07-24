package com.fengshen.web.util;

import javax.servlet.Servlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

public class ServletConfig {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		return new ServletRegistrationBean((Servlet) new ValidateCodeServlet(), new String[] { "/validateCode" });
	}
}
