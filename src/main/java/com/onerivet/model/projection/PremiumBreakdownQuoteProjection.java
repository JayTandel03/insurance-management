package com.onerivet.model.projection;

import java.math.BigDecimal;

public class PremiumBreakdownQuoteProjection {
	private final String quoteId;
	private final Integer carRegistrationYear;
	private final Integer motorCycleRegistrationYear;
	private final BigDecimal planFactor;
	private final BigDecimal carVariantFactor;
	private final BigDecimal motorVariantFactor;

	public PremiumBreakdownQuoteProjection(String quoteId, Integer carRegistrationYear,
			Integer motorCycleRegistrationYear, BigDecimal planFactor, BigDecimal carVariantFactor,
			BigDecimal motorVariantFactor) {
		this.quoteId = quoteId;
		this.carRegistrationYear = carRegistrationYear;
		this.motorCycleRegistrationYear = motorCycleRegistrationYear;
		this.planFactor = planFactor;
		this.carVariantFactor = carVariantFactor;
		this.motorVariantFactor = motorVariantFactor;
	}

	public String getQuoteId() {
		return quoteId;
	}

	public Integer getCarRegistrationYear() {
		return carRegistrationYear;
	}

	public Integer getMotorCycleRegistrationYear() {
		return motorCycleRegistrationYear;
	}

	public BigDecimal getPlanFactor() {
		return planFactor;
	}

	public BigDecimal getCarVariantFactor() {
		return carVariantFactor;
	}

	public BigDecimal getMotorVariantFactor() {
		return motorVariantFactor;
	}
}