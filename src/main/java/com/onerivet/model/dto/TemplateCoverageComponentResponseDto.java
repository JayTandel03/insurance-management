package com.onerivet.model.dto;

import java.util.List;

public class TemplateCoverageComponentResponseDto {
    private Integer componentId;
    private String component;
    private List<TemplateFieldResponseDto> fields;

    public TemplateCoverageComponentResponseDto() {}

    public TemplateCoverageComponentResponseDto(Integer componentId, String component,List<TemplateFieldResponseDto> fields) {
        this.componentId = componentId;
        this.component = component;
        this.fields = fields;
    }

    public Integer getComponentId() {
        return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public List<TemplateFieldResponseDto> getFields() {
        return fields;
    }

    public void setFields(List<TemplateFieldResponseDto> fields) {
        this.fields = fields;
    }
}