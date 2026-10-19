package com.onerivet.model.dto;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "coverageId", "coverage", "basePrice"})
public class CoverageSimpleResponseDto {

    private Integer coverageId;
    private String coverage;
    private BigDecimal basePrice;
 
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

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }
}