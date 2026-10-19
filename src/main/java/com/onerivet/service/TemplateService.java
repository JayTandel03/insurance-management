package com.onerivet.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.onerivet.model.dto.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onerivet.exception.BadRequestException;
import com.onerivet.exception.ResourceNotFoundException;
import com.onerivet.model.dto.StatusResponseDto;
import com.onerivet.model.dto.GetAllTemplateResponseDto;
import com.onerivet.model.entity.Template;
import com.onerivet.model.projection.AuditProjection;
import com.onerivet.model.projection.TemplateProjection;
import com.onerivet.model.projection.TemplateStatusProjection;
import com.onerivet.repository.TemplateRepository;
import com.onerivet.util.Constants;
import com.onerivet.util.ErrorMessageConstants;

@Service
public class TemplateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateService.class);

    private final TemplateRepository templateRepository;
    private final ModelMapper modelMapper;

    public TemplateService(TemplateRepository templateRepository, ModelMapper modelMapper) {
        this.templateRepository = templateRepository;
        this.modelMapper = modelMapper;
    }
    public com.onerivet.model.dto.TemplateResponseDto getTemplateBasic(String templateId) {

        long startTime = System.currentTimeMillis();
        LOGGER.info("Fetching template basic details for templateId={}", templateId);

        List<TemplateProjection> projectionList = templateRepository.getTemplateBasicDetails(templateId);

        if (projectionList == null || projectionList.isEmpty()) {
            LOGGER.error("Template not found for templateId={}", templateId);
            throw new ResourceNotFoundException(ErrorMessageConstants.TEMPLATE_NOT_FOUND);
        }

        TemplateProjection templateProjection = projectionList.get(0);

        com.onerivet.model.dto.TemplateResponseDto response = new com.onerivet.model.dto.TemplateResponseDto();
        response.setTemplateName(templateProjection.getTemplateName());
        response.setVehicleTypeId(templateProjection.getVehicleTypeId());
        response.setVehicleType(templateProjection.getVehicleType());

        Map<Integer, PlanResponseDto> planMap = new LinkedHashMap<>();

        for (TemplateProjection projection : projectionList) {

            planMap.computeIfAbsent(projection.getPlanId(), planId -> {

                PlanResponseDto plan = new PlanResponseDto();
                plan.setPlanId(planId);
                plan.setPlan(projection.getPlanName());
                return plan;
            });
        }

        response.setPlans(new ArrayList<>(planMap.values()));
        LOGGER.info("Template basic fetched in {} ms", System.currentTimeMillis() - startTime);
        return response;
    }

    public List<CoverageSimpleResponseDto> getCoverages(String templateId, Integer planId) {

        long startTime = System.currentTimeMillis();
        LOGGER.info("Fetching coverages for templateId={}, planId={}", templateId, planId);
        Template template = templateRepository.getTemplateByTemplateId(templateId);

        if (template == null) {
            LOGGER.error("Template not found for templateId={}", templateId);
            throw new ResourceNotFoundException(ErrorMessageConstants.TEMPLATE_NOT_FOUND);
        }

        List<TemplateProjection> projectionList = templateRepository.getCoveragesByPlan(templateId, planId);
        List<CoverageSimpleResponseDto> responseList = new ArrayList<>(projectionList.size());

        for (TemplateProjection projection : projectionList) {
            CoverageSimpleResponseDto coverage = new CoverageSimpleResponseDto();
            coverage.setCoverageId(projection.getCoverageId());
            coverage.setCoverage(projection.getCoverageName());
            coverage.setBasePrice(projection.getCoveragePrice());
            responseList.add(coverage);
        }

        LOGGER.info("Coverages fetched: {} in {} ms", responseList.size(), System.currentTimeMillis() - startTime);
        return responseList;
    }

    public TemplateCoverageResponseDto getCoverageDetails(String templateId, Integer planId, Integer coverageId) {

        long startTime = System.currentTimeMillis();
        LOGGER.info("Fetching coverage details for templateId={}, planId={}, coverageId={}", templateId, planId,
                coverageId);

        Template template = templateRepository.getTemplateByTemplateId(templateId);

        if (template == null) {
            LOGGER.error("Template not found for templateId={}", templateId);
            throw new ResourceNotFoundException(ErrorMessageConstants.TEMPLATE_NOT_FOUND);
        }

        List<TemplateProjection> projectionList = templateRepository.getCoverageDetails(templateId, planId, coverageId);

        if (projectionList == null || projectionList.isEmpty()) {
            LOGGER.error("Coverage not found for coverageId={}", coverageId);
            throw new ResourceNotFoundException(ErrorMessageConstants.COVERAGE_NOT_FOUND);
        }

        TemplateProjection baseProjection = projectionList.get(0);

        TemplateCoverageResponseDto response = new TemplateCoverageResponseDto();
        response.setCoverageId(baseProjection.getCoverageId());
        response.setCoverage(baseProjection.getCoverageName());
        response.setIsStandard(baseProjection.getIsStandard());
        response.setBasePrice(baseProjection.getCoveragePrice());

        Map<Integer, TemplateCoverageTypeResponseDto> coverageTypeMap = new LinkedHashMap<>();
        Map<Integer, TemplateCoverageComponentResponseDto> componentMap = new HashMap<>();
        Set<String> fieldTracker = new HashSet<>();
        Set<Integer> addOnTracker = new HashSet<>();

        List<TemplateAddOnResponseDto> addOnList = new ArrayList<>();

        for (TemplateProjection projection : projectionList) {

            TemplateCoverageTypeResponseDto coverageType = coverageTypeMap.computeIfAbsent(projection.getCoverageTypeId(),
                    typeId -> {

                        TemplateCoverageTypeResponseDto type = new TemplateCoverageTypeResponseDto();
                        type.setCoverageTypeId(typeId);
                        type.setCoverageType(projection.getCoverageTypeName());
                        type.setComponents(new ArrayList<>());
                        return type;
                    });

            if (projection.getComponentId() != null) {

                TemplateCoverageComponentResponseDto component = componentMap.computeIfAbsent(projection.getComponentId(),
                        componentIdKey -> {

                            TemplateCoverageComponentResponseDto comp = new TemplateCoverageComponentResponseDto();
                            comp.setComponentId(componentIdKey);
                            comp.setComponent(projection.getComponentName());
                            comp.setFields(new ArrayList<>());
                            coverageType.getComponents().add(comp);
                            return comp;
                        });

                String fieldKey = projection.getComponentId() + "_" + projection.getFieldId();
                if (projection.getFieldId() != null && fieldTracker.add(fieldKey))
                {
                    TemplateFieldResponseDto field = new TemplateFieldResponseDto();
                    field.setFieldId(projection.getFieldId());
                    field.setField(projection.getFieldName());
                    field.setPrice(projection.getFieldPrice());
                    component.getFields().add(field);
                }
            }

            if (projection.getAddOnId() != null && addOnTracker.add(projection.getAddOnId())) {

                TemplateAddOnResponseDto addOn = new TemplateAddOnResponseDto();
                addOn.setAddOnId(projection.getAddOnId());
                addOn.setAddOn(projection.getAddOnName());
                addOn.setBasePrice(projection.getAddOnPrice());
                addOnList.add(addOn);
            }
        }

        response.setCoverageTypes(new ArrayList<>(coverageTypeMap.values()));
        response.setAddOns(addOnList);
        LOGGER.info("Coverage details fetched in {} ms", System.currentTimeMillis() - startTime);
        return response;
    }

    public List<GetAllTemplateResponseDto> getAllTemplates() {

        LOGGER.info("Starting getAllTemplates process");

        List<TemplateProjection> projections = templateRepository.findAllTemplates();

        if (projections == null) {
            LOGGER.error("Template repository returned null");
            return Collections.emptyList();
        }

        if (projections.isEmpty()) {
            LOGGER.warn("Template list is empty in database");
            return Collections.emptyList();
        }

        LOGGER.info("Found {} templates to process", projections.size());

        return projections.stream().map(p -> {
            LOGGER.debug("Mapping template: {} (ID: {})", p.getTemplateName(), p.getVehicleTypeId());

            GetAllTemplateResponseDto getAllTemplateResponseDto = modelMapper.map(p, GetAllTemplateResponseDto.class);

            getAllTemplateResponseDto.setTemplateName(p.getTemplateName());

            getAllTemplateResponseDto.setVehicle(new VehicleTypeResponseDto(p.getVehicleTypeId(), p.getVehicleType()));
            getAllTemplateResponseDto.setStatus(new StatusResponseDto(p.getStatusId(), p.getStatusName()));

            AuditProjection audit = p.getAuditProjection();
            if (audit != null) {
                getAllTemplateResponseDto.setModifiedBy(audit.getName());

                getAllTemplateResponseDto.setCreatedDate(audit.getCreatedDate());
                getAllTemplateResponseDto.setModifiedDate(audit.getModifiedDate());
            } else {
                getAllTemplateResponseDto.setModifiedBy("");
            }

            return getAllTemplateResponseDto;
        }).toList();
    }

    @Transactional
    public void deleteTemplate(String templateId) {
        LOGGER.info("Attempting to delete template: {}", templateId);
        try {
            validateTemplateId(templateId);
            TemplateStatusProjection projection = templateRepository.findStatusByTemplateId(templateId)
                    .orElseThrow(() -> {
                        LOGGER.warn("Delete failed: Template {} not found", templateId);
                        return new ResourceNotFoundException(ErrorMessageConstants.TEMPLATE_NOT_FOUND);
                    });
            LOGGER.debug("Template {} current status: {}", templateId, projection.getStatus());
            validateDraftStatus(projection.getStatus());
            deleteTemplateDependencies(templateId);
            templateRepository.softDeleteTemplate(templateId);
            LOGGER.info("Template {} successfully deleted", templateId);
        } catch (BadRequestException | IllegalArgumentException e) {
            LOGGER.warn("Validation error for template {}: {}", templateId, e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.error("Unexpected error deleting template {}: ", templateId, e);
            throw e;
        }
    }

    private void validateTemplateId(String templateId) {
        if (templateId == null || templateId.trim().isEmpty()) {
            throw new IllegalArgumentException(ErrorMessageConstants.INVALID_TEMPLATE_ID);
        }
    }

    private void validateDraftStatus(String status) {
        if (!com.onerivet.model.enums.TemplateStatus.DRAFT.name().equalsIgnoreCase(status)) {
            throw new BadRequestException(ErrorMessageConstants.DRAFT_DELETE_ONLY);
        }
    }

    private void deleteTemplateDependencies(String templateId) {
        LOGGER.debug("Deleting dependencies for template: {}", templateId);
        templateRepository.softDeleteTemplateCoverageFieldPrices(templateId);
        templateRepository.softDeleteTemplateAddOnPrices(templateId);
        templateRepository.softDeleteVehicleTypeCoveragePrices(templateId);
        templateRepository.softDeleteTemplateCoverages(templateId);
        templateRepository.softDeleteTemplatePlans(templateId);
    }
}
