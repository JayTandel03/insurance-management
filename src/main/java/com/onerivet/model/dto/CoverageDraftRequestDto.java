package com.onerivet.model.dto;
import java.math.BigDecimal;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
public class CoverageDraftRequestDto {
    private Integer coverageId;
    @Size(min = 3, max = 50, message = "Coverage Name should be between 3 to 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+(?: [a-zA-Z0-9]+)*$", message = "Coverage Name accepts alphanumeric characters and spaces only")
    private String coverageName;
    private BigDecimal basePrice;
    @NotNull(message = "Coverage standard flag is required")
    private Boolean isStandard;
    @Valid
    private List<CoverageTypeDraftRequestDto> coverageTypes;
    @Valid
    private List<AddOnDraftRequestDto> addOns;
    public Integer getCoverageId() {
        return coverageId;
    }

    public void setCoverageId(Integer coverageId) {
        this.coverageId = coverageId;
    }
    public String getCoverageName() {
        return coverageName;
    }
    public void setCoverageName(String coverageName) {
        this.coverageName = coverageName;
    }
 
    public BigDecimal getBasePrice() {
        return basePrice;
    }
    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }
    public Boolean getIsStandard() {
        return isStandard;
    }
    public void setIsStandard(Boolean isStandard) {
        this.isStandard = isStandard;
    }
    public List<CoverageTypeDraftRequestDto> getCoverageTypes() {
        return coverageTypes;
    }
    public void setCoverageTypes(List<CoverageTypeDraftRequestDto> coverageTypes) {
        this.coverageTypes = coverageTypes;
    }
    public List<AddOnDraftRequestDto> getAddOns() {
        return addOns;
    }
    public void setAddOns(List<AddOnDraftRequestDto> addOns) {
        this.addOns = addOns;
    }
}