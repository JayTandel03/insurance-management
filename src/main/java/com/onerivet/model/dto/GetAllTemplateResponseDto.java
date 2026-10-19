package com.onerivet.model.dto;

import java.time.LocalDateTime;

import com.onerivet.model.dto.VehicleTypeResponseDto;

public class GetAllTemplateResponseDto {
	private String templateId;
	private String templateName;
	private VehicleTypeResponseDto vehicle;
	private StatusResponseDto status;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private String modifiedBy;

	public GetAllTemplateResponseDto() {}

	public GetAllTemplateResponseDto(String templateId, String templateName, VehicleTypeResponseDto vehicle, StatusResponseDto status,
	                           LocalDateTime createdDate, LocalDateTime modifiedDate, String modifiedBy) {

		this.templateId = templateId;
		this.templateName = templateName;
		this.vehicle = vehicle;
		this.status = status;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public VehicleTypeResponseDto getVehicle() {
		return vehicle;
	}

	public void setVehicle(VehicleTypeResponseDto vehicle) {
		this.vehicle = vehicle;
	}

	public StatusResponseDto getStatus() {
		return status;
	}

	public void setStatus(StatusResponseDto status) {
		this.status = status;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
