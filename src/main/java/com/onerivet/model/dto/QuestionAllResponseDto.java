package com.onerivet.model.dto;

import com.onerivet.model.dto.StatusResponseDto;

public class QuestionAllResponseDto {

	private Integer questionId;
	private String question;
	private StatusResponseDto status;
	private Boolean isMandatory;
	private VehicleTypeResponseDto vehicleType;
	private Boolean isActive;

	public QuestionAllResponseDto() {
	}

	public QuestionAllResponseDto(Integer questionId, String question, StatusResponseDto status, Boolean isMandatory,
			VehicleTypeResponseDto vehicleType, Boolean isActive) {
		this.questionId = questionId;
		this.question = question;
		this.status = status;
		this.isMandatory = isMandatory;
		this.vehicleType = vehicleType;
		this.isActive = isActive;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public StatusResponseDto getStatus() {
		return status;
	}

	public void setStatus(StatusResponseDto status) {
		this.status = status;
	}

	public Boolean getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public VehicleTypeResponseDto getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleTypeResponseDto vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}