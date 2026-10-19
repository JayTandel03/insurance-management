package com.onerivet.model.dto;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
public class ComponentDraftRequestDto {
    private Integer componentId;
    @Size(min = 3, max = 50, message = "Component Name should be between 3 to 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+(?: [a-zA-Z0-9]+)*$", message = "Component Name accepts alphanumeric characters and spaces only")
    private String componentName;
    @Valid
    private List<FieldDraftRequestDto> fields;
    public Integer getComponentId() {
        return componentId;
    }
    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }
    public String getComponentName() {
        return componentName;

    }
    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }
    
    public List<FieldDraftRequestDto> getFields() {
        return fields;
    }
    public void setFields(List<FieldDraftRequestDto> fields) {
        this.fields = fields;
    }
}