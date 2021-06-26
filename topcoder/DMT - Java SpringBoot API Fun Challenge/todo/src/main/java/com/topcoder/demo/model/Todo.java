package com.topcoder.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity(name = "todos")
public class Todo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	@NotBlank
	private String name;
	
	@NotBlank
	@Column(name = "description")
	private String description;
	
	@NotNull
	@Column(name = "priority")
	private Integer priority;
	
	@NotNull
	@Column(name = "due_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dueDate;
	
	@NotNull
	@Column(name = "completed", columnDefinition="smallint")
	private Boolean completed;
	
	@NotNull
	@Column(name = "completion_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date completionDate;
}
