package com.onerivet.model.dto;

import jakarta.validation.constraints.NotBlank;

public class StatusRequestDto {
	

    @NotBlank(message = "Status is required")
    private String status; 

    public StatusRequestDto(String status) {
		
		this.status = status;
	}

	public StatusRequestDto() {
		
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}