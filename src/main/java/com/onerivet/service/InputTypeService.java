package com.onerivet.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.onerivet.exception.ResourceNotFoundException;
import com.onerivet.model.projection.InputTypeProjection;
import com.onerivet.repository.InputTypeRepository;
import com.onerivet.util.ErrorMessageConstants;

@Service
public class InputTypeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InputTypeService.class);
	private final InputTypeRepository inputTypeRepository;

	public InputTypeService(InputTypeRepository inputTypeRepository) {
		this.inputTypeRepository = inputTypeRepository;
	}

	public List<InputTypeProjection> getAllInputTypes() {

		LOGGER.info("Fetching all input types");

		List<InputTypeProjection> inputTypes = inputTypeRepository.getAllInputTypes();

		if (inputTypes.isEmpty()) {

			LOGGER.warn("No input types found");

			throw new ResourceNotFoundException(ErrorMessageConstants.INPUT_TYPES_NOT_FOUND);
		}

		LOGGER.info("Successfully fetched {} input types", inputTypes.size());

		return inputTypes;
	}
}