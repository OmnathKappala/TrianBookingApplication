package com.springboot.util;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ResponseStructure<T> {
	 private String message;
	 
	 private  int status;
	 
	 private T data;
	 
	 private LocalDateTime date;
	

}
