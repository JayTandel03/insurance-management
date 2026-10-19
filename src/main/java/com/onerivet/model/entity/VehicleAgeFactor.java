package com.onerivet.model.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.onerivet.util.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "[VehicleAgeFactor]", schema = Constants.REF_SCHEMA)

public class VehicleAgeFactor {

	@Id
	@Column(name = "[VehicleAgeFactorId]")
	private Integer vehicleAgeFactorId;

	@Column(name = "[VehicleAge]")
	private String vehicleAge;

	@Column(name = "[VehicleAgeFactor]")
	private String vehicleAgeFactor;

	@CreationTimestamp
	@Column(name = "[CreatedDate]")
	private LocalDateTime createdDate;

	@Column(name = "[DeletedDate]")
	private LocalDateTime deletedDate;

	public Integer getVehicleAgeFactorId() {
		return vehicleAgeFactorId;

	}

	public void setVehicleAgeFactorId(Integer vehicleAgeFactorId) {
		this.vehicleAgeFactorId = vehicleAgeFactorId;

	}

	public String getVehicleAge() {
		return vehicleAge;

	}

	public void setVehicleAge(String vehicleAge) {
		this.vehicleAge = vehicleAge;
	}

	public String getVehicleAgeFactor() {
		return vehicleAgeFactor;
	}

	public void setVehicleAgeFactor(String vehicleAgeFactor) {
		this.vehicleAgeFactor = vehicleAgeFactor;
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
}