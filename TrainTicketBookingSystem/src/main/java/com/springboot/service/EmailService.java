package com.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	 
	    @Autowired
	    private JavaMailSender mailSender;

	    public void sendBookingConfirmation(String toEmail, String userName, 
	                                         String trainName, String source, 
	                                         String destination, Double fare) {

	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(toEmail);
	        message.setSubject("Booking Confirmation - " + trainName);
	        message.setText("Dear " + userName + ",\n\n" +
	                       "Your booking is confirmed!\n" +
	                       "Train: " + trainName + "\n" +
	                       "From: " + source + "\n" +
	                       "To: " + destination + "\n" +
	                       "Fare: ₹" + fare + "\n\n" +
	                       "Thank you for booking with us!");

	        mailSender.send(message);
	    }
	}


