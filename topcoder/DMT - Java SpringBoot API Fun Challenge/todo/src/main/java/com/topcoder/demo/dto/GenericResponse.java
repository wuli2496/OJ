package com.topcoder.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericResponse<T> {
	private Integer code;
	
	private String msg;
	
	private T data;
	
	public static <T> GenericResponse<T> success(T data) {
		return new GenericResponse<T>(0, "", data);
	}
	
	public static <T> GenericResponse<T> failure(Integer code, String msg) {
		return new GenericResponse<T>(code, msg, null);
	}
}
