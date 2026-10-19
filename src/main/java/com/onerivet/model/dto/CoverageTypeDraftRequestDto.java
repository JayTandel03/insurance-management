package com.onerivet.model.dto;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
public class CoverageTypeDraftRequestDto {
    private Integer coverageTypeId;
    @Size(min = 3, max = 50, message = "Coverage Type Name should be between 3 to 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+(?: [a-zA-Z0-9]+)*$", message = "Coverage Type Name accepts alphanumeric characters and spaces only")
    private String coverageTypeName;
    @Valid
    private List<ComponentDraftRequestDto> components;
    public Integer getCoverageTypeId() {
        return coverageTypeId;
    }
    public void setCoverageTypeId(Integer coverageTypeId) {
        this.coverageTypeId = coverageTypeId;
    }
    public String getCoverageTypeName() {
        return coverageTypeName;
    }
    public void setCoverageTypeName(String coverageTypeName) {
        this.coverageTypeName = coverageTypeName;
    }
    
    public List<ComponentDraftRequestDto> getComponents() {
        return components;
    }
    public void setComponents(List<ComponentDraftRequestDto> components) {
        this.components = components;
    }
}