package com.onerivet.model.entity;

import java.time.LocalDateTime;
import com.onerivet.util.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "[InputType]", schema = Constants.REF_SCHEMA)
public class InputType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "[InputTypeId]")
	private Integer inputTypeId;
	@Column(name = "[InputType]", nullable = false)
	private String inputType;
	@Column(name = "[CreatedDate]", nullable = false)
	private LocalDateTime createdDate;
	@Column(name = "[DeletedDate]")
	private LocalDateTime deletedDate;

	

	public Integer getInputTypeId() {
		return inputTypeId;
	}

	public void setInputTypeId(Integer inputTypeId) {
		this.inputTypeId = inputTypeId;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(LocalDateTime deletedDate) {
		this.deletedDate = deletedDate;
	}
}