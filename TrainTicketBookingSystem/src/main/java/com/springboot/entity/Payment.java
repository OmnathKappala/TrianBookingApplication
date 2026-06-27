package com.springboot.entity;

import java.time.LocalDateTime;

import com.springboot.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment
{      
	@Id
	@GeneratedValue
	private Long id;
	
	private String razorPayOrderId;
	
	private String razorpayPaymentId;
	
	private Double amount;
	
	private LocalDateTime paymentDate;
	@Enumerated(EnumType.STRING)
	private PaymentStatus status;
	
	@OneToOne
	@JoinColumn(name="booking_id")
	private Booking booking;
	

}
