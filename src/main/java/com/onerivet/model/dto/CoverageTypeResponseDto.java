package com.onerivet.model.dto;

import java.util.List;

public class CoverageTypeResponseDto {
	private Integer coverageTypeId;
	private String coverageType;
	private List<CoverageComponentResponseDto> components;

    public CoverageTypeResponseDto() {}

	public CoverageTypeResponseDto(Integer coverageTypeId, String coverageType,
			List<CoverageComponentResponseDto> components) {
		this.coverageTypeId = coverageTypeId;
		this.coverageType = coverageType;
		this.components = components;
	}

    public Integer getCoverageTypeId() {
        return coverageTypeId;
    }

    public void setCoverageTypeId(Integer coverageTypeId) {
        this.coverageTypeId = coverageTypeId;
    }

    public String getCoverageType() {
        return coverageType;
    }

    public void setCoverageType(String coverageType) {
        this.coverageType = coverageType;
    }

    public List<CoverageComponentResponseDto> getComponents() {
        return components;
    }

    public void setComponents(List<CoverageComponentResponseDto> components) {
        this.components = components;
    }
    
}