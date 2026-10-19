package com.onerivet.model.projection;

import java.math.BigDecimal;

public class TemplateProjection {

	private String templateId;
	private String templateName;
	private Integer vehicleTypeId;
	private String vehicleType;
	private Integer planId;
	private String planName;

	private Integer coverageId;
	private String coverageName;
	private Boolean isStandard;
	private BigDecimal coveragePrice;

	private Integer coverageTypeId;
	private String coverageTypeName;

	private Integer componentId;
	private String componentName;

	private Integer fieldId;
	private String fieldName;
	private BigDecimal fieldPrice;

	private Integer addOnId;
	private String addOnName;
	private BigDecimal addOnPrice;
	private Integer statusId;
	private String statusName;
	private AuditProjection auditProjection;

	public TemplateProjection(String templateId, String templateName, Integer vehicleTypeId, String vehicleType,
	                          Integer planId, String planName) {
		this.templateId = templateId;
		this.templateName = templateName;
		this.vehicleTypeId = vehicleTypeId;
		this.vehicleType = vehicleType;
		this.planId = planId;
		this.planName = planName;
	}

	public TemplateProjection(Integer coverageId, String coverageName, Boolean isStandard, BigDecimal coveragePrice) {
		this.coverageId = coverageId;
		this.coverageName = coverageName;
		this.isStandard = isStandard;
		this.coveragePrice = coveragePrice;
	}

	public TemplateProjection(Integer coverageId, String coverageName, Boolean isStandard, BigDecimal coveragePrice, Integer coverageTypeId,
	                          String coverageTypeName, Integer componentId, String componentName, Integer fieldId, String fieldName,
	                          BigDecimal fieldPrice, Integer addOnId, String addOnName, BigDecimal addOnPrice) {

		this.coverageId = coverageId;
		this.coverageName = coverageName;
		this.isStandard = isStandard;
		this.coveragePrice = coveragePrice;

		this.coverageTypeId = coverageTypeId;
		this.coverageTypeName = coverageTypeName;

		this.componentId = componentId;
		this.componentName = componentName;

		this.fieldId = fieldId;
		this.fieldName = fieldName;
		this.fieldPrice = fieldPrice;

		this.addOnId = addOnId;
		this.addOnName = addOnName;
		this.addOnPrice = addOnPrice;
	}
	
	public TemplateProjection(String templateId, String templateName, Integer vehicleTypeId, String vehicleType,
			Integer statusId, String statusName, AuditProjection auditProjection) {
		this.templateId = templateId;
		this.templateName = templateName;
		this.vehicleTypeId = vehicleTypeId;
		this.vehicleType = vehicleType;
		this.statusId = statusId;
		this.statusName = statusName;
		this.auditProjection = auditProjection;
	}

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

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

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

	public Boolean getIsStandard() {
		return isStandard;
	}

	public void setIsStandard(Boolean isStandard) {
		this.isStandard = isStandard;
	}

	public BigDecimal getCoveragePrice() {
		return coveragePrice;
	}

	public void setCoveragePrice(BigDecimal coveragePrice) {
		this.coveragePrice = coveragePrice;
	}

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

	public Integer getFieldId() {
		return fieldId;
	}

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public BigDecimal getFieldPrice() {
		return fieldPrice;
	}

	public void setFieldPrice(BigDecimal fieldPrice) {
		this.fieldPrice = fieldPrice;
	}

	public Integer getAddOnId() {
		return addOnId;
	}

	public void setAddOnId(Integer addOnId) {
		this.addOnId = addOnId;
	}

	public String getAddOnName() {
		return addOnName;
	}

	public void setAddOnName(String addOnName) {
		this.addOnName = addOnName;
	}

	public BigDecimal getAddOnPrice() {
		return addOnPrice;
	}

	public void setAddOnPrice(BigDecimal addOnPrice) {
		this.addOnPrice = addOnPrice;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public AuditProjection getAuditProjection() {
		return auditProjection;
	}

	public void setAuditProjection(AuditProjection auditProjection) {
		this.auditProjection = auditProjection;
	}
}	