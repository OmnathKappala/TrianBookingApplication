package com.springboot.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.springboot.util.ResponseStructure;

@RestControllerAdvice
public class GobalException {
	@ExceptionHandler(UserNotFound.class)
	public ResponseEntity<ResponseStructure<String>> userNotFound(UserNotFound unf){
		ResponseStructure<String>structure= new ResponseStructure<>();
		 structure.setData(null);
		structure.setMessage(unf.getMessage());
		structure.setStatus(HttpStatus. BAD_REQUEST.value());
		structure.setDate(LocalDateTime.now());
		return new ResponseEntity<>(structure,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(TrainAlreadyExist.class)
	public ResponseEntity<ResponseStructure<String>>trainExists(TrainAlreadyExist tae){
		
		ResponseStructure<String>structure = new ResponseStructure<>();
		    structure.setMessage(tae.getMessage());
		    structure.setStatus(HttpStatus.BAD_REQUEST.value());
		    structure.setDate(LocalDateTime.now());
		    structure.setData(null);
		    return  new ResponseEntity<>(structure,HttpStatus.BAD_REQUEST);
		
		
	}
	
	@ExceptionHandler(SeatsAvabliable.class)
	public ResponseEntity<ResponseStructure<String>>seatAvaliablity(SeatsAvabliable sa){
		ResponseStructure  structure = new ResponseStructure<>();
	    structure.setMessage(sa.getMessage());
	    structure.setStatus(HttpStatus.BAD_REQUEST.value());
	    structure.setDate(LocalDateTime.now());
	    structure.setData(null);
	    
	    return  new ResponseEntity<>(structure,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(BookingNotFound.class)
	public ResponseEntity<ResponseStructure<String>>SeatNotBooked(BookingNotFound bnf){
		ResponseStructure<String> structure= new ResponseStructure<>();
		
		structure.setMessage(bnf.getMessage());
		structure.setStatus(HttpStatus.NOT_FOUND.value());
		structure.setDate(LocalDateTime.now());
		return  new ResponseEntity<>(structure,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RazorPayException.class)
	public ResponseEntity<ResponseStructure<String>>paymentException(RazorPayException rpe){
		ResponseStructure<String>response= new ResponseStructure<>();
		response.setMessage(rpe.getMessage());
		response.setData(null);
		response.setStatus(HttpStatus.BAD_GATEWAY.value());
		response.setDate(LocalDateTime.now());
		
		
		return  new ResponseEntity<>(response,HttpStatus.BAD_GATEWAY);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
