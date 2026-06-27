package com.springboot.entity;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Train {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique=true)
	private String trainNumber;
	
	private String trainName;
	
	private String source;
	
	private String destination;
	
	private LocalTime   departureTime;
	
	private LocalTime arrivalTime;
	
	private Integer totalSeats;
	
	private Integer availableSeats;
	
	private Double fare;

	 

}
