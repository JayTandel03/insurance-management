package com.onerivet.model.dto;

public class VehicleVariantResponseDto {
	private VehicleTypeResponseDto vehicle;
	private VehicleVariantDto vehicleVariant;

	public VehicleTypeResponseDto getVehicle() {
		return vehicle;
	} 

	public void setVehicle(VehicleTypeResponseDto vehicle) {
		this.vehicle = vehicle;
	}

	public VehicleVariantDto getVehicleVariant() {
		return vehicleVariant;
	}

	public void setVehicleVariant(VehicleVariantDto vehicleVariant) {
		this.vehicleVariant = vehicleVariant;
	}
}
