package com.onerivet.model.projection;

public class GetAllQuestionProjection {
	private Integer questionId;
	private String question;
	private Integer vehicleTypeId;
	private String vehicleTypeName;
	private Integer statusId;
	private String status;
	private Boolean isMandatory;
	private Boolean isActive;
	
	public GetAllQuestionProjection(Integer questionId, String question, Integer vehicleTypeId, String vehicleTypeName,
			Integer statusId, String status, Boolean isMandatory, Boolean isActive) {
		this.questionId = questionId;
		this.question = question;
		this.vehicleTypeId = vehicleTypeId;
		this.vehicleTypeName = vehicleTypeName;
		this.statusId = statusId;
		this.status = status;
		this.isMandatory = isMandatory;
		this.isActive = isActive;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public String getQuestion() {
		return question;
	}

	public Integer getVehicleTypeId() {
		return vehicleTypeId;
	}

	public String getVehicleTypeName() {
		return vehicleTypeName;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public String getStatus() {
		return status;
	}

	public Boolean getIsMandatory() {
		return isMandatory;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	
}
