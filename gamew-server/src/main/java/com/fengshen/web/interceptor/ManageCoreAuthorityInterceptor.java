package com.fengshen.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fengshen.web.controller.BaseController;
import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.sys.SysUser;
import com.fengshen.server.util.GameConfig;


/**
 * 核心权限拦截器-->
 * 	拦截所有用户请求,除了登录和退出
 *	当用户没有此权限直接返回401
 */
public class ManageCoreAuthorityInterceptor implements HandlerInterceptor {
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mv)
			throws Exception {
	
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		HttpSession session = request.getSession();
		SysUser user = (SysUser) session.getAttribute("user");
		String path = GameConfig.path;
		if(user == null) {
			response.sendRedirect(path+"/sys/login.html");
			return false;
		}
		if(!"0".equals(user.getAllowLoginIp())) {
			String ip = BaseController.getIP(request);
			String[] allowLoginIp = user.getAllowLoginIp().split(",");
			boolean isAllowLogin = false;
			for(String ips:allowLoginIp) {
				if(ip.equals(ips)) {
					isAllowLogin = true;
				}
			}
			if(!isAllowLogin) {
				ResponseView.unauthorized("无权访问");
			}
		}
		return true;
	}
	

}