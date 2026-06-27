package com.springboot.service.impl;

import java.time.LocalDateTime;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.springboot.dto.request.PaymentRequest;
import com.springboot.dto.request.PaymentVerficationRequest;
import com.springboot.dto.response.PaymentResponse;
import com.springboot.entity.Booking;
import com.springboot.entity.Payment;
import com.springboot.enums.PaymentStatus;
import com.springboot.exception.BookingNotFound;
import com.springboot.exception.RazorPayException;
import com.springboot.repository.IBookingRepo;
import com.springboot.repository.PaymentRepo;
import com.springboot.service.EmailService;
import com.springboot.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
	@Autowired
	private PaymentRepo repo;
	
	@Autowired
	private IBookingRepo bookingRepo;
	
	@Autowired
	private EmailService emailService;
	
	
	@Autowired
	private RazorpayClient razropayClient;

	@Value("${razorpay.key.secret}")
	private String keySecret;
	@Override
	public PaymentResponse makePayment(PaymentRequest paymentRequest) {
		Booking booking = bookingRepo.findById(paymentRequest.getBookingId()).orElseThrow(()-> new BookingNotFound("no bus with this id : "+paymentRequest.getBookingId() ));
		try {
		 JSONObject orderRequest = new JSONObject();
         orderRequest.put("amount", booking.getTotalFare() * 100); 
         orderRequest.put("currency", "INR");
         orderRequest.put("receipt", "booking_" + booking.getId());

         Order razorpayOrder =  razropayClient.orders.create(orderRequest);
         Payment savedPayment = new Payment();
         savedPayment.setBooking(booking);
         savedPayment.setAmount(booking.getTotalFare());
         savedPayment.setPaymentDate(LocalDateTime.now());
         savedPayment.setRazorPayOrderId(razorpayOrder.get("id"));
         savedPayment.setStatus(PaymentStatus.Pending);
       Payment payment = repo.save(savedPayment);
       PaymentResponse paymentResponse= new PaymentResponse();
       paymentResponse.setId(payment.getId());
       paymentResponse.setRazorPayOrderId(razorpayOrder.get("id"));
       paymentResponse.setStatus(payment.getStatus());
       paymentResponse.setDateTime(LocalDateTime.now());
       paymentResponse.setBookingId(payment.getBooking().getId());
       
       return paymentResponse;
         
         
		}
		catch(RazorpayException e) {
			throw new RazorPayException("PaymentFailed");
		}
		  
	}

	@Override
	public PaymentResponse verifyPayment(PaymentVerficationRequest paymentVerificationRequest) {
		Payment payment = repo.findByRazorPayOrderId(paymentVerificationRequest.getRazorpayOrderId())
				.orElseThrow(()-> new RazorPayException("Order not inisiated yet "));
		     PaymentResponse paymentResponse = new PaymentResponse();
		
	 		if(verifySignature(paymentVerificationRequest.getRazorpayOrderId(),paymentVerificationRequest.getRazorpayPaymentId()
	 				,paymentVerificationRequest.getRazorpaySignature())) {
	 			payment.setStatus(PaymentStatus.Success);
	 			
	 			payment.setRazorpayPaymentId(paymentVerificationRequest.getRazorpayPaymentId());
	 			Payment updatePayment = repo.save(payment);
	 			
	 			 emailService.sendBookingConfirmation(
	 			        payment.getBooking().getUser().getEmail(),
	 			        payment.getBooking().getUser().getName(),
	 			        payment.getBooking().getTrain().getTrainName(),
	 			        payment.getBooking().getTrain().getSource(),
	 			        payment.getBooking().getTrain().getDestination(),
	 			        payment.getBooking().getTotalFare()
	 			    );
	 			
	 			paymentResponse.setAmount(updatePayment.getAmount());
	 			paymentResponse.setBookingId(updatePayment.getBooking().getId());
	 			paymentResponse.setDateTime(updatePayment.getPaymentDate());
	 			paymentResponse.setRazorPayOrderId(updatePayment.getRazorPayOrderId());
	 			paymentResponse.setStatus(updatePayment.getStatus());
	 			return paymentResponse;
	 		}
	 		else {
	 		    payment.setStatus(PaymentStatus.Failed);
	 		    repo.save(payment);
	 		    throw new RazorPayException("Payment verification failed!");
	 		}
	}
	
	
	private boolean verifySignature(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) {
	    try {
	        String data = razorpayOrderId + "|" + razorpayPaymentId;
	        Mac mac = Mac.getInstance("HmacSHA256");
	        SecretKeySpec secretKeySpec = new SecretKeySpec(keySecret.getBytes(), "HmacSHA256");
	        mac.init(secretKeySpec);
	        byte[] hash = mac.doFinal(data.getBytes());
	        StringBuilder hexString = new StringBuilder();
	        for(byte b : hash) {
	            String hex = Integer.toHexString(0xff & b);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }
	        return hexString.toString().equals(razorpaySignature);
	    } catch(Exception e) {
	        return false;
	    }
	}

}