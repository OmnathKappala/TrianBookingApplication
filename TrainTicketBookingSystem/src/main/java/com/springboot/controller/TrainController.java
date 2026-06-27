package com.springboot.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.dto.request.TrainRequest;
import com.springboot.dto.response.TrainResponse;
import com.springboot.service.ITrainService;
import com.springboot.util.ResponseStructure;

@RestController
@RequestMapping("/trains")
@CrossOrigin(origins = "*",methods = {RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT,RequestMethod.DELETE})
public class TrainController {
	
	@Autowired
	private ITrainService service;
	
	@PostMapping("/register-train")
	public ResponseEntity<ResponseStructure<TrainResponse>>registerTrain(@RequestBody TrainRequest trainRequest){
		ResponseStructure<TrainResponse> structure= new ResponseStructure<>();
		TrainResponse trainResponse = service.registerTrain(trainRequest);
		
		structure.setMessage("Train Registration sucessfully");
		structure.setData(trainResponse);
		structure.setStatus(HttpStatus.CREATED.value());
		structure.setDate(LocalDateTime.now());
		
		return new ResponseEntity<>(structure,HttpStatus.CREATED);
		
	}
	@GetMapping("/search")
	public ResponseEntity<ResponseStructure<List<TrainResponse>>>findbySourceAndDestination(@RequestParam("source")String Source,@RequestParam("destination")String Destination)
	{
		   ResponseStructure<List<TrainResponse>>structure= new ResponseStructure<>();
		 List<TrainResponse> trainResponse = service.findbySourceAndDestination(Source,  Destination);
		 structure.setData(trainResponse);
		 structure.setMessage("train found with this source: "+Source+ " and destination : "  +  Destination);
		 structure.setStatus(HttpStatus.OK.value());
		 structure.setDate(LocalDateTime.now());
		
		
		
		          return  new ResponseEntity<>(structure,HttpStatus.OK);
	}
	
	
	

}
