package com.onerivet.model.projection;

import java.math.BigDecimal;

public class PaymentProjection {

	private final String transactionId;
	private final BigDecimal totalPremium;

	public PaymentProjection(String transactionId, BigDecimal totalPremium) {
		this.transactionId = transactionId;
		this.totalPremium = totalPremium;
	} 

	public String getTransactionId() {
		return transactionId; 
	}

	public BigDecimal getTotalPremium() {
		return totalPremium;
	}
}