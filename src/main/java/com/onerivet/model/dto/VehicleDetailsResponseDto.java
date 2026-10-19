package com.onerivet.model.dto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonPropertyOrder({"vehicleType",
		"vehicleName",
		"vehicleRegistrationYear"
})
public class VehicleDetailsResponseDto {
	private String vehicleType;
	private String vehicleName;
	private Integer vehicleRegistrationYear;
	public VehicleDetailsResponseDto( String vehicleType,String vehicleName, Integer vehicleRegistrationYear) {
		this.vehicleType = vehicleType;
		this.vehicleName = vehicleName;
		this.vehicleRegistrationYear = vehicleRegistrationYear;
	}
	public String getVehicleName() {
		return vehicleName;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public Integer getVehicleRegistrationYear() {
		return vehicleRegistrationYear;
	}
}