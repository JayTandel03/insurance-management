package com.onerivet.model.dto;

import com.onerivet.util.Constants;
import com.onerivet.util.ErrorMessageConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class PaymentRequestDto {

	@NotNull(message = ErrorMessageConstants.SELECT_PAYMENT_METHOD)
	private String paymentMethod;

	@NotBlank(message = ErrorMessageConstants.CARD_NUMBER_REQUIRED)
	@Pattern(regexp = Constants.CARD_NUMBER_REGEX, message = ErrorMessageConstants.INVALID_CARD_NUMBER)
	private String cardNumber;

	@NotBlank(message = ErrorMessageConstants.CARD_HOLDER_NAME_REQUIRED)
	@Pattern(regexp = Constants.CARD_HOLDER_NAME_REGEX, message = ErrorMessageConstants.INVALID_CARD_HOLDER_NAME)
	private String cardHolderName;

	@NotBlank(message = ErrorMessageConstants.EXPIRY_DATE_REQUIRED)
	@Pattern(regexp = Constants.EXPIRY_DATE_REGEX, message = ErrorMessageConstants.INVALID_EXPIRY_DATE)
	private String expiryDate;

	@NotBlank(message = ErrorMessageConstants.CVV_REQUIRED)
	@Pattern(regexp = Constants.CVV_REGEX, message = ErrorMessageConstants.INVALID_CVV)
	private String cvv;

	public PaymentRequestDto() {
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
}