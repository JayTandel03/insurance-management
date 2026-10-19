package com.onerivet.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.onerivet.model.entity.VehicleVariant;
import com.onerivet.model.projection.VehicleVariantProjection;

@Repository
public interface VehicleVariantRepository extends JpaRepository<VehicleVariant, Integer> {

	@Query("""
			SELECT new com.onerivet.model.projection.VehicleVariantProjection(
				vv.vehicleVariantId,
				vv.vehicleVariant
			)
			FROM VehicleVariant vv
			WHERE vv.vehicleType.vehicleTypeId = :vehicleTypeId
			AND vv.deletedDate IS NULL
			ORDER BY vv.vehicleVariantId
			""")
	List<VehicleVariantProjection> getVariantsByVehicleTypeId(Integer vehicleTypeId);
	
}