package com.onerivet.model.dto;

import java.math.BigDecimal;

public class PaymentDetailsResponse {

	private Integer paymentId;
	private String paymentStatus;
	private BigDecimal totalPremium;
	private String transactionId;

	public PaymentDetailsResponse(Integer paymentId, String paymentStatus, BigDecimal totalPremium,
			String transactionId) {
		this.paymentId = paymentId;
		this.paymentStatus = paymentStatus;
		this.totalPremium = totalPremium;
		this.transactionId = transactionId;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public BigDecimal getTotalPremium() {
		return totalPremium;
	}

	public String getTransactionId() {
		return transactionId;
	}
}
