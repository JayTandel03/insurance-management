package com.onerivet.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.onerivet.exception.BadRequestException;
import com.onerivet.exception.ResourceNotFoundException;
import com.onerivet.model.projection.VehicleVariantProjection;
import com.onerivet.repository.VehicleVariantRepository;
import com.onerivet.util.ErrorMessageConstants;

@Service
public class VehicleVariantService {

	private static final Logger logger = LoggerFactory.getLogger(VehicleVariantService.class);

	private final VehicleVariantRepository vehicleVariantRepository;

	public VehicleVariantService(VehicleVariantRepository vehicleVariantRepository) {
		this.vehicleVariantRepository = vehicleVariantRepository;
	}

	public List<VehicleVariantProjection> getVariantsByVehicleTypeId(Integer vehicleTypeId) {

		validateVehicleTypeId(vehicleTypeId);

		List<VehicleVariantProjection> vehicleVariants = vehicleVariantRepository
				.getVariantsByVehicleTypeId(vehicleTypeId);

		if (vehicleVariants.isEmpty()) {
			logger.warn("No vehicle variants found for vehicleTypeId: {}", vehicleTypeId);

			throw new ResourceNotFoundException(ErrorMessageConstants.VEHICLE_VARIANTS_NOT_FOUND);
		}

		return vehicleVariants;
	}

	private void validateVehicleTypeId(Integer vehicleTypeId) {

		if (vehicleTypeId == null || vehicleTypeId <= 0) {
			logger.warn("Invalid vehicleTypeId received: {}", vehicleTypeId);

			throw new BadRequestException(ErrorMessageConstants.INVALID_VEHICLE_TYPEID);
		}
	}
	
}