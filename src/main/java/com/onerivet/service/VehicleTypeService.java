package com.onerivet.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.onerivet.exception.ResourceNotFoundException;
import com.onerivet.model.dto.VehicleTypeResponseDto;
import com.onerivet.model.projection.VehicleTypeProjection;
import com.onerivet.repository.VehicleTypeRepository;
import com.onerivet.util.ErrorMessageConstants;

@Service
public class VehicleTypeService {

	private static final Logger logger = LoggerFactory.getLogger(VehicleTypeService.class);

	private final VehicleTypeRepository repository;
	private final ModelMapper modelMapper;

	public VehicleTypeService(VehicleTypeRepository repository, ModelMapper modelMapper) {
		this.repository = repository;
		this.modelMapper = modelMapper;
	}

	public List<VehicleTypeResponseDto> getVehicleTypes() {

		logger.info("Fetching vehicle types");

		List<VehicleTypeProjection> vehicleTypeProjectionList = repository.findVehicleTypes();

		if (vehicleTypeProjectionList == null || vehicleTypeProjectionList.isEmpty()) {
			logger.error("No vehicle types found");
			throw new ResourceNotFoundException(ErrorMessageConstants.VEHICLETYPE_NOT_FOUND);
		}

		List<VehicleTypeResponseDto> result = vehicleTypeProjectionList.stream()
				.map(vehicleTypeProjection -> modelMapper.map(vehicleTypeProjection, VehicleTypeResponseDto.class)).toList();

		logger.info("Fetched {} vehicle types", result.size());

		return result;
	}
}