package com.onerivet.model.dto;

import java.math.BigDecimal;

public class QuoteCreateResponseDto {

	private String message;
	private String quoteId;
	private BigDecimal totalPremium;

	public QuoteCreateResponseDto() {
	}

	public QuoteCreateResponseDto(String message, String quoteId,
			BigDecimal totalPremium) {
		this.message = message;
		this.quoteId = quoteId;
		this.totalPremium = totalPremium;
	}

	public String getMessage() {
		return message;
	}

	public String getQuoteId() {
		return quoteId;
	}

	public BigDecimal getTotalPremium() {
		return totalPremium;
	}

}