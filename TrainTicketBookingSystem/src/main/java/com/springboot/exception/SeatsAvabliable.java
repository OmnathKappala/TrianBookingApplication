package com.springboot.exception;

public class SeatsAvabliable extends RuntimeException{
	private String msg;

	public SeatsAvabliable(String msg) {
		super(msg);
		 
	}
	
	

}
