package com.fengshen.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.ErrorCode;
import com.fengshen.core.util.FieldFilterUtil;
import com.fengshen.core.util.ResponseView;
import com.fengshen.core.util.Utils;
import com.fengshen.db.domain.Daili;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.util.GameConfig;
import com.github.pagehelper.PageInfo;
import com.google.common.io.Files;

@Component
public class BaseController {

	
	protected Timer timer;
	

	/**
	 * springMVC 获取requset
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		
		return request;
	}

	/**
	 * 获取response
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse() {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getResponse();
		return response;
	}

	/**
	 * 获取session
	 * 
	 * @return
	 */
	public HttpSession getSession() {
		HttpSession session = this.getRequest().getSession();
		return session;
	}

	/**
	 * 获取ServletContext
	 * 
	 * @return
	 */
	public ServletContext getServletContent() {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		return servletContext;
	}
	
	/**
	 * 获取ip
	 * 
	 * @return
	 */
	public static String getRemortIP(HttpServletRequest request) {
	    return getIP(request);
	}

	public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (!checkIP(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (!checkIP(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!checkIP(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    private static boolean checkIP(String ip) {
        if (ip == null || ip.length() == 0 || "unkown".equalsIgnoreCase(ip)
                || ip.split(".").length != 4) {
            return false;
        }
        return true;
    }
	
	/**
	 * 获取port
	 * 
	 * @return
	 */
	public int getPort() {
		return this.getRequest().getServerPort();
	}


	/**
	 * 设置分页
	 * 
	 * @param pageInfo
	 *            分页详情
	 * @return 结果集包含一些分页信息和数据
	 */
	protected Map<String, Object> settingsPage(@SuppressWarnings("rawtypes") PageInfo pageInfo) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", pageInfo.getTotal());
		resultMap.put("pageSize", pageInfo.getPageSize());
		if (pageInfo.getList() == null) {
			resultMap.put("resultData", new ArrayList<Object>());
		} else {
			resultMap.put("resultData", pageInfo.getList());
		}
		resultMap.put("currentPageNum", pageInfo.getPageNum());
		return resultMap;
	}

	/**
	 * 设置分页
	 * 
	 * @param pageInfo
	 *            分页详情
	 * @param fields
	 *            需要传入前端的JSON字段
	 * @return 结果集包含一些分页信息和数据
	 */
	protected Map<String, Object> settingsPage(@SuppressWarnings("rawtypes") PageInfo pageInfo, String... fields) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", pageInfo.getTotal());
		resultMap.put("pageSize", pageInfo.getPageSize());
		if (pageInfo.getList() == null) {
			resultMap.put("resultData", new ArrayList<Object>());
		} else {
			resultMap.put("resultData", pageInfo.getList());
		}
		resultMap.put("currentPageNum", pageInfo.getPageNum());
		if (pageInfo.getList() != null) {
			FieldFilterUtil.includeFields(pageInfo.getList(), fields);
		}
		return resultMap;
	}

	/**
	 * 设置分页
	 * 
	 * @param pageInfo
	 *            分页详情
	 * @param fields
	 *            需要过滤的JSON字段
	 * @return 结果集包含一些分页信息和数据
	 */
	protected Map<String, Object> settingsPageFilter(@SuppressWarnings("rawtypes") PageInfo pageInfo,
			String... fields) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", pageInfo.getTotal());
		resultMap.put("pageSize", pageInfo.getPageSize());
		if (pageInfo.getList() == null) {
			resultMap.put("resultData", new ArrayList<Object>());
		} else {
			resultMap.put("resultData", pageInfo.getList());
		}
		resultMap.put("currentPageNum", pageInfo.getPageNum());
		if (pageInfo.getList() != null) {
			FieldFilterUtil.filterField(pageInfo.getList(), fields);
		}
		return resultMap;
	}

	/**
	 * 设置分页
	 * @param pageInfo 分页对象
	 * @param rv 返回统一类对象
	 * @since 1.0.0
	 */
	protected void settingsPage(@SuppressWarnings("rawtypes") PageInfo pageInfo, ResponseView rv) {
		if(pageInfo.getTotal() > 0) {
			rv.put("total", pageInfo.getTotal());
			rv.put("pageSize", pageInfo.getPageSize());
			rv.put("currentPageNum", pageInfo.getPageNum());
		}
	}
	/**
	 * 设置分页
	 * @param pageInfo 分页信息
	 * @param rv 返回统一类对象
	 * @param includeField 需要返回的字段
	 * @since 1.0.1
	 */
	protected void settingsPage(@SuppressWarnings("rawtypes") PageInfo pageInfo, ResponseView rv, String... filterField) {
		if(pageInfo.getTotal() > 0) {
			rv.put("total", pageInfo.getTotal());
		}
		rv.put("resultData", pageInfo.getList() == null ? new ArrayList<>():pageInfo.getList());
		FieldFilterUtil.filterField(rv, filterField);
	}

	/**
	 * 从缓存中取出当前用户的ID
	 * 
	 * @return 用户id
	 */
//	protected synchronized Integer getCurrentUserId() {
//		String token = getToken();
//		
//		String userId = rs.get(token);
//		
//		if(token != null) {
//			if(userId == null) {
//				ResponseView.fail(ErrorCode.E1002);
//			}
//		}
//		return Integer.valueOf(userId);
//	}
	
	/**
	 * 获取当前用户id
	 * @param token 凭证
	 * @return
	 */
	protected synchronized Integer getCurrentUserId() {
		
		Daili daili = (Daili) getRequest().getSession().getAttribute("daili");
		return daili.getId();
	}
	
	protected synchronized Daili getCurrentUser() {
		
		Daili daili = (Daili) getRequest().getSession().getAttribute("daili");
		return daili;
	}
	
	protected synchronized String getToken() {
		//从session获取token
		String activeShopToken = (String) getRequest().getSession().getAttribute("accessToken");
		//从参数中获取token
		String authToken = getRequest().getParameter("accessToken");
		//从head头获取token
		String header = getRequest().getHeader("accessToken");
		try {
			if(activeShopToken != null) {
				return activeShopToken;
			}
			if(authToken != null) {
				return authToken;
			}
			return header;
		} catch (Exception e) {
			ResponseView.fail(ErrorCode.E106);
		}
		return "";
	}
	
	/**
	 * 发送验证码
	 * @param rv 返回组件类
	 * @param phone 手机号码
	 */
//	protected void sendSms(ResponseView rv, String phone, String prefix) {
//		if (phone == null) {
//			ResponseView.fail(ErrorCode.E10110);
//		} else if (!Utils.isMobileNO(phone)) {
//			ResponseView.fail(ErrorCode.E10116);
//		}
//		int code = 123456;
//		if("prod".equals(runType)) { //真实环境才发送验证码
//			//优先去缓存中读取,是否有测试账号.
//			List<String> testAccount = rs.getList("account");
//			boolean isTestAccount = false;
//			for(String p:testAccount) {
//				if(phone.equals(p)) {
//					//发送的手机号为测试账号
//					isTestAccount = true;
//					break;
//				}
//			}
//			//不为测试账号的时候,才发送验证码.
//			if(!isTestAccount)  {
//				code = (int) ((Math.random() * 9 + 1) * 100000); //生成验证码.
//				AliSmsUtil.sendSms(code, phone);
//			}
//		}
//		// 生成token
//		Map<String, Object> resultMap = new HashMap<>();
//		resultMap.put("smsToken", RedisToken.createSmsToken(phone, code, prefix));
//		rv.put("data", resultMap);
//		rv.put("retmsg", "发送成功");
//	}
	
	protected Chara getChara(String name) {
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(name);
		if(gameObjectChar == null) {
			ResponseView.fail("该玩家不在线");
		}
		Chara thisChara = gameObjectChar.chara;
		return thisChara;
	}
	
	protected GameObjectChar getGameObjectChar(String name) {
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(name);
		if(gameObjectChar == null) {
			ResponseView.fail("该玩家不在线");
		}
		return gameObjectChar;
	}
	
	
	protected GameObjectChar getCharaByGid(String gid) {
		return GameObjectCharMng.getGameObjectCharByUUid(gid);
	}
	
	protected void flushConfig() {
		//重新设置
		String json = JSONObject.toJSONString(GameConfig.config);
		File resFile = Utils.getResFile("config.json");
		try {
			Files.write(json.getBytes("utf-8"), resFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 刷新配置文件
	 * @param name
	 */
	protected void flushConfig(String name, Object obejct) {
		//重新设置
		String json = JSONObject.toJSONString(obejct);
		File resFile = Utils.getResFile(name);
		try {
			Files.write(json.getBytes("utf-8"), resFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}