package com.springboot.exception;

public class UserNotFound  extends RuntimeException{

	
	private String msg;

	public UserNotFound(String msg) {
		super(msg);
		 
	}
	
	
}
