package com.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.entity.Train;
@Repository
public interface ITrainRepo extends JpaRepository<Train, Long>

{
	 boolean existsByTrainNumber(String trainNumber);
	 List<Train> findBySourceAndDestination(String source, String destination);
}
