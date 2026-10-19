package com.onerivet.model.dto;

public class CompletedQuoteResponseDto {

	private String quoteId;
	private VehicleDetailsResponseDto vehicleDetails;
	private TemplateDetailsResponseDto templateDetails;
	private PaymentDetailsResponse paymentDetails;

	public CompletedQuoteResponseDto(String quoteId, VehicleDetailsResponseDto vehicleDetails,
			TemplateDetailsResponseDto templateDetails, PaymentDetailsResponse paymentDetails) {
		this.quoteId = quoteId;
		this.vehicleDetails = vehicleDetails;
		this.templateDetails = templateDetails;
		this.paymentDetails = paymentDetails;
	}

	public String getQuoteId() {
		return quoteId;
	}

	public VehicleDetailsResponseDto getVehicleDetails() {
		return vehicleDetails;
	}

	public TemplateDetailsResponseDto getTemplateDetails() {
		return templateDetails;
	}

	public PaymentDetailsResponse getPaymentDetails() {
		return paymentDetails;
	}
}