package com.onerivet.model.dto;

public class CoverageFieldResponseDto {
	
	private Integer fieldId;
	private String field;
	
	public CoverageFieldResponseDto(Integer fieldId, String field) {
		this.fieldId = fieldId;
		this.field = field;
	}
	
	public Integer getFieldId() {
		return fieldId;
	}
	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}

}