package com.springboot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.dto.request.TrainRequest;
import com.springboot.dto.response.TrainResponse;
import com.springboot.entity.Train;
import com.springboot.exception.TrainAlreadyExist;
import com.springboot.repository.ITrainRepo;
import com.springboot.service.ITrainService;
@Service
public class ITrainServiceImpl implements ITrainService {
	
	@Autowired
	private ITrainRepo repo;

	@Override
	public TrainResponse registerTrain(TrainRequest trainRequest) {
		
		if(repo.existsByTrainNumber(trainRequest.getTrainNumber())) {
			throw new TrainAlreadyExist("Train already exist with this number "+ trainRequest.getTrainName()+" ");
		}
		
	
		 Train trainData = new Train();
		 
		 
		 trainData.setTrainNumber(trainRequest.getTrainNumber());
		 trainData.setTrainName(trainRequest.getTrainName());
		 trainData.setDepartureTime(trainRequest.getDepartureTime());
		 trainData.setArrivalTime(trainRequest.getArrivalTime());
		 trainData.setSource(trainRequest.getSource());
		 trainData.setDestination(trainRequest.getDestination());
		 trainData.setTotalSeats(trainRequest.getTotalSeats());
		 trainData.setAvailableSeats(trainRequest.getAvailableSeats());
		 trainData.setFare(trainRequest.getFare()); 
		 
		 Train savedTrain = repo.save(trainData);
		 TrainResponse trainRep = new TrainResponse();
		 
		 trainRep.setId(savedTrain.getId());
		 trainRep.setTrainNumber(savedTrain.getTrainNumber());
		 trainRep.setTrainName(savedTrain.getTrainName());
		 trainRep.setSource(savedTrain.getSource());
		 trainRep.setDestination(savedTrain.getDestination());
		 trainRep.setArrivalTime(savedTrain.getArrivalTime());
		 trainRep.setDepartureTime(savedTrain.getDepartureTime());
		 trainRep.setTotalSeats(savedTrain.getTotalSeats());
		 trainRep.setAvailableSeats(savedTrain.getAvailableSeats());
		 trainRep.setFare(savedTrain.getFare());
		return trainRep;
	}

	@Override
	public List<TrainResponse> findbySourceAndDestination(String source, String destination) {
		List<Train> trains = repo.findBySourceAndDestination(source, destination);
		if(trains.isEmpty()) {
			 throw new TrainAlreadyExist("No train found with :"+source+" and :"+destination+" ");
		}
		List<TrainResponse>trainResponse= new ArrayList<>();
		for(Train train:trains) {
			TrainResponse trainsp= new TrainResponse();
			trainsp.setId(train.getId());
			trainsp.setTrainNumber(train.getTrainNumber());
			trainsp.setTrainName(train.getTrainName());
			trainsp.setSource(train.getSource());
			trainsp.setDestination(train.getDestination());
			trainsp.setArrivalTime(train.getArrivalTime());
			trainsp.setDepartureTime(train.getDepartureTime());
			trainsp.setTotalSeats(train.getTotalSeats());
			trainsp.setAvailableSeats(train.getAvailableSeats());
			trainsp.setFare((train.getFare()));
			trainResponse.add(trainsp);
		}
		
		
		return  trainResponse;
	}

}
