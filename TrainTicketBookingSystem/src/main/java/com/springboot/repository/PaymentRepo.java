package com.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.entity.Payment;

public interface PaymentRepo extends JpaRepository<Payment,Long>{
	Optional<Payment> findByRazorPayOrderId(String razorpayOrderId);

}
