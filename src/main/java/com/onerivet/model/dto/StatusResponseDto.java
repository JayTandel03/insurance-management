package com.onerivet.model.dto;

public class StatusResponseDto {
	private Integer statusId;
	private String status;

	public StatusResponseDto() {
	}

	public StatusResponseDto(Integer statusId, String status) {
		this.statusId = statusId;
		this.status = status;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}