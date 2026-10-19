package com.onerivet.model.projection;
public class InCompleteQuoteProjection {
	private String quoteId;
	private String carName;
	private Integer carRegistrationYear;
	private String motorCycleName;
	private Integer motorCycleRegistrationYear;
	public InCompleteQuoteProjection(String quoteId, String carName, Integer carRegistrationYear,
									 String motorCycleName, Integer motorCycleRegistrationYear) {
		this.quoteId = quoteId;
		this.carName = carName;
		this.carRegistrationYear = carRegistrationYear;
		this.motorCycleName = motorCycleName;
		this.motorCycleRegistrationYear = motorCycleRegistrationYear;
	}
	public String getQuoteId() {
		return quoteId;
	}
	public String getCarName() {
		return carName;
	}
	public Integer getCarRegistrationYear() {
		return carRegistrationYear;
	}
	public String getMotorCycleName() {
		return motorCycleName;
	}
	public Integer getMotorCycleRegistrationYear() {
		return motorCycleRegistrationYear;
	}
}