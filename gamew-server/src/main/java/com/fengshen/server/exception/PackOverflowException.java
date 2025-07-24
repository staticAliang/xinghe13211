package com.fengshen.server.exception;

/**
 * 背包溢出
 * 
 *
 */
public class PackOverflowException extends RuntimeException {

	private static final long serialVersionUID = 4814319114805744317L;

	public PackOverflowException() {

	}

	public PackOverflowException(String message) {
		super(message);
	}
}
