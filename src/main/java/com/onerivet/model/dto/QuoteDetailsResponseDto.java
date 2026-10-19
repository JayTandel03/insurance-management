package com.onerivet.model.dto;

import java.math.BigDecimal;
import java.util.List;

public class QuoteDetailsResponseDto {
	private String quoteId;
	private BigDecimal totalPremium;
	private Integer vehicleTypeId;
	private String vehicle;
	private Integer carRegistrationYear;
	private Integer carVariantId;
	private Integer motorCycleRegistrationYear;
	private Integer motorCycleVariantId;
	private TemplateDetailsResponseDto template;
	private List<QuoteAnswerResponseDto> answers;
	private List<QuoteCoverageSelectionResponseDto> coverageSelections;

	public String getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}
	
	public BigDecimal getTotalPremium() {
		return totalPremium;
	}

	public void setTotalPremium(BigDecimal totalPremium) {
		this.totalPremium = totalPremium;
	}

	public Integer getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(Integer vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public Integer getCarRegistrationYear() {
		return carRegistrationYear;
	}

	public void setCarRegistrationYear(Integer carRegistrationYear) {
		this.carRegistrationYear = carRegistrationYear;
	}

	public Integer getCarVariantId() {
		return carVariantId;
	}

	public void setCarVariantId(Integer carVariantId) {
		this.carVariantId = carVariantId;
	}

	public Integer getMotorCycleRegistrationYear() {
		return motorCycleRegistrationYear;
	}

	public void setMotorCycleRegistrationYear(Integer motorCycleRegistrationYear) {
		this.motorCycleRegistrationYear = motorCycleRegistrationYear;
	}

	public Integer getMotorCycleVariantId() {
		return motorCycleVariantId;
	}

	public void setMotorCycleVariantId(Integer motorCycleVariantId) {
		this.motorCycleVariantId = motorCycleVariantId;
	}

	public TemplateDetailsResponseDto getTemplate() {
		return template;
	}

	public void setTemplate(TemplateDetailsResponseDto template) {
		this.template = template;
	}

	public List<QuoteAnswerResponseDto> getAnswers() {
		return answers;
	}

	public void setAnswers(List<QuoteAnswerResponseDto> answers) {
		this.answers = answers;
	}

	public List<QuoteCoverageSelectionResponseDto> getCoverageSelections() {
		return coverageSelections;
	}

	public void setCoverageSelections(List<QuoteCoverageSelectionResponseDto> coverageSelections) {
		this.coverageSelections = coverageSelections;
	}
}