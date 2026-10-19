package com.onerivet.model.dto;

import java.util.List;

public class CoverageComponentResponseDto {
	private Integer componentId;
	private String component;
	private List<CoverageFieldResponseDto> fields;
	
	public CoverageComponentResponseDto() {}

	public CoverageComponentResponseDto(Integer componentId, String component,List<CoverageFieldResponseDto> fields) {
		this.componentId = componentId;
		this.component = component;
		this.fields = fields;
	}

	public Integer getComponentId() {
		return componentId;
	}

	public void setComponentId(Integer componentId) {
		this.componentId = componentId;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public List<CoverageFieldResponseDto> getFields() {
		return fields;
	}

	public void setFields(List<CoverageFieldResponseDto> fields) {
		this.fields = fields;
	}

}