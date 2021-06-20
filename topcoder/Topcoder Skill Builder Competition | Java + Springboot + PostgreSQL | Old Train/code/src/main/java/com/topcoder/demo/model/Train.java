package com.topcoder.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
@Table(name = "trains", schema = "public")
@DynamicUpdate(true)
public class Train {
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("id")
	@NotNull
	private Long id;
	
	@JsonProperty("name")
	@Column(name = "name")
	@NotBlank
	private String name;
	
	@JsonProperty("description")
	@Column(name = "description")
	@NotBlank
	private String description;
	
	@Column(name = "\"distance-between-stop\"")
	@JsonProperty("distance-between-stop")
	@NotBlank
	private String distanceBetweenStop;
	
	@JsonProperty("max-speed")
	@Column(name = "\"max-speed\"")
	@NotBlank
	private String maxSpeed;
	
	@JsonProperty("sharing-tracks")
	@Column(name = "\"sharing-tracks\"")
	@NotNull
	private Boolean sharingTracks;
	
	@JsonProperty("grade-crossing")
	@Column(name = "\"grade-crossing\"")
	@NotNull
	private Boolean gradeCrossing;
	
	@JsonProperty("train-frequency")
	@Column(name = "\"train-frequency\"")
	@NotBlank
	private String trainFrequency;
	
	@JsonProperty("amenities")
	@Column(name = "amenities")
	@NotBlank
	private String amenities;
}
