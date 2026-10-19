package com.onerivet.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.onerivet.model.dto.CoverageSimpleResponseDto;
import com.onerivet.model.dto.GetAllTemplateResponseDto;
import com.onerivet.model.dto.TemplateCoverageResponseDto;
import com.onerivet.model.dto.TemplateDraftRequestDto;
import com.onerivet.model.dto.TemplateDraftResponseDto;
import com.onerivet.model.dto.TemplateResponseDto;
import com.onerivet.model.dto.response.GenericResponse;
import com.onerivet.service.TemplateCreateService;
import com.onerivet.service.TemplateService;
import com.onerivet.util.Constants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/templates")
public class TemplateController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TemplateController.class);
	private final TemplateService templateService;
	private final TemplateCreateService templateDraftService;

	public TemplateController(TemplateCreateService templateDraftService, TemplateService templateService) {
		this.templateDraftService = templateDraftService;
		this.templateService = templateService;
	}

	@PreAuthorize(Constants.HAS_ADMIN)
	@GetMapping("/{templateId}")
	public GenericResponse<TemplateResponseDto> getTemplateBasic(@PathVariable String templateId) {

		LOGGER.info("API Request: Fetch template basic details | templateId={}", templateId);

		TemplateResponseDto response = templateService.getTemplateBasic(templateId);

		LOGGER.info("API Response: Template basic fetched successfully | templateId={}", templateId);
		return new GenericResponse<>(response);
	}
 
	@PreAuthorize(Constants.HAS_ADMIN)
	@GetMapping("/{templateId}/plans/{planId}/coverages")
	public GenericResponse<List<CoverageSimpleResponseDto>> getCoveragesByPlan(@PathVariable String templateId,
			@PathVariable Integer planId) {

		LOGGER.info("API Request: Fetch coverages | templateId={}, planId={}", templateId, planId);

		List<CoverageSimpleResponseDto> responseList = templateService.getCoverages(templateId, planId);

		LOGGER.info("API Response: Coverages fetched successfully | count={}", responseList.size());

		return new GenericResponse<>(responseList);
	}
	
	@PreAuthorize(Constants.HAS_ADMIN)
	@GetMapping("/{templateId}/plans/{planId}/coverages/{coverageId}")
	public GenericResponse<TemplateCoverageResponseDto> getCoverageDetails(@PathVariable String templateId,
			@PathVariable Integer planId, @PathVariable Integer coverageId) {

		LOGGER.info("API Request: Fetch coverage details | templateId={}, planId={}, coverageId={}", templateId, planId,
				coverageId);

		TemplateCoverageResponseDto response = templateService.getCoverageDetails(templateId, planId, coverageId);

		LOGGER.info("API Response: Coverage details fetched successfully | coverageId={}", coverageId);

		return new GenericResponse<>(response);
	}

	@PreAuthorize(Constants.HAS_ADMIN)
	@DeleteMapping("/{templateId}")
	public GenericResponse<String> deleteTemplate(@PathVariable String templateId) {
		LOGGER.info("request to delete Template: {}", templateId);
		try {
			templateService.deleteTemplate(templateId);
			LOGGER.info("Successfully processed deletion for Template: {}", templateId);
			return new GenericResponse<>(Constants.TEMPLATE_DELETED);
		} catch (Exception e) {
			LOGGER.error("Error in deleteTemplate controller for ID {}: {}", templateId, e.getMessage());
			throw e;
		}
	}

	@PreAuthorize(Constants.HAS_ADMIN)
	@GetMapping
	public GenericResponse<List<GetAllTemplateResponseDto>> getAllTemplates() {

		LOGGER.info("Request received to fetch all templates");

		List<GetAllTemplateResponseDto> templates = templateService.getAllTemplates();

		LOGGER.info("Successfully retrieved {} templates", templates.size());

		return new GenericResponse<>(templates);
	}

	@PreAuthorize(Constants.HAS_ADMIN)
	@PostMapping("/draft")
	@ResponseStatus(HttpStatus.CREATED)
	public GenericResponse<TemplateDraftResponseDto> createDraftTemplate(
			@Valid @RequestBody TemplateDraftRequestDto request) {

		LOGGER.info("Create/Update Draft Template API called");

		TemplateDraftResponseDto response = templateDraftService.createOrUpdateDraft(request); 

		LOGGER.info("Draft template processed successfully. TemplateId: {}", response.getTemplateId());

		return new GenericResponse<>(response);
	}

	@PreAuthorize(Constants.HAS_ADMIN)
	@PutMapping("/{templateId}/publish")
	public GenericResponse<TemplateDraftResponseDto> publishTemplate(@PathVariable String templateId) {

		LOGGER.info("Publish Template API called for templateId: {}", templateId);

		TemplateDraftResponseDto response = templateDraftService.publishTemplate(templateId);

		LOGGER.info("Template published successfully. templateId: {}", templateId);

		return new GenericResponse<>(response);
	}
}