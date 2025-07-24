package com.fengshen.server.exception;

/**
 * 战斗报错异常类
 * @author weilian
 *
 */
public class FightException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FightException() {
		
	}

	public FightException(String message) {
		super(message);
	}
}
