package com.onerivet.model.dto;

import java.math.BigDecimal;
import java.util.List;

public class TemplateCoverageResponseDto {
    private Integer coverageId;
    private String coverage;
    private Boolean isStandard;
    private BigDecimal basePrice;


    private List<TemplateCoverageTypeResponseDto> coverageTypes;
    private List<TemplateAddOnResponseDto> addOns;


    public TemplateCoverageResponseDto() {
    }

    public TemplateCoverageResponseDto(Integer coverageId, String coverage, BigDecimal basePrice,
                                       List<TemplateCoverageTypeResponseDto> coverageTypes) {
        this.coverageId = coverageId;
        this.coverage = coverage;
        this.basePrice = basePrice;
        this.coverageTypes = coverageTypes;
    }

    public List<TemplateAddOnResponseDto> getAddOns() {
        return addOns;
    }

    public void setAddOns(List<TemplateAddOnResponseDto> addOns) {
        this.addOns = addOns;
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

    public void setIsStandard(Boolean isStandard) {
        this.isStandard = isStandard;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public List<TemplateCoverageTypeResponseDto> getCoverageTypes() {
        return coverageTypes;
    }

    public void setCoverageTypes(List<TemplateCoverageTypeResponseDto> coverageTypes) {
        this.coverageTypes = coverageTypes;
    }
}