package com.onerivet.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.onerivet.util.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "[Payment]", schema = Constants.QUOTE_SCHEMA)
public class Payment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "[PaymentId]")
	private Integer paymentId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[QuoteId]")
	private Quote quote;

	@Column(name = "[PaymentMethod]")
	private String paymentMethod;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[StatusId]")
	private Status status;

	@Column(name = "[TransactionId]")
	private String transactionId;

	@Column(name = "[TotalPremium]")
	private BigDecimal totalPremium;

	@Column(name = "[DeletedDate]")
	private LocalDateTime deletedDate;

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public BigDecimal getTotalPremium() {
		return totalPremium;
	}

	public void setTotalPremium(BigDecimal totalPremium) {
		this.totalPremium = totalPremium;
	}

	public LocalDateTime getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(LocalDateTime deletedDate) {
		this.deletedDate = deletedDate;
	}
}
