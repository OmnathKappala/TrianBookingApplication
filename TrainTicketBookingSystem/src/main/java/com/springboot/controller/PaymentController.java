package com.springboot.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.dto.request.PaymentRequest;
import com.springboot.dto.request.PaymentVerficationRequest;
import com.springboot.dto.response.PaymentResponse;
import com.springboot.service.PaymentService;
import com.springboot.util.ResponseStructure;

@RestController
@RequestMapping("/payments")
@CrossOrigin(origins = "*",methods = {RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT,RequestMethod.DELETE})
public class PaymentController {
	@Autowired
	private PaymentService service;
	
	@PostMapping("make-payment")
	public ResponseEntity<ResponseStructure<PaymentResponse>>makePayment(@RequestBody PaymentRequest paymentRequest){
		PaymentResponse response = service.makePayment(paymentRequest);
		
		ResponseStructure<PaymentResponse> structure = new ResponseStructure<>();
		structure.setData(response);
		structure.setDate(LocalDateTime.now());
		structure.setMessage("Payment intiated");
		structure.setStatus(HttpStatus.CREATED.value());
		
		
		
		return  new ResponseEntity<>(structure,HttpStatus.CREATED);
		
	}
	@PostMapping("verify-payments")
	public ResponseEntity<ResponseStructure<PaymentResponse>>verifyPayment(@RequestBody PaymentVerficationRequest pvr){
		PaymentResponse response = service.verifyPayment(pvr);
		ResponseStructure<PaymentResponse> structure = new ResponseStructure<>();
		structure.setData(response);
		structure.setDate(LocalDateTime.now());
		structure.setMessage("Payment sucessfull");
		structure.setStatus(HttpStatus.OK.value());
		
		return new ResponseEntity<>(structure,HttpStatus.OK);
		
		
		
		
	}

}
