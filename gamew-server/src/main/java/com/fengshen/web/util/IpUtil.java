package com.fengshen.web.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IpUtil {
	private static final Logger logger;
	
	static {
		logger = LoggerFactory.getLogger(IpUtil.class);
	}

	public static String client(final HttpServletRequest request) {
		String xff = request.getHeader("x-forwarded-for");
		if (xff == null) {
			xff = request.getRemoteAddr();
		}
		return xff;
	}

	public static String getIpAddr(final HttpServletRequest request) {
		String ipAddress = null;
		try {
			ipAddress = request.getHeader("x-forwarded-for");
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
				if (ipAddress.equals("127.0.0.1")) {
					InetAddress inet = null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						IpUtil.logger.error("", (Throwable) e);
					}
					ipAddress = inet.getHostAddress();
				}
			}
			if (ipAddress != null && ipAddress.length() > 15 && ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		} catch (Exception e2) {
			ipAddress = "";
		}
		return ipAddress;
	}
}
