package com.onerivet.model.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "error", "data" })
public class GenericResponse<T> {
	private String error;
	private T data;


	public GenericResponse() {

	}

	public GenericResponse(T data) {
		this.data = data;
	}

	public GenericResponse(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public T getData() {
		return data;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setData(T data) {
		this.data = data;
	}


}