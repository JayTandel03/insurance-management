package com.onerivet.model.projection;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReceiptDetailsProjection {

    private String quoteId;
    private LocalDateTime paymentCreatedDate;
    private String transactionId;
    private String paymentMethod;
    private BigDecimal totalPremium;

    private String customerFirstName;
    private String customerLastName;
    private String address;
    private String gender;
    private LocalDate dateOfBirth;

    private Integer vehicleTypeId;

    private String carVariant;
    private BigDecimal carVariantFactor;
    private Integer carRegistrationYear;

    private String motorcycleVariant;
    private BigDecimal motorcycleVariantFactor;
    private Integer motorcycleRegistrationYear;


    private String planName;
    private BigDecimal planFactor;

    public ReceiptDetailsProjection(String quoteId, LocalDateTime paymentCreatedDate, String transactionId,
                                    String paymentMethod, BigDecimal totalPremium, String customerFirstName, String customerLastName,
                                    String address, String gender, LocalDate dateOfBirth, Integer vehicleTypeId, String carVariant,
                                    BigDecimal carVariantFactor, Integer carRegistrationYear, String motorcycleVariant,
                                    BigDecimal motorcycleVariantFactor, Integer motorcycleRegistrationYear, String planName,
                                    BigDecimal planFactor) {
        this.quoteId = quoteId;
        this.paymentCreatedDate = paymentCreatedDate;
        this.transactionId = transactionId;
        this.paymentMethod = paymentMethod;
        this.totalPremium = totalPremium;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.address = address;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.vehicleTypeId = vehicleTypeId;
        this.carVariant = carVariant;
        this.carVariantFactor = carVariantFactor;
        this.carRegistrationYear = carRegistrationYear;
        this.motorcycleVariant = motorcycleVariant;
        this.motorcycleVariantFactor = motorcycleVariantFactor;
        this.motorcycleRegistrationYear = motorcycleRegistrationYear;
        this.planName = planName;
        this.planFactor = planFactor;
    }

    public String getQuoteId() {
        return quoteId;
    }
    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }
    public LocalDateTime getPaymentCreatedDate() {
        return paymentCreatedDate;
    }
    public void setPaymentCreatedDate(LocalDateTime paymentCreatedDate) {
        this.paymentCreatedDate = paymentCreatedDate;
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
    public BigDecimal getTotalPremium() {
        return totalPremium;
    }
    public void setTotalPremium(BigDecimal totalPremium) {
        this.totalPremium = totalPremium;
    }
    public String getCustomerFirstName() {
        return customerFirstName;
    }
    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }
    public String getCustomerLastName() {
        return customerLastName;
    }
    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
    public BigDecimal getCarVariantFactor() {
        return carVariantFactor;
    }
    public void setCarVariantFactor(BigDecimal carVariantFactor) {
        this.carVariantFactor = carVariantFactor;
    }
    public Integer getCarRegistrationYear() {
        return carRegistrationYear;
    }
    public void setCarRegistrationYear(Integer carRegistrationYear) {
        this.carRegistrationYear = carRegistrationYear;
    }
    public String getMotorcycleVariant() {
        return motorcycleVariant;
    }
    public void setMotorcycleVariant(String motorcycleVariant) {
        this.motorcycleVariant = motorcycleVariant;
    }
    public BigDecimal getMotorcycleVariantFactor() {
        return motorcycleVariantFactor;
    }
    public void setMotorcycleVariantFactor(BigDecimal motorcycleVariantFactor) {
        this.motorcycleVariantFactor = motorcycleVariantFactor;
    }
    public Integer getMotorcycleRegistrationYear() {
        return motorcycleRegistrationYear;
    }
    public void setMotorcycleRegistrationYear(Integer motorcycleRegistrationYear) {
        this.motorcycleRegistrationYear = motorcycleRegistrationYear;
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

}