package com.springboot.dto.response;

import java.time.LocalTime;

 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


	 
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	 
	public class TrainResponse {
	 
		private Long id;
		
		private String trainNumber;
	 
		private String trainName;
		
		private String source;
		
		private String destination;
		
		private LocalTime  departureTime;
		
		private LocalTime arrivalTime;
		
		private Integer totalSeats;
		
		private Integer availableSeats;
		
		private Double fare;

	



}
