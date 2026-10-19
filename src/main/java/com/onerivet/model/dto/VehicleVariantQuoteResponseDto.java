package com.onerivet.model.dto;

public class VehicleVariantQuoteResponseDto {

    private Integer vehicleRegistrationYear;
    private Integer vehicleVariantId;
    private String vehicleVariant;

    public VehicleVariantQuoteResponseDto(
            Integer vehicleRegistrationYear,
            Integer vehicleVariantId,
            String vehicleVariant) {

        this.vehicleRegistrationYear = vehicleRegistrationYear;
        this.vehicleVariantId = vehicleVariantId;
        this.vehicleVariant = vehicleVariant;
    }

    public Integer getVehicleRegistrationYear() {
        return vehicleRegistrationYear;
    }

    public Integer getVehicleVariantId() {
        return vehicleVariantId;
    }

    public String getVehicleVariant() {
        return vehicleVariant;
    }
}

