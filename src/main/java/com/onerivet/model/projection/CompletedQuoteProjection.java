package com.onerivet.model.projection;
import java.math.BigDecimal;
public class CompletedQuoteProjection {
	private String quoteId;
	private String carName;
	private Integer carRegistrationYear;
	private String motorCycleName;
	private Integer motorCycleRegistrationYear;
	private String templateId;
	private String templateName;
	private Integer planId; 
	private String planName;
	private Integer paymentId;
	private BigDecimal totalPremium;
	private String transactionId;
	public CompletedQuoteProjection(String quoteId, String carName, Integer carRegistrationYear,
									String motorCycleName, Integer motorCycleRegistrationYear, String templateId, String templateName,
									Integer planId, String planName, Integer paymentId, BigDecimal totalPremium, String transactionId) {
		this.quoteId = quoteId;
		this.carName = carName;
		this.carRegistrationYear = carRegistrationYear;
		this.motorCycleName = motorCycleName;
		this.motorCycleRegistrationYear = motorCycleRegistrationYear;
		this.templateId = templateId;
		this.templateName = templateName;
		this.planId = planId;
		this.planName = planName;
		this.paymentId = paymentId;
		this.totalPremium = totalPremium;
		this.transactionId = transactionId;
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
	public String getTemplateId() {
		return templateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public Integer getPlanId() {
		return planId;
	}
	public String getPlanName() {
		return planName;
	}
	public Integer getPaymentId() {
		return paymentId;
	}
	public BigDecimal getTotalPremium() {
		return totalPremium;
	}
	public String getTransactionId() {
		return transactionId;
	}
}