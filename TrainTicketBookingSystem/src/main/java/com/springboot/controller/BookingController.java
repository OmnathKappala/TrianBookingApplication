package com.springboot.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.dto.request.BookingRequest;
import com.springboot.dto.response.BookingResponse;
import com.springboot.service.IBookingService;
import com.springboot.util.ResponseStructure;

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "*",methods = {RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT,RequestMethod.DELETE})
public class BookingController {
	@Autowired
	private IBookingService service;
	
	
	@PostMapping("/bookSeat")
	public ResponseEntity<ResponseStructure<BookingResponse>> bookTrain(
	        @RequestHeader("Authorization") String authHeader,
	        @RequestBody BookingRequest bookingRequest) {

	    String token = authHeader.substring(7); // remove "Bearer "
	    BookingResponse response = service.bookTrainSeat(token, bookingRequest);
	    ResponseStructure<BookingResponse>structure=new ResponseStructure<>();
	    
	    structure.setData(response);
	    structure.setStatus(HttpStatus.CREATED.value());
	    structure.setDate(response.getBookingDateTime());
	    structure.setMessage("Booking Sucessfully completed");
	    
	    return new ResponseEntity<>(structure,HttpStatus.CREATED);
	}
	
	@PutMapping("/cancel-seat/{id}")
	public ResponseEntity<ResponseStructure<BookingResponse>>cancleSeat(@PathVariable("id") Long bookingSeatId){
		
        ResponseStructure<BookingResponse> strucuture=new ResponseStructure<>();
		
		BookingResponse  cancelSeat = service.cancelTrainTicket(bookingSeatId);
		
		strucuture.setData(cancelSeat);
		strucuture.setMessage("booking  cancelled");
		strucuture.setStatus(HttpStatus.OK.value());
		strucuture.setDate(LocalDateTime.now());
		
		
		return new ResponseEntity<>(strucuture,HttpStatus.OK);
		
	}
	@GetMapping("/get-ById/{id}")
	public ResponseEntity<ResponseStructure<List<BookingResponse>>>getAllBookingUserId(@PathVariable("id") Long Id){
		ResponseStructure<List<BookingResponse>>structure= new ResponseStructure<>();
		List<BookingResponse> br = service.getAllBookingsByUserId(Id);
		
		structure.setMessage("Booking History of User with ID : "+ Id);
		structure.setData(br);
		structure.setStatus(HttpStatus.OK.value());
		structure.setDate(LocalDateTime.now());
	     	
		 
		
		
		return new ResponseEntity<>(structure,HttpStatus.OK);
		

		
		
	
	}

}
