package com.onerivet.model.projection;

public class VehicleTypeProjection {

    private Integer vehicleTypeId;
    private String vehicle;

    public VehicleTypeProjection(Integer vehicleTypeId, String vehicle) {
        this.vehicleTypeId = vehicleTypeId;
        this.vehicle = vehicle;
    }

    public Integer getVehicleTypeId() {
        return vehicleTypeId;
    }

    public String getVehicle() {
        return vehicle;
    }
}