package com.onerivet.model.entity;

import java.time.LocalDateTime;
import com.onerivet.util.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "[Question]", schema = Constants.QUESTION_SCHEMA)
public class Question extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "[QuestionId]")
	private Integer questionId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[VehicleTypeId]")
	private VehicleType vehicleType;

	@Column(name = "[QuestionType]")
	private Integer questionType;

	@Column(name = "[Question]")
	private String question;

	@Column(name = "[HelpText]")
	private String helpText;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[InputTypeId]")
	private InputType inputType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[StatusId]")
	private Status status;

	@Column(name = "[IsMandatory]")
	private Boolean isMandatory;

	@Column(name = "[IsActive]")
	private Boolean isActive;

	@Column(name = "[DeletedDate]")
	private LocalDateTime deletedDate;

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
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

	public InputType getInputType() {
		return inputType;
	}

	public void setInputType(InputType inputType) {
		this.inputType = inputType;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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

	public LocalDateTime getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(LocalDateTime deletedDate) {
		this.deletedDate = deletedDate;
	}

}