package com.onerivet.model.dto;

import java.math.BigDecimal;

public class VehicleVariantDto {
	private Integer vehicleVariantId;
	private String vehicleVariant;
	private BigDecimal vehicleVariantFactor;

	public VehicleVariantDto() {
	}

	public VehicleVariantDto(Integer vehicleVariantId, String vehicleVariant, BigDecimal vehicleVariantFactor) {
		this.vehicleVariantId = vehicleVariantId;
		this.vehicleVariant = vehicleVariant;
		this.vehicleVariantFactor = vehicleVariantFactor;
	}

	public Integer getVehicleVariantId() {
		return vehicleVariantId;
	}

	public void setVehicleVariantId(Integer vehicleVariantId) {
		this.vehicleVariantId = vehicleVariantId;
	}

	public String getVehicleVariant() {
		return vehicleVariant;
	}

	public void setVehicleVariant(String vehicleVariant) {
		this.vehicleVariant = vehicleVariant;
	}

	public BigDecimal getVehicleVariantFactor() {
		return vehicleVariantFactor;
	}

	public void setVehicleVariantFactor(BigDecimal vehicleVariantFactor) {
		this.vehicleVariantFactor = vehicleVariantFactor;
	}
}
