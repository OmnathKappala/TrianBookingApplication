package com.springboot.service;

import com.springboot.dto.request.PaymentRequest;
import com.springboot.dto.request.PaymentVerficationRequest;
import com.springboot.dto.response.PaymentResponse;

public interface PaymentService {
	
	public PaymentResponse makePayment(PaymentRequest paymentRequest);
	
	public PaymentResponse verifyPayment(PaymentVerficationRequest paymentVerificationRequest);

}
