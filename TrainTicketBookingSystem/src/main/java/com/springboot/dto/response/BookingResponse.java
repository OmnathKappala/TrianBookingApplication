package com.springboot.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.springboot.enums.BookingStatus;
import com.springboot.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
	
	private Long id;
	
	private LocalDateTime bookingDateTime;
	
	private BookingStatus status;
	
	private Integer seatNumber;
	
	private double fare;
	
	private String userName;
	
	private String trainName;
	
	private String source;
	
	private String destination;

}
