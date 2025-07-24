package com.fengshen.core.exception;

import java.util.Map;

import com.fengshen.core.util.ResponseView;

/**
 * 
 * @ClassName: SuperException
 * @Description:项目所有异常的父类
 * @author 彭联伟
 * @date 2016年11月10日 上午11:46:52
 *
 */
public class SuperException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> resultMap;
	private String retmsg;
	private ResponseView rv;
	public SuperException(String message) {
		super(message);
	}

	public SuperException(ResponseView rv) {
		super((String) rv.get("retmsg"));
		this.retmsg = (String) rv.get("retmsg");
		this.rv = rv;
	}

	public SuperException(Map<String, Object> resultMap) {

		this.resultMap = resultMap;
	}

	public SuperException() {
		super();
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	

	public String getRetmsg() {
		return retmsg;
	}

	public void setRetmsg(String retmsg) {
		this.retmsg = retmsg;
	}

	public ResponseView getRv() {
		return rv;
	}

	public void setRv(ResponseView rv) {
		this.rv = rv;
	}
	
	
	

}
