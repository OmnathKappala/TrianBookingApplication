package com.springboot.exception;

public class BookingNotFound  extends RuntimeException
{
          private String msg;
          
          public BookingNotFound(String msg) {
        	  super(msg);
          }
}
