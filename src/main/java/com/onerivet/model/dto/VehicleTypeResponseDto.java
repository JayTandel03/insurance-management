package com.onerivet.model.dto;

public class VehicleTypeResponseDto {
    private Integer vehicleTypeId;
    private String vehicle;
 
    public VehicleTypeResponseDto() {}

    public VehicleTypeResponseDto(Integer vehicleTypeId, String vehicle) {
        this.vehicleTypeId = vehicleTypeId;
        this.vehicle = vehicle;
    }

    public Integer getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(Integer vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }
}