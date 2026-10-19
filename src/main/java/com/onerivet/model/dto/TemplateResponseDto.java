package com.onerivet.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "templateName",
    "vehicleType",
    "vehicleTypeId",
    "plans"
})
public class TemplateResponseDto {

    private String templateName;
    private Integer vehicleTypeId;
    private String vehicleType;
    private List<PlanResponseDto> plans;

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

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public List<PlanResponseDto> getPlans() {
		return plans;
	}

	public void setPlans(List<PlanResponseDto> plans) {
		this.plans = plans;
	}
}
