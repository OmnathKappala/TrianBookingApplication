package com.springboot.service;

import java.util.List;

import com.springboot.dto.request.BookingRequest;
import com.springboot.dto.response.BookingResponse;

public interface IBookingService 
{ 
	 
	
	public BookingResponse cancelTrainTicket( Long bookingSeatId);
	
	public List<BookingResponse> getAllBookingsByUserId(Long Id);
	
	public 	 BookingResponse bookTrainSeat(String token, BookingRequest bookingRequest);
	
	
	
 
}
