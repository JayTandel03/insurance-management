package com.onerivet.model.projection;

public class QuestionProjection {

	private Integer questionId;
	private String question;
	private String helpText;
	private Integer questionType;
	private Integer vehicleTypeId;
	private String vehicleTypeName;
	private Integer inputTypeId;
	private String inputTypeName;
	private Boolean isMandatory;
	private Boolean isActive;
	private Integer optionId;
	private String optionText;

	public QuestionProjection(Integer questionId, String question, String helpText, Integer questionType,
			Integer vehicleTypeId, String vehicleTypeName, Integer inputTypeId, String inputTypeName,
			Boolean isMandatory, Boolean isActive, Integer optionId, String optionText) {
		this.questionId = questionId;
		this.question = question;
		this.helpText = helpText;
		this.questionType = questionType;
		this.vehicleTypeId = vehicleTypeId;
		this.vehicleTypeName = vehicleTypeName;
		this.inputTypeId = inputTypeId;
		this.inputTypeName = inputTypeName;
		this.isMandatory = isMandatory;
		this.isActive = isActive;
		this.optionId = optionId;
		this.optionText = optionText;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public String getQuestion() {
		return question;
	}

	public String getHelpText() {
		return helpText;
	}

	public Integer getQuestionType() {
		return questionType;
	}

	public Integer getVehicleTypeId() {
		return vehicleTypeId;
	}

	public String getVehicleTypeName() {
		return vehicleTypeName;
	}

	public Integer getInputTypeId() {
		return inputTypeId;
	}

	public String getInputTypeName() {
		return inputTypeName;
	}

	public Boolean getIsMandatory() {
		return isMandatory;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public Integer getOptionId() {
		return optionId;
	}

	public String getOptionText() {
		return optionText;
	}
}
