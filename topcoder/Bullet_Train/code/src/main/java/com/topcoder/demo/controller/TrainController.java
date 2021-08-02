package com.topcoder.demo.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.topcoder.demo.dto.GenericResponse;
import com.topcoder.demo.error.EntityNotFoundException;
import com.topcoder.demo.error.SearchParamException;
import com.topcoder.demo.error.TrainNotFoundException;
import com.topcoder.demo.model.Train;
import com.topcoder.demo.repository.TrainRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/trains")
@Slf4j
public class TrainController {
	
	@Autowired
	private TrainRepository trainRepository;

	
	@GetMapping() 
	public ResponseEntity<List<Train>> getAllTrains(@RequestParam(value = "amenities", defaultValue = "") String keyWord,
			HttpServletRequest request) { 
		
		List<Train> trains = null; 
		if (StringUtils.isBlank(keyWord)) {
			if (request.getParameterMap().size() >= 1) {
				throw new SearchParamException("invalid endpoint");
			}
			trains = trainRepository.findAll();
		} else {
			trains = trainRepository.findByAmenitiesLike("%" + keyWord + "%");
			if (CollectionUtils.isEmpty(trains)) {
				throw new TrainNotFoundException("train not found");
			}
		}
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
	
	@GetMapping("sharing-tracks")
	public ResponseEntity<List<Train>> getTrainBySharingTracks() {
		List<Train> trains = trainRepository.findBySharingTracks(true);
		return new ResponseEntity<>(trains, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<GenericResponse> deleteById(@PathVariable("id") Long id) {
		Optional<Train> train = trainRepository.findById(id);
		if (!train.isPresent()) {
			throw new EntityNotFoundException("train not found");
		} 
		
		trainRepository.deleteById(id);
		GenericResponse genericResponse = new GenericResponse("train removed successfully", null);
		return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.OK);
	}
}
