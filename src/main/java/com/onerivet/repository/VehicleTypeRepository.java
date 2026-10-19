package com.onerivet.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.onerivet.model.entity.VehicleType;
import com.onerivet.model.projection.VehicleTypeProjection;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Integer> {

	@Query("""
			SELECT new com.onerivet.model.projection.VehicleTypeProjection(
			    v.vehicleTypeId,
			    v.vehicle
			)
			FROM VehicleType v
			WHERE v.deletedDate IS NULL
			""")
	List<VehicleTypeProjection> findVehicleTypes();
}