package com.fengshen.server.exception;

/**
 * 宠物背包满了
 * 
 *
 */
public class PetPackOverflowException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8549356404907148160L;

	public PetPackOverflowException() {

	}

	public PetPackOverflowException(String message) {
		super(message);
	}

}
