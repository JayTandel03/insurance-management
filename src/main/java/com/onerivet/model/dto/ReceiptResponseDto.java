package com.onerivet.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReceiptResponseDto {

	private String invoiceNumber;
	private LocalDateTime invoiceDate;
	private LocalDate paymentDate;
	private LocalDate dueDate;
	private String transactionId;
	private String paymentMethod;

	private String policyNumber;
	private LocalDate policyStartDate;
	private LocalDate policyEndDate;
	private BigDecimal totalPremiumPaid;

	private String customerName;
	private String address;
	private Integer customerAge;
	private String gender;

	private Integer insuredVehicleCount;
	private String vehicleName;
	private String vinNumber;

	private String planName;
	private BigDecimal planFactor;

	private Integer vehicleTypeId;

	private String carVariant;
	private String carAge;
	private String carVehicleAgeFactor;
	private BigDecimal carVariantFactor;

	private String motorcycleVariant;
	private String motorcycleAge;
	private String motorcycleVehicleAgeFactor;
	private BigDecimal motorcycleVariantFactor;

	private BigDecimal basePremium;

	private List<ReceiptCoverageResponseDto> coverages;
	private List<ReceiptAddOnResponseDto> addOns;

	public ReceiptResponseDto() {

	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public LocalDateTime getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(LocalDateTime invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public LocalDate getPolicyStartDate() {
		return policyStartDate;
	}

	public void setPolicyStartDate(LocalDate policyStartDate) {
		this.policyStartDate = policyStartDate;
	}

	public LocalDate getPolicyEndDate() {
		return policyEndDate;
	}

	public void setPolicyEndDate(LocalDate policyEndDate) {
		this.policyEndDate = policyEndDate;
	}

	public BigDecimal getTotalPremiumPaid() {
		return totalPremiumPaid;
	}

	public void setTotalPremiumPaid(BigDecimal totalPremiumPaid) {
		this.totalPremiumPaid = totalPremiumPaid;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getCustomerAge() {
		return customerAge;
	}

	public void setCustomerAge(Integer customerAge) {
		this.customerAge = customerAge;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getInsuredVehicleCount() {
		return insuredVehicleCount;
	}

	public void setInsuredVehicleCount(Integer insuredVehicleCount) {
		this.insuredVehicleCount = insuredVehicleCount;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getVinNumber() {
		return vinNumber;
	}

	public void setVinNumber(String vinNumber) {
		this.vinNumber = vinNumber;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public BigDecimal getPlanFactor() {
		return planFactor;
	}

	public void setPlanFactor(BigDecimal planFactor) {
		this.planFactor = planFactor;
	}

	public Integer getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(Integer vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public String getCarVariant() {
		return carVariant;
	}

	public void setCarVariant(String carVariant) {
		this.carVariant = carVariant;
	}

	public String getCarAge() {
		return carAge;
	}

	public void setCarAge(String carAge) {
		this.carAge = carAge;
	}

	public String getCarVehicleAgeFactor() {
		return carVehicleAgeFactor;
	}

	public void setCarVehicleAgeFactor(String carVehicleAgeFactor) {
		this.carVehicleAgeFactor = carVehicleAgeFactor;
	}

	public BigDecimal getCarVariantFactor() {
		return carVariantFactor;
	}

	public void setCarVariantFactor(BigDecimal carVariantFactor) {
		this.carVariantFactor = carVariantFactor;
	}

	public String getMotorcycleVariant() {
		return motorcycleVariant;
	}

	public void setMotorcycleVariant(String motorcycleVariant) {
		this.motorcycleVariant = motorcycleVariant;
	}

	public String getMotorcycleAge() {
		return motorcycleAge;
	}

	public void setMotorcycleAge(String motorcycleAge) {
		this.motorcycleAge = motorcycleAge;
	}

	public String getMotorcycleVehicleAgeFactor() {
		return motorcycleVehicleAgeFactor;
	}

	public void setMotorcycleVehicleAgeFactor(String motorcycleVehicleAgeFactor) {
		this.motorcycleVehicleAgeFactor = motorcycleVehicleAgeFactor;
	}

	public BigDecimal getMotorcycleVariantFactor() {
		return motorcycleVariantFactor;
	}

	public void setMotorcycleVariantFactor(BigDecimal motorcycleVariantFactor) {
		this.motorcycleVariantFactor = motorcycleVariantFactor;
	}

	public BigDecimal getBasePremium() {
		return basePremium;
	}

	public void setBasePremium(BigDecimal basePremium) {
		this.basePremium = basePremium;
	}

	public List<ReceiptCoverageResponseDto> getCoverages() {
		return coverages;
	}

	public void setCoverages(List<ReceiptCoverageResponseDto> coverages) {
		this.coverages = coverages;
	}

	public List<ReceiptAddOnResponseDto> getAddOns() {
		return addOns;
	}

	public void setAddOns(List<ReceiptAddOnResponseDto> addOns) {
		this.addOns = addOns;
	}

}