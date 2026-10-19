package com.onerivet.model.projection;

public class CoverageComponentProjection {

	private Integer coverageTypeId;
	private Integer componentId;
	private String component;
	private Integer coverageFieldId;
	private String coverageField;

	public CoverageComponentProjection(Integer coverageTypeId, Integer componentId, String component,Integer coverageFieldId, String coverageField) {
		this.coverageTypeId = coverageTypeId;
		this.componentId = componentId;
		this.component = component;
		this.coverageFieldId = coverageFieldId;
		this.coverageField = coverageField;
	}

	public Integer getCoverageTypeId() {
		return coverageTypeId;
	}

	public Integer getComponentId() {
		return componentId;
	}

	public String getComponent() {
		return component;
	}

	public Integer getCoverageFieldId() {
		return coverageFieldId;
	}

	public String getCoverageField() {
		return coverageField;
	}

}