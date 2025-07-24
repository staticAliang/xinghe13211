package com.fengshen.core.exception;

import com.fengshen.core.util.ResponseView;

/**
 * 授权异常
 * 
 * 
 *
 * createTime: 2018年3月27日 上午11:50:00
 * @author: William Peng
 * @sine: 1.0
 */
public class UnauthorizedException extends SuperException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7147829327106595761L;

	public UnauthorizedException(String message) {
		super(message);
	}

	public UnauthorizedException(ResponseView rv) {
		super(rv);
	}
	
	public UnauthorizedException() {
		
		super();
	}
}
