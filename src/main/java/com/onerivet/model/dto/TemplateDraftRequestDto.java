package com.onerivet.model.dto;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
public class TemplateDraftRequestDto {
	
    private String templateId;
    
    @NotBlank(message = "Template Name is Required")
    @Size(min = 3, max = 30, message = "Template Name should be between 3 to 30 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+(?: [a-zA-Z0-9]+)*$", message = "Template Name accepts alphanumeric characters and spaces only")
    private String templateName;
    
    @NotNull(message = "Please select vehicle type")
    private Integer vehicleTypeId;
    
    @Valid
    private List<PlanDraftRequestDto> plans;
    
    public String getTemplateId() {
        return templateId;
    } 
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
    public String getTemplateName() {
        return templateName;
    }
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
    public Integer getVehicleTypeId() {
        return vehicleTypeId;
    }
    public void setVehicleTypeId(Integer vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }
    public List<PlanDraftRequestDto> getPlans() {
        return plans;
    }
    public void setPlans(List<PlanDraftRequestDto> plans) {
        this.plans = plans;
    }
}