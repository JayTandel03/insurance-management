package com.onerivet.model.dto;

import java.util.List;

public class CoverageResponseDto {
    private Integer coverageId;
    private String coverage;
    private Boolean isStandard;

    private List<CoverageTypeResponseDto> coverageTypes;

    public CoverageResponseDto() {}

    public CoverageResponseDto(Integer coverageId, String coverage, Boolean isStandard, List<CoverageTypeResponseDto> coverageTypes) {
        this.coverageId = coverageId;
        this.coverage = coverage;
        this.isStandard = isStandard;
        this.coverageTypes = coverageTypes;
    }

    public Integer getCoverageId() {
        return coverageId;
    }

    public void setCoverageId(Integer coverageId) {
        this.coverageId = coverageId;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public Boolean getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(Boolean standard) {
        isStandard = standard;
    }

    public List<CoverageTypeResponseDto> getCoverageTypes() {
        return coverageTypes;
    }

    public void setCoverageTypes(List<CoverageTypeResponseDto> coverageTypes) {
        this.coverageTypes = coverageTypes;
    }
}