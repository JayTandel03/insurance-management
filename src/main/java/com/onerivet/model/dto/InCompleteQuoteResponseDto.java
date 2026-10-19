package com.onerivet.model.dto;

public class InCompleteQuoteResponseDto {

	private String quoteId;
	private String vehicleName;
	private Integer vehicleRegistrationYear;

	public InCompleteQuoteResponseDto(
			String quoteId,
			String vehicleName,
			Integer vehicleRegistrationYear) {
		this.quoteId = quoteId;
		this.vehicleName = vehicleName;
		this.vehicleRegistrationYear = vehicleRegistrationYear;
	}

	public String getQuoteId() {
		return quoteId;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public Integer getVehicleRegistrationYear() {
		return vehicleRegistrationYear;
	}
}
