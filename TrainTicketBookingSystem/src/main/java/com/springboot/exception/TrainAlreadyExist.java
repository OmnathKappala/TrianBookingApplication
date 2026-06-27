package com.springboot.exception;

public class TrainAlreadyExist extends RuntimeException{
	private String msg;

	public TrainAlreadyExist(String msg) {
		 super(msg);
		 
	}
	
	

}
