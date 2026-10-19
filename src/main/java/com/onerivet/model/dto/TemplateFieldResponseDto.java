package com.onerivet.model.dto;

import java.math.BigDecimal;

public class TemplateFieldResponseDto {
	
	private Integer fieldId;
	private String field;
	private BigDecimal price;

public TemplateFieldResponseDto(Integer fieldId, String field, BigDecimal price) {
		this.fieldId = fieldId;
		this.field = field;
		this.price = price;
	}

public TemplateFieldResponseDto() {}

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
	public BigDecimal getPrice() {
	        return price;
	}

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}