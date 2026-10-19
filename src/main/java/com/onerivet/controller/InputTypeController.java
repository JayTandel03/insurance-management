package com.onerivet.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.onerivet.model.dto.response.GenericResponse;
import com.onerivet.model.projection.InputTypeProjection;
import com.onerivet.service.InputTypeService;
import com.onerivet.util.Constants;


@RestController
@RequestMapping("/api/input-types")
public class InputTypeController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InputTypeController.class);

	private final InputTypeService inputTypeService;

	public InputTypeController(InputTypeService inputTypeService) {
		this.inputTypeService = inputTypeService;
	}
	
	@PreAuthorize(Constants.HAS_ADMIN)
	@GetMapping
	public GenericResponse<List<InputTypeProjection>> getAllInputTypes() {
		
		LOGGER.info("Fetching Input types");

		return new GenericResponse<>(inputTypeService.getAllInputTypes());
	}
}