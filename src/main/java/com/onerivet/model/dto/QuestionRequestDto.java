package com.onerivet.model.dto;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class QuestionRequestDto {

	private Integer questionId;

	@Positive(message = "Vehicle type id must be greater than 0")
	private Integer vehicleTypeId;

	@Positive(message = "Question type id must be greater than 0")
	@NotNull(message = "Question type is required")
	private Integer questionType;

	@NotBlank(message = "Question label is required")
	@Size(min = 15, max = 50, message = "Question label should be between 15 and 50 characters")
	@Pattern(regexp = "^(?!^[0-9]+$)[^!@#*]*$", message = "Question label cannot be purely numeric and cannot contain !, @, #, or *.")
	private String question;

	@Size(min = 30, max = 70, message = "Help text should be between 30 and 70 characters")
	@Pattern(regexp = "^(?!^[0-9]+$)[^!@#*]*$", message = "Helptext cannot be purely numeric and cannot contain !, @, #, or *.")
	private String helpText;

	@NotNull(message = "Input type is required")
	@Positive(message = "Input type id must be greater than 0")
	private Integer inputTypeId;

	@NotNull(message = "Mandatory field is required")
	private Boolean isMandatory;

	private Boolean isActive;

	private String modifiedById;

	@Valid
	private List<OptionRequestDto> options;

	public Integer getQuestionId() {
		return questionId;
	}

	public QuestionRequestDto() {

	}

	public QuestionRequestDto(Integer questionId,
			@Positive(message = "Vehicle type id must be greater than 0") Integer vehicleTypeId,
			@Positive(message = "Question type id must be greater than 0") @NotNull(message = "Question type is required") Integer questionType,
			@NotBlank(message = "Question label is required") @Size(min = 15, max = 50, message = "Question label should be between 15 and 50 characters") @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Question label only accepts alphabets and numbers") String question,
			@Size(min = 30, max = 70, message = "Help text should be between 30 and 70 characters") @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "Help text only accepts alphabets and numbers") String helpText,
			@NotNull(message = "Input type is required") @Positive(message = "Input type id must be greater than 0") Integer inputTypeId,
			@NotNull(message = "Mandatory field is required") Boolean isMandatory, Boolean isActive,
			String modifiedById, @Valid List<OptionRequestDto> options) {

		this.questionId = questionId;
		this.vehicleTypeId = vehicleTypeId;
		this.questionType = questionType;
		this.question = question;
		this.helpText = helpText;
		this.inputTypeId = inputTypeId;
		this.isMandatory = isMandatory;
		this.isActive = isActive;
		this.modifiedById = modifiedById;
		this.options = options;
	}

	public QuestionRequestDto(Integer questionId, Integer vehicleTypeId, Integer questionType, String question,
			String helpText, Integer inputTypeId, Boolean isMandatory, List<OptionRequestDto> options) {
		this.questionId = questionId;
		this.vehicleTypeId = vehicleTypeId;
		this.questionType = questionType;
		this.question = question;
		this.helpText = helpText;
		this.inputTypeId = inputTypeId;
		this.isMandatory = isMandatory;
		this.options = options;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(Integer vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public Integer getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
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

	public Integer getInputTypeId() {
		return inputTypeId;
	}

	public void setInputTypeId(Integer inputTypeId) {
		this.inputTypeId = inputTypeId;
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

	public String getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById;
	}

	public List<OptionRequestDto> getOptions() {
		return options;
	}

	public void setOptions(List<OptionRequestDto> options) {
		this.options = options;
	}

}
