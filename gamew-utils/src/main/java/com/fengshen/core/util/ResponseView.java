package com.fengshen.core.util;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;

import com.fengshen.core.exception.SuperException;

/**
 * 
 * @ClassName: ResponseView
 * @Description: 统一JSON返回类
 * @author 彭联伟
 *
 */
public class ResponseView extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;
	
	private static String retmsg = "请求成功";
	
	public static ResponseView ok(ResponseView rv) {
		init(rv);
		return rv;
	}
	public static ResponseView ok(Object data) {
		ResponseView rv = new ResponseView();
		if(data != null) {
			rv.put("data", data);
		}
		init(rv);
		return rv;
	}
	
	public static ResponseView ok(String retmsg) {
		ResponseView rv = new ResponseView();
		rv.put("retmsg", retmsg);
		initPassMessage(rv);
		return rv;
	}
	
	public static ResponseView ok() {
		ResponseView rv = new ResponseView();
		initPass(rv);
		return rv;
	}

	public static ResponseView fail(ResponseView rv) {
		initFial(rv);
		return rv;
	}
	
	public static ResponseView fail(String retmsg) {
		ResponseView rv = new ResponseView();
		rv.put("retmsg", retmsg);
		initFial(rv);
		return rv;
	}
	public static ResponseView unauthorized(String retmsg) {
		return initUnauthorized(retmsg);
	}
	
	public static ResponseView unauthorized() {
		return initUnauthorized(ErrorCode.E401);
	}
	private static ResponseView initUnauthorized(String retmsg) {
		ResponseView rv = new ResponseView();
		rv.put("retmsg", retmsg);
		rv.put("currentTime", new Date().getTime());
		rv.put("status", ErrorCode.UNAUTHORIZED);
		rv.put("retcode", ErrorCode.UNAUTHORIZED);
		throw new SuperException(rv);
	}
	
	private static void init(ResponseView rv) {
		rv.put("retmsg", retmsg);
		rv.put("currentTime", new Date().getTime());
		rv.put("status", ErrorCode.SUCCESS);
		rv.put("retcode", 0);
	}
	private static void initPass(ResponseView rv) {
		rv.put("retmsg", retmsg);
		rv.put("currentTime", new Date().getTime());
		rv.put("status", ErrorCode.SUCCESS);
		rv.put("retcode", 0);
	}
	private static void initPassMessage(ResponseView rv) {
		if(rv.get("retmsg") == null) {
			rv.put("retmsg", retmsg);
		}else {
			rv.put("retmsg", rv.get("retmsg"));
		}
		rv.put("currentTime", new Date().getTime());
		rv.put("status", ErrorCode.SUCCESS);
		rv.put("retcode", 0);
	}
	
	private static void initFial(ResponseView rv) {
		if (rv.get("retmsg") != null) {
			String retcodeName = getErrorCodeName((String)rv.get("retmsg") );
			if(retcodeName != "") {
				rv.put("retcode", retcodeName);
			}else {
				rv.put("retcode", ErrorCode.ERROR);
			}
		}
		rv.put("currentTime", new Date().getTime());
		rv.put("status", ErrorCode.ERROR);
		throw new SuperException(rv);
	}
	
	/**
	 * 根据具体的错误信息获取相应的Code名字
	 * 
	 * @param errorInfo
	 *            错误原因
	 * @return code
	 */
	public static String getErrorCodeName(String errorInfo) {

		ErrorCode error = new ErrorCode();
		// 获取Class对象
		@SuppressWarnings("rawtypes")
		Class c = error.getClass();
		// 获取所有字段
		Field[] fields = c.getFields();
		String codeName = "";

		for (Field f : fields) {
			try {
				if (f.get(error) instanceof String) {
					String info = (String) f.get(error);
					if (info.equals(errorInfo)) {
						codeName = f.getName();
					}
				}

			} catch (IllegalArgumentException | IllegalAccessException e) {

				return "";
			}
		}
		return codeName.replace("E", "");
	}
}