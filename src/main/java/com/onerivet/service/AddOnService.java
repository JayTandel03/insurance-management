package com.onerivet.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.onerivet.exception.ResourceNotFoundException;
import com.onerivet.model.entity.Template;
import com.onerivet.model.enums.TemplateStatus;
import com.onerivet.model.projection.AddOnProjection;
import com.onerivet.repository.AddOnRepository;
import com.onerivet.repository.CoverageRepository;
import com.onerivet.util.ErrorMessageConstants;
import com.onerivet.util.TemplateHelperService;

@Service
public class AddOnService {

	private static final Logger log = LoggerFactory.getLogger(AddOnService.class);

	private final AddOnRepository addOnRepository;
	private final CoverageRepository coverageRepository;

	public AddOnService(AddOnRepository addOnRepository, CoverageRepository coverageRepository) {

		this.addOnRepository = addOnRepository;
		this.coverageRepository = coverageRepository;
	}

	public List<AddOnProjection> getAddOns(Integer coverageId) {

		log.info("Fetching add-ons for coverageId: {}", coverageId);

		validateCoverageId(coverageId);
		validateCoverageExists(coverageId);
		validateCoverageSupportsAddons(coverageId);

		List<AddOnProjection> addOns = addOnRepository.findAddOns(coverageId,TemplateStatus.PUBLISHED.name());

		log.info("Total add-ons fetched: {}", addOns.size());

		return addOns;
	}

	private void validateCoverageId(Integer coverageId) {

		if (coverageId == null || coverageId <= 0) {
			log.error("Invalid coverageId: {}", coverageId);
			throw new IllegalArgumentException(ErrorMessageConstants.INVALID_PARAMETER);
		}
	}

	private void validateCoverageExists(Integer coverageId) {

		boolean exists = coverageRepository.existsById(coverageId);
		if (!exists) {

			log.error("Coverage not found for id: {}", coverageId);
			throw new ResourceNotFoundException(ErrorMessageConstants.COVERAGE_NOT_FOUND);
		}
	}

	private void validateCoverageSupportsAddons(Integer coverageId) {

		if (coverageId <= 2) {
			log.error("Add-ons not supported for coverageId: {}", coverageId);
			throw new ResourceNotFoundException(ErrorMessageConstants.ADDONS_NOT_SUPPORTED);
		}
	}

}