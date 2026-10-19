package com.onerivet.model.dto;
import java.math.BigDecimal;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
public class FieldDraftRequestDto {
    private Integer coverageFieldId;
    @Size(min = 3, max = 50, message = "Field Name should be between 3 to 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9()]+(?: [a-zA-Z0-9()]+)*$", message = "Field Name accepts alphanumeric characters and spaces only")
    private String fieldName;
    private BigDecimal basePrice;
    public Integer getCoverageFieldId() {
        return coverageFieldId;
    }
    public void setCoverageFieldId(Integer coverageFieldId) {
        this.coverageFieldId = coverageFieldId;
    }
    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    
    public BigDecimal getBasePrice() {
        return basePrice;
    }
    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }
}