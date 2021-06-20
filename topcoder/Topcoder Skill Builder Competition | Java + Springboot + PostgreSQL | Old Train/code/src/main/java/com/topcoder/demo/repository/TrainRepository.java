package com.topcoder.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.topcoder.demo.model.Train;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
	List<Train> findBySharingTracks(Boolean sharingTracks);
	
	List<Train> findByAmenitiesLike(String amenities);
}
