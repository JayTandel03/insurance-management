package com.onerivet.model.dto;

public class TemplateDetailsResponseDto {
	private String templateId;
	private String templateName;
	private Integer planId;
	private String planName;

	public TemplateDetailsResponseDto(String templateId, String templateName, Integer planId, String planName) {
		this.templateId = templateId;
		this.templateName = templateName;
		this.planId = planId;
		this.planName = planName;
	}

	public String getTemplateId() {
		return templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public Integer getPlanId() {
		return planId;
	}

	public String getPlanName() {
		return planName;
	}
}