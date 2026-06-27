package com.springboot.dto.response;

import java.time.LocalDateTime;

import com.springboot.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
	private Long id;
	private String razorPayOrderId;
	
	private Double amount;
	
	private PaymentStatus status;
	
	private LocalDateTime dateTime;
	
	 private Long bookingId;
	
	
	

}
