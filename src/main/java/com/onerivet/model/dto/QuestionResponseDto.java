package com.onerivet.model.dto;

import java.util.List;

public class QuestionResponseDto {
	private Integer questionId;
	private String question;
	private String helpText;
	private Integer questionType;
	private VehicleTypeResponseDto vehicleType;
	private InputTypeResponseDto inputType;
	private Boolean isMandatory;
	private Boolean isActive;
	private List<OptionResponseDto> options; 

	public QuestionResponseDto() {

	}

	public QuestionResponseDto(Integer questionId, String question, String helpText, Integer questionType,
			VehicleTypeResponseDto vehicleType, InputTypeResponseDto inputType, Boolean isMandatory, Boolean isActive,
			List<OptionResponseDto> options) {

		this.questionId = questionId;
		this.question = question;
		this.helpText = helpText;
		this.questionType = questionType;
		this.vehicleType = vehicleType;
		this.inputType = inputType;
		this.isMandatory = isMandatory;
		this.isActive = isActive;
		this.options = options;
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

	public String getHelpText() {
		return helpText;
	}

	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}

	public Integer getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	public VehicleTypeResponseDto getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleTypeResponseDto vehicleType) {
		this.vehicleType = vehicleType;
	}

	public InputTypeResponseDto getInputType() {
		return inputType;
	}

	public void setInputType(InputTypeResponseDto inputType) {
		this.inputType = inputType;
	}

	public Boolean getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public List<OptionResponseDto> getOptions() {
		return options;
	}

	public void setOptions(List<OptionResponseDto> options) {
		this.options = options;
	}
}
