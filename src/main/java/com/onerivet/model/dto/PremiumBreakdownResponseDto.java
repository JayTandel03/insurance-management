package com.onerivet.model.dto;

import java.math.BigDecimal;
import java.util.List;

public class PremiumBreakdownResponseDto {

	private String quoteId;
	private String transactionId;
	private BigDecimal planFactor;
	private BigDecimal carAgeFactor;
	private BigDecimal carVehicleVariantFactor;
	private BigDecimal motorAgeFactor;
	private BigDecimal motorVehicleVariantFactor;
	private BigDecimal totalPremium;
	private List<CoveragePremiumDto> coverages;
	private List<AddOnPremiumDto> addOns;

	public String getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public BigDecimal getPlanFactor() {
		return planFactor;
	}

	public void setPlanFactor(BigDecimal planFactor) {
		this.planFactor = planFactor;
	}

	public BigDecimal getCarAgeFactor() {
		return carAgeFactor;
	}

	public void setCarAgeFactor(BigDecimal carAgeFactor) {
		this.carAgeFactor = carAgeFactor;
	}

	public BigDecimal getCarVehicleVariantFactor() {
		return carVehicleVariantFactor;
	}

	public void setCarVehicleVariantFactor(BigDecimal carVehicleVariantFactor) {
		this.carVehicleVariantFactor = carVehicleVariantFactor;
	}

	public BigDecimal getMotorAgeFactor() {
		return motorAgeFactor;
	}

	public void setMotorAgeFactor(BigDecimal motorAgeFactor) {
		this.motorAgeFactor = motorAgeFactor;
	}

	public BigDecimal getMotorVehicleVariantFactor() {
		return motorVehicleVariantFactor;
	}

	public void setMotorVehicleVariantFactor(BigDecimal motorVehicleVariantFactor) {
		this.motorVehicleVariantFactor = motorVehicleVariantFactor;
	}

	public BigDecimal getTotalPremium() {
		return totalPremium;
	}

	public void setTotalPremium(BigDecimal totalPremium) {
		this.totalPremium = totalPremium;
	}

	public List<CoveragePremiumDto> getCoverages() {
		return coverages;
	}

	public void setCoverages(List<CoveragePremiumDto> coverages) {
		this.coverages = coverages;
	}

	public List<AddOnPremiumDto> getAddOns() {
		return addOns;
	}

	public void setAddOns(List<AddOnPremiumDto> addOns) {
		this.addOns = addOns;
	}
}