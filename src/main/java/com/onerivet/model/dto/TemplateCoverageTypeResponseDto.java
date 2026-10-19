package com.onerivet.model.dto;

import java.util.List;

public class TemplateCoverageTypeResponseDto {
    private Integer coverageTypeId;
    private String coverageType;
    private List<TemplateCoverageComponentResponseDto> components;

    public TemplateCoverageTypeResponseDto() {}

    public TemplateCoverageTypeResponseDto(Integer coverageTypeId, String coverageType, List<TemplateCoverageComponentResponseDto> components) {
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

    public List<TemplateCoverageComponentResponseDto> getComponents() {
        return components;
    }

    public void setComponents(List<TemplateCoverageComponentResponseDto> components) {
        this.components = components;
    }

}