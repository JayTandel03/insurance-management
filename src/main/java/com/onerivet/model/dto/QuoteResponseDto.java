package com.onerivet.model.dto;
import java.util.List;
public class QuoteResponseDto {
	private String quoteId;
	private List<VehicleDetailsResponseDto> vehicleDetails;
	private TemplateDetailsResponseDto templateDetails;
	private PaymentDetailsResponse paymentDetails;
	public QuoteResponseDto(String quoteId, List<VehicleDetailsResponseDto> vehicleDetails,
	                        TemplateDetailsResponseDto templateDetails, PaymentDetailsResponse paymentDetails) {
		this.quoteId = quoteId;
		this.vehicleDetails = vehicleDetails;
		this.templateDetails = templateDetails;
		this.paymentDetails = paymentDetails;
	}
	public String getQuoteId() {
		return quoteId;
	}
	public List<VehicleDetailsResponseDto> getVehicleDetails() {
		return vehicleDetails;
	}
	public TemplateDetailsResponseDto getTemplateDetails() {
		return templateDetails;
	}
	public PaymentDetailsResponse getPaymentDetails() {
		return paymentDetails;
	}
}

