package com.onerivet.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onerivet.model.entity.VehicleAgeFactor;
import com.onerivet.model.projection.VehicleAgeFactorProjection;

public interface VehicleAgeFactorRepository extends JpaRepository<VehicleAgeFactor, Integer> {

	@Query("""
			SELECT new com.onerivet.model.projection.VehicleAgeFactorProjection(
			    vaf.vehicleAgeFactor
			)
			FROM VehicleAgeFactor vaf 
			WHERE vaf.vehicleAge = :vehicleAge 
			AND vaf.deletedDate IS NULL
			""")
	Optional<VehicleAgeFactorProjection> findByVehicleAge(@Param("vehicleAge") String vehicleAge);

	@Query("""
		    SELECT v.vehicleAgeFactor
		    FROM VehicleAgeFactor v
		    WHERE v.vehicleAge = :vehicleAge
		      AND v.deletedDate IS NULL
		""")
		Optional<BigDecimal> findVehicleAgeFactorByVehicleAge(
		        @Param("vehicleAge") String vehicleAge);
}