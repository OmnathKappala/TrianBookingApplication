package com.springboot.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentVerficationRequest {
	private String razorpayOrderId;
	
	private String razorpayPaymentId;
	
	private String razorpaySignature;
	

}
