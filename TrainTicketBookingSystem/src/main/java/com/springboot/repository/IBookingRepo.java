package com.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.entity.Booking;
@Repository
public interface IBookingRepo extends JpaRepository<Booking, Long>{
   List<Booking> findByUserId(Long id);
}
