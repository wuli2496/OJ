package com.topcoder.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.topcoder.demo.error.EntityNotFoundException;
import com.topcoder.demo.model.Train;
import com.topcoder.demo.repository.TrainRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trains")
public class TrainController {
	
	@Autowired
	private TrainRepository trainRepository;

	@GetMapping() 
	public ResponseEntity<List<Train>> getAllTrains() {
		List<Train> trains = new ArrayList<>();
		trains = trainRepository.findAll();
		return new ResponseEntity<>(trains, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Train> getTrainById(@PathVariable("id") Long id) {
		Optional<Train> train = trainRepository.findById(id);
		if (!train.isPresent()) {
			throw new EntityNotFoundException("train not found");
		} 
		
		return new ResponseEntity<>(train.get(), HttpStatus.OK);
	}
}
