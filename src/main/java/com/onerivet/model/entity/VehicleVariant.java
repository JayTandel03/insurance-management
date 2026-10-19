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
@Table(name = "[VehicleVariant]", schema = Constants.REF_SCHEMA)
public class VehicleVariant {

    @Id
    @Column(name = "[VehicleVariantId]")
    private Integer vehicleVariantId;
 
    @Column(name = "[VehicleVariant]")
    private String vehicleVariant;

    @Column(name = "[VehicleVariantFactor]")
    private BigDecimal vehicleVariantFactor;

    @Column(name = "[CreatedDate]")
    private LocalDateTime createdDate;

    @Column(name = "[DeletedDate]")
    private LocalDateTime deletedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "[VehicleTypeId]")
    private VehicleType vehicleType;

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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }
}

