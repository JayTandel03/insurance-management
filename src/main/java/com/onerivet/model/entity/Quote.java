package com.onerivet.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.onerivet.util.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "[Quote]", schema = Constants.QUOTE_SCHEMA)
public class Quote extends BaseEntity {

	@Id
	@Column(name = "[QuoteId]")
	private String quoteId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[UserId]")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[VehicleTypeId]")
	private VehicleType vehicleType;

	@Column(name = "[CarRegistrationYear]")
	private Integer carRegistrationYear;

	@Column(name = "[MotorcycleRegistrationYear]")
	private Integer motorCycleRegistrationYear;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="[CarVariantId]")
	private VehicleVariant carVariantId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="[MotorcycleVariantId]")
	private VehicleVariant motorCycleVariantId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[TemplateId]")
	private Template template;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[PlanId]")
	private Plan plan;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[StatusId]")
	private Status status;

	@Column(name = "[TotalPremium]")
	private BigDecimal totalPremium;

	@Column(name = "[DeletedDate]")
	private LocalDateTime deletedDate;

	public String getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Integer getCarRegistrationYear() {
		return carRegistrationYear;
	}

	public void setCarRegistrationYear(Integer carRegistrationYear) {
		this.carRegistrationYear = carRegistrationYear;
	}

	public Integer getMotorCycleRegistrationYear() {
		return motorCycleRegistrationYear;
	}

	public void setMotorCycleRegistrationYear(Integer motorCycleRegistrationYear) {
		this.motorCycleRegistrationYear = motorCycleRegistrationYear;
	}

	public VehicleVariant getCarVariantId() {
		return carVariantId;
	}

	public void setCarVariantId(VehicleVariant carVariantId) {
		this.carVariantId = carVariantId;
	}

	public VehicleVariant getMotorCycleVariantId() {
		return motorCycleVariantId;
	}

	public void setMotorCycleVariantId(VehicleVariant motorCycleVariantId) {
		this.motorCycleVariantId = motorCycleVariantId;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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