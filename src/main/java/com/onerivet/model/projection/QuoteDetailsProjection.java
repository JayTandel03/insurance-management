package com.onerivet.model.projection;

import java.math.BigDecimal;

public class QuoteDetailsProjection {
	private final String quoteId;
	private final Integer vehicleTypeId;
	private final String vehicle;
	private final Integer carRegistrationYear;
	private final Integer motorCycleRegistrationYear;
	private final Integer carVariantId;
	private final Integer motorCycleVariantId;
	private final String templateId;
	private final String templateName;
	private final Integer planId;
	private final String planName;
	private final BigDecimal totalPremium;

	public QuoteDetailsProjection(String quoteId, Integer vehicleTypeId, String vehicle, Integer carRegistrationYear,
	                              Integer motorCycleRegistrationYear, Integer carVariantId, Integer motorCycleVariantId, String templateId,
	                              String templateName, Integer planId, String planName, BigDecimal totalPremium) {
		this.quoteId = quoteId;
		this.vehicleTypeId = vehicleTypeId;
		this.vehicle = vehicle;
		this.carRegistrationYear = carRegistrationYear;
		this.motorCycleRegistrationYear = motorCycleRegistrationYear;
		this.carVariantId = carVariantId;
		this.motorCycleVariantId = motorCycleVariantId;
		this.templateId = templateId;
		this.templateName = templateName;
		this.planId = planId;
		this.planName = planName;
		this.totalPremium = totalPremium;
	}

	public String getQuoteId() {
		return quoteId;
	}

	public Integer getVehicleTypeId() {
		return vehicleTypeId;
	}

	public String getVehicle() {
		return vehicle;
	}

	public Integer getCarRegistrationYear() {
		return carRegistrationYear;
	}

	public Integer getMotorCycleRegistrationYear() {
		return motorCycleRegistrationYear;
	}

	public Integer getCarVariantId() {
		return carVariantId;
	}

	public Integer getMotorCycleVariantId() {
		return motorCycleVariantId;
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

	public BigDecimal getTotalPremium() {
		return totalPremium;
	}
}