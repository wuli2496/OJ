package com.topcoder.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
@Table(name = "trains", schema = "public")
public class Train {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("name")
	@Column(name = "name")
	private String name;
	
	@JsonProperty("description")
	@Column(name = "description")
	private String description;
	
	@Column(name = "\"distance-between-stop\"")
	@JsonProperty("distance-between-stop")
	private String distanceBetweenStop;
	
	@JsonProperty("max-speed")
	@Column(name = "\"max-speed\"")
	private String maxSpeed;
	
	@JsonProperty("sharing-tracks")
	@Column(name = "\"sharing-tracks\"")
	private Boolean sharingTracks;
	
	@JsonProperty("grade-crossing")
	@Column(name = "\"grade-crossing\"")
	private Boolean gradeCrossing;
	
	@JsonProperty("train-frequency")
	@Column(name = "\"train-frequency\"")
	private String trainFrequency;
	
	@JsonProperty("amenities")
	@Column(name = "amenities")
	private String amenities;
}
