package com.onerivet.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.onerivet.model.dto.VehicleTypeResponseDto;
import com.onerivet.model.dto.response.GenericResponse;
import com.onerivet.service.VehicleTypeService;
import com.onerivet.util.Constants;

@RestController
@RequestMapping("/api/vehicle-types")
public class VehicleTypeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(VehicleTypeController.class);

	private final VehicleTypeService vehicleTypeService;

	public VehicleTypeController(VehicleTypeService vehicleTypeService) {
		this.vehicleTypeService = vehicleTypeService;
	}
	
	@PreAuthorize(Constants.HAS_ADMIN)
	@GetMapping
	public GenericResponse<List<VehicleTypeResponseDto>> getVehicleTypes() {

		LOGGER.info("Fetching vehicle types");

		return new GenericResponse<>(vehicleTypeService.getVehicleTypes());
	}
}