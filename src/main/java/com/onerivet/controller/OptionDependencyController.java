package com.onerivet.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.onerivet.model.dto.response.GenericResponse;
import com.onerivet.model.projection.OptionDependencyProjection;
import com.onerivet.service.OptionDependencyService;
import com.onerivet.util.Constants;

@RestController
@RequestMapping("/api/vehicle-types")
public class OptionDependencyController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OptionDependencyController.class);

	private final OptionDependencyService optionDependencyService;

	public OptionDependencyController(OptionDependencyService optionDependencyService) {
		this.optionDependencyService = optionDependencyService;
	}
	@PreAuthorize(Constants.HAS_USER)
	@GetMapping("/{questionId}/manufacturers")
	public GenericResponse<List<OptionDependencyProjection>> getManufacturers(@PathVariable Integer questionId) {

		LOGGER.info("REST request to get manufacturers for question ID: {}", questionId);

		List<OptionDependencyProjection> manufacturers = optionDependencyService.getManufacturers(questionId);

		LOGGER.debug("Found {} manufacturers for question ID: {}", manufacturers.size(), questionId);

		return new GenericResponse<>(manufacturers);
	}
	@PreAuthorize(Constants.HAS_USER)
	@GetMapping("/{questionId}/manufacturers/{manufacturerId}/models")
	public GenericResponse<List<OptionDependencyProjection>> getModels(@PathVariable Integer questionId,
			@PathVariable Integer manufacturerId) {

		LOGGER.info("REST request to get models for question ID: {} and manufacturer ID: {}", questionId, manufacturerId);

		List<OptionDependencyProjection> models = optionDependencyService.getModels(questionId, manufacturerId);

		LOGGER.debug("Found {} models for question ID: {} and manufacturer ID: {}", models.size(), questionId,
				manufacturerId);

		return new GenericResponse<>(models);
	}
}