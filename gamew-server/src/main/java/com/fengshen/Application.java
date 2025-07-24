package com.fengshen;

import java.lang.reflect.Method;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.fastjson.JSON;
import com.fengshen.core.util.Utils;
import com.fengshen.server.auth.LoginAuth;

@SpringBootApplication(scanBasePackages = { "com.fengshen" })
@EnableTransactionManagement(proxyTargetClass = true)
@EnableScheduling
@EnableAsync
@ServletComponentScan
@EnableCaching
public class Application {

	public static void main(final String[] args) throws Exception {
		//如果服务器是linux就直接运行
		System.out.println(Utils.getLocalMac());
		if(System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0) {
			SpringApplication.run(Application.class, args);
		}else {
			new LoginAuth().run();
		}
	}

	@Bean(name = "cacheAutoKey")
	public KeyGenerator myKeyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				sb.append("&");
				for (Object obj : params) {
					if (obj != null) {
						sb.append(obj.getClass().getName());
						sb.append("&");
						sb.append(JSON.toJSONString(obj));
						sb.append("&");
					}
				}
				return DigestUtils.sha256Hex(sb.toString());
			}
		};
	}
}
