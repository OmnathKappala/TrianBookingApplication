package com.springboot.exception;

public class RazorPayException extends RuntimeException {
	private String msg;
	
	public RazorPayException(String msg) {
		super(msg);
	}

}
