package com.onerivet.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.onerivet.exception.BadRequestException;
import com.onerivet.exception.ResourceNotFoundException;
import com.onerivet.model.projection.OptionDependencyProjection;
import com.onerivet.repository.OptionDependencyRepository;
import com.onerivet.util.ErrorMessageConstants;

@Service
public class OptionDependencyService {

	private static final Logger logger = LoggerFactory.getLogger(OptionDependencyService.class);

	private final OptionDependencyRepository optionDependencyRepository;

	public OptionDependencyService(OptionDependencyRepository optionDependencyRepository) {

		this.optionDependencyRepository = optionDependencyRepository;
	}

	public List<OptionDependencyProjection> getManufacturers(Integer questionId) {

		validateQuestionId(questionId);

		List<OptionDependencyProjection> manufacturers = optionDependencyRepository
				.getManufacturersByQuestionId(questionId);

		if (manufacturers.isEmpty()) {
			logger.warn("No manufacturers found for questionId: {}", questionId);

			throw new ResourceNotFoundException( ErrorMessageConstants.NO_MANUFACTURER_FOUND+ questionId);
		}

		return manufacturers;
	}

	public List<OptionDependencyProjection> getModels(Integer questionId, Integer manufacturerId) {

		validateQuestionId(questionId);
		validateManufacturerId(manufacturerId); 

		List<OptionDependencyProjection> models = optionDependencyRepository.getModelsByManufacturer(questionId,
				manufacturerId);

		if (models.isEmpty()) {
			logger.warn("No models found for questionId: {} and manufacturerId: {}", questionId, manufacturerId);

			throw new ResourceNotFoundException(
					ErrorMessageConstants.NO_MODEL_FOUND + manufacturerId + ErrorMessageConstants.QUESTION_ID + questionId);
		}

		return models;
	}

	private void validateQuestionId(Integer questionId) {

		if (questionId == null || questionId <= 0) {
			logger.warn("Invalid questionId received: {}", questionId);

			throw new BadRequestException(ErrorMessageConstants.INVALID_PARENT_QUESTION_ID);
		}
	}

	private void validateManufacturerId(Integer manufacturerId) {

		if (manufacturerId == null || manufacturerId <= 0) {
			logger.warn("Invalid manufacturerId received: {}", manufacturerId);

			throw new BadRequestException(ErrorMessageConstants.INVALID_MANUFACTURER_ID);
		}
	}
}