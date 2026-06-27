package com.springboot.service;

import java.util.List;

import com.springboot.dto.request.TrainRequest;
import com.springboot.dto.response.TrainResponse;
import com.springboot.entity.Train;

public interface ITrainService {
	public TrainResponse registerTrain(TrainRequest trainRequest);
	
	public List<TrainResponse>findbySourceAndDestination(String source , String destination);

}
