package com.onerivet.model.projection;

public class VehicleVariantProjection {

	private final Integer vehicleVariantId;
	private final String vehicleVariant;

	public VehicleVariantProjection(Integer vehicleVariantId, String vehicleVariant) {
		this.vehicleVariantId = vehicleVariantId;
		this.vehicleVariant = vehicleVariant;
	}

	public Integer getVehicleVariantId() {
		return vehicleVariantId;
	}

	public String getVehicleVariant() {
		return vehicleVariant;
	}
	
}