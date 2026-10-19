package com.onerivet.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.onerivet.exception.BadRequestException;
import com.onerivet.exception.ResourceAlreadyExistsException;
import com.onerivet.exception.ResourceNotFoundException;
import com.onerivet.model.dto.AddOnDraftRequestDto;
import com.onerivet.model.dto.ComponentDraftRequestDto;
import com.onerivet.model.dto.CoverageDraftRequestDto;
import com.onerivet.model.dto.CoverageTypeDraftRequestDto;
import com.onerivet.model.dto.FieldDraftRequestDto;
import com.onerivet.model.dto.PlanDraftRequestDto;
import com.onerivet.model.dto.TemplateDraftRequestDto;
import com.onerivet.model.dto.TemplateDraftResponseDto;
import com.onerivet.model.entity.AddOn;
import com.onerivet.model.entity.BaseEntity;
import com.onerivet.model.entity.Component;
import com.onerivet.model.entity.ComponentCatalog;
import com.onerivet.model.entity.ComponentFieldCatalog;
import com.onerivet.model.entity.Coverage;
import com.onerivet.model.entity.CoverageAddOnCatalog;
import com.onerivet.model.entity.CoverageField;
import com.onerivet.model.entity.CoverageType;
import com.onerivet.model.entity.CoverageTypeCatalog;
import com.onerivet.model.entity.Plan;
import com.onerivet.model.entity.Status;
import com.onerivet.model.entity.Template;
import com.onerivet.model.entity.TemplateAddOnPrice;
import com.onerivet.model.entity.TemplateCoverage;
import com.onerivet.model.entity.TemplateCoverageFieldPrice;
import com.onerivet.model.entity.TemplatePlan;
import com.onerivet.model.entity.User;
import com.onerivet.model.entity.VehicleType;
import com.onerivet.model.entity.VehicleTypeCoveragePrice;
import com.onerivet.model.enums.TemplateStatus;
import com.onerivet.model.projection.ComponentCatalogProjection;
import com.onerivet.model.projection.ComponentFieldCatalogProjection;
import com.onerivet.model.projection.CoverageTypeCatalogProjection;
import com.onerivet.model.projection.StandardCoverageProjection;
import com.onerivet.repository.ComponentCatalogRepository;
import com.onerivet.repository.ComponentFieldCatalogRepository;
import com.onerivet.repository.ComponentRepository;
import com.onerivet.service.helper.CoverageRepositoryHelper;
import com.onerivet.service.helper.MasterRepositoryHelper;
import com.onerivet.service.helper.TemplateRepositoryHelper;
import com.onerivet.util.AuthUtil;
import com.onerivet.util.Constants;
import com.onerivet.util.DateTimeHelper;
import com.onerivet.util.ErrorMessageConstants;

@Service
public class TemplateCreateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TemplateCreateService.class);

	private final TemplateRepositoryHelper templateRepositoryHelper;
	private final CoverageRepositoryHelper coverageRepositoryHelper;
	private final MasterRepositoryHelper masterRepositoryHelper;
	private final AuthUtil authUtil;
	private final ComponentRepository componentRepository;
	private final ComponentCatalogRepository componentCatalogRepository;
	private final ComponentFieldCatalogRepository componentFieldCatalogRepository;

	public TemplateCreateService(TemplateRepositoryHelper templateRepositoryHelper,
								 CoverageRepositoryHelper coverageRepositoryHelper, MasterRepositoryHelper masterRepositoryHelper,
								 AuthUtil authUtil, ComponentRepository componentRepository,
								 ComponentCatalogRepository componentCatalogRepository,
								 ComponentFieldCatalogRepository componentFieldCatalogRepository) {

		this.templateRepositoryHelper = templateRepositoryHelper;
		this.coverageRepositoryHelper = coverageRepositoryHelper;
		this.masterRepositoryHelper = masterRepositoryHelper;
		this.authUtil = authUtil;
		this.componentRepository = componentRepository;
		this.componentCatalogRepository = componentCatalogRepository;
		this.componentFieldCatalogRepository = componentFieldCatalogRepository;
	}

	@Transactional
	public TemplateDraftResponseDto createOrUpdateDraft(TemplateDraftRequestDto request) {

		LOGGER.info("createOrUpdateDraft started for templateName: {}", request.getTemplateName());

		User loggedInUser = getLoggedInUser();

		LOGGER.debug("Logged in userId: {}", loggedInUser.getUserId());

		Status draftStatus = masterRepositoryHelper.statusRepository.findByNameIgnoreCase(TemplateStatus.DRAFT.name())
				.orElseThrow(() -> {

					LOGGER.error("Draft status not found");

					throw new ResourceNotFoundException(ErrorMessageConstants.DRAFT_STATUS_NOT_FOUND);
				});

		VehicleType vehicleType = masterRepositoryHelper.vehicleTypeRepository.findById(request.getVehicleTypeId())
				.orElseThrow(() -> {

					LOGGER.error("Vehicle type not found for id: {}", request.getVehicleTypeId());

					throw new ResourceNotFoundException(ErrorMessageConstants.VEHICLE_TYPE_NOT_FOUND);
				});

		boolean isUpdate = request.getTemplateId() != null && !request.getTemplateId().isBlank();

		Template template = createOrUpdateTemplate(request, vehicleType, draftStatus, loggedInUser);

		if (request.getPlans() != null) {

			for (PlanDraftRequestDto planRequest : request.getPlans()) {

				LOGGER.debug("Processing planId: {}", planRequest.getPlanId());

				if (planRequest.getPlanId() == null) {

					LOGGER.error("Plan id is required");

					throw new BadRequestException(ErrorMessageConstants.PLAN_ID_IS_REQUIRED);
				}

				Plan plan = masterRepositoryHelper.planRepository.findById(planRequest.getPlanId()).orElseThrow(() -> {

					LOGGER.error("Plan not found for id: {}", planRequest.getPlanId());

					throw new ResourceNotFoundException(ErrorMessageConstants.PLAN_NOT_FOUND);
				});

				TemplatePlan templatePlan = createOrGetTemplatePlan(template, plan, loggedInUser);

				savePlanCoverages(templatePlan, plan, planRequest.getCoverages(), loggedInUser, draftStatus);
			}

		}

		LOGGER.info("Template draft {} successfully with templateId: {}", isUpdate ? "updated" : "created",
				template.getTemplateId());

		return new TemplateDraftResponseDto(template.getTemplateId(),
				isUpdate ? Constants.TEMPLATE_DRAFT_UPDATED : Constants.TEMPLATE_DRAFT_CREATED);
	}

	private Template createOrUpdateTemplate(TemplateDraftRequestDto request, VehicleType vehicleType,
											Status draftStatus, User loggedInUser) {

		LOGGER.debug("createOrUpdateTemplate called for templateName: {}", request.getTemplateName());

		if (request.getTemplateId() != null && !request.getTemplateId().isBlank()) {

			LOGGER.info("Updating template with id: {}", request.getTemplateId());

			Template template = templateRepositoryHelper.templateRepository.findById(request.getTemplateId())
					.orElseThrow(() -> {

						LOGGER.error("Template not found for id: {}", request.getTemplateId());

						throw new ResourceNotFoundException(ErrorMessageConstants.TEMPLATE_NOT_FOUND);
					});

			if (template.getStatus() != null
					&& TemplateStatus.PUBLISHED.name().equalsIgnoreCase(template.getStatus().getName())) {

				LOGGER.error("Published template cannot be edited. templateId: {}", template.getTemplateId());

				throw new BadRequestException(ErrorMessageConstants.PUBLISHED_TEMPLATE_CANNOT_BE_EDITED);
			}

			Optional<Template> existingTemplate = templateRepositoryHelper.templateRepository
					.findByNameIgnoreCase(request.getTemplateName());

			if (existingTemplate.isPresent()
					&& !existingTemplate.get().getTemplateId().equals(request.getTemplateId())) {

				LOGGER.error("Duplicate template name found: {}", request.getTemplateName());

				throw new ResourceAlreadyExistsException(ErrorMessageConstants.TEMPLATE_ALREADY_EXISTS);
			}

			template.setName(request.getTemplateName());
			template.setVehicleType(vehicleType);
			template.setStatus(draftStatus);

			setUpdateAudit(template, loggedInUser);

			return templateRepositoryHelper.templateRepository.save(template);
		}

		LOGGER.info("Creating new template: {}", request.getTemplateName());

		if (templateRepositoryHelper.templateRepository.existsByNameIgnoreCase(request.getTemplateName())) {

			LOGGER.error("Template already exists: {}", request.getTemplateName());

			throw new ResourceAlreadyExistsException(ErrorMessageConstants.TEMPLATE_ALREADY_EXISTS);
		}

		Template template = new Template();

		template.setTemplateId(generateStringId(Constants.TEMPLATE_ID_PREFIX));

		template.setName(request.getTemplateName());
		template.setVehicleType(vehicleType);
		template.setStatus(draftStatus);

		setCreateAudit(template, loggedInUser);

		return templateRepositoryHelper.templateRepository.save(template);
	}

	private void processStandardCoverageTypesAndFields(Coverage coverage, CoverageDraftRequestDto coverageRequest,
													   User loggedInUser, Plan plan, Template template, TemplatePlan templatePlan) {

		if (coverageRequest.getCoverageTypes() == null || coverageRequest.getCoverageTypes().isEmpty()) {

			return;
		}

		for (CoverageTypeDraftRequestDto typeRequest : coverageRequest.getCoverageTypes()) {

			Integer coverageTypeId = typeRequest.getCoverageTypeId();

			if (coverageTypeId == null) {

				throw new BadRequestException(ErrorMessageConstants.COVERAGE_TYPE_ID_IS_REQUIRED);
			}

			coverageRepositoryHelper.coverageTypeRepository.findById(coverageTypeId)
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.COVERAGE_TYPE_NOT_FOUND));

			validateCoverageTypeBelongsToCoverage(coverage.getCoverageId(), coverageTypeId);

			CoverageTypeCatalog coverageTypeCatalog = coverageRepositoryHelper.coverageTypeCatalogRepository
					.findByCoverageCoverageIdAndCoverageTypeCoverageTypeId(coverage.getCoverageId(), coverageTypeId)
					.orElseThrow(
							() -> new ResourceNotFoundException(ErrorMessageConstants.COVERAGE_TYPE_CATALOG_NOT_FOUND));

			createTemplateCoverageMapping(templatePlan, coverage, coverageTypeCatalog, loggedInUser);

			if (typeRequest.getComponents() == null || typeRequest.getComponents().isEmpty()) {

				continue;
			}

			for (ComponentDraftRequestDto componentRequest : typeRequest.getComponents()) {

				Integer componentId = componentRequest.getComponentId();

				if (componentId == null) {

					throw new BadRequestException(ErrorMessageConstants.COMPONENT_ID_REQUIRED);
				}

				componentRepository.findById(componentId)
						.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.COMPONENT_NOT_FOUND));

				validateComponentBelongsToCoverageType(coverageTypeId, componentId);

				if (componentRequest.getFields() == null || componentRequest.getFields().isEmpty()) {

					continue;
				}

				validateBiPerPersonAndAccident(coverage, componentRequest.getFields());

				for (FieldDraftRequestDto fieldRequest : componentRequest.getFields()) {

					Integer coverageFieldId = fieldRequest.getCoverageFieldId();

					if (coverageFieldId == null) {

						throw new BadRequestException(ErrorMessageConstants.COVERAGE_FIELD_REQUIRED);
					}

					BigDecimal basePrice = fieldRequest.getBasePrice();

					CoverageField coverageField = coverageRepositoryHelper.coverageFieldRepository
							.findById(coverageFieldId).orElseThrow(() -> new ResourceNotFoundException(
									ErrorMessageConstants.COVERAGE_FIELD_NOT_FOUND));

					validateFieldBelongsToComponent(componentId, coverageFieldId);

					// Draft: field price optional
					if (basePrice != null) {

						saveCoverageFieldPriceIfPresent(template, plan, coverageField, basePrice, loggedInUser);
					}
				}
			}
		}
	}

	private void validateMandatoryStandardCoverages(List<CoverageDraftRequestDto> coverages) {

		List<StandardCoverageProjection> standardCoverages = coverageRepositoryHelper.coverageRepository
				.findByIsStandardTrue();

		if (standardCoverages.isEmpty()) {

			LOGGER.error("No predefined standard coverages configured");

			throw new BadRequestException(ErrorMessageConstants.PREDEFINED_COVERAGE_ARE_MISSING);
		}

		if (coverages == null || coverages.isEmpty()) {

			LOGGER.error("Coverages are required");

			throw new BadRequestException(ErrorMessageConstants.ALL_PREDEFINED_COVERAGE_ARE_REQUIRED);
		}

		Set<Integer> requestCoverageIds = coverages.stream().filter(Objects::nonNull)
				.filter(coverage -> Boolean.TRUE.equals(coverage.getIsStandard()))
				.map(CoverageDraftRequestDto::getCoverageId).filter(Objects::nonNull).collect(Collectors.toSet());

		for (StandardCoverageProjection coverage : standardCoverages) {

			if (!requestCoverageIds.contains(coverage.getCoverageId())) {

				LOGGER.error("Missing predefined coverage. coverageId: {}", coverage.getCoverageId());

				throw new BadRequestException(ErrorMessageConstants.ALL_PREDEFINED_COVERAGE_ARE_REQUIRED);
			}
		}
	}

	private void validateStandardCoverageStructureCompleteness(Coverage coverage,
															   CoverageDraftRequestDto coverageRequest) {

		List<CoverageTypeCatalogProjection> coverageTypeCatalogs = coverageRepositoryHelper.coverageTypeCatalogRepository
				.findProjectedByCoverageCoverageId(coverage.getCoverageId());

		if (coverageTypeCatalogs.isEmpty()) {

			LOGGER.error("No predefined coverage types found for coverageId: {}", coverage.getCoverageId());

			throw new BadRequestException(ErrorMessageConstants.PREDEFINED_COVERAGE_STRUCTURE_IS_MISSING);
		}

		if (coverageRequest.getCoverageTypes() == null || coverageRequest.getCoverageTypes().isEmpty()) {

			LOGGER.error("Coverage types are required for standard coverage");

			throw new BadRequestException(ErrorMessageConstants.ALL_PREDEFINED_COVERAGE_TYPE_ARE_REQUIRED);
		}

		Map<Integer, CoverageTypeDraftRequestDto> requestCoverageTypes = coverageRequest.getCoverageTypes().stream()
				.filter(type -> type.getCoverageTypeId() != null)
				.collect(Collectors.toMap(CoverageTypeDraftRequestDto::getCoverageTypeId, Function.identity()));

		for (CoverageTypeCatalogProjection coverageType : coverageTypeCatalogs) {

			CoverageTypeDraftRequestDto typeRequest = requestCoverageTypes.get(coverageType.getCoverageTypeId());

			if (typeRequest == null) {

				LOGGER.error("Missing predefined coverage type. coverageTypeId: {}", coverageType.getCoverageTypeId());

				throw new BadRequestException(ErrorMessageConstants.ALL_PREDEFINED_COVERAGE_TYPE_ARE_REQUIRED);
			}

			validateStandardComponentCompleteness(coverageType, typeRequest);
		}
	}

	private void validateStandardComponentCompleteness(CoverageTypeCatalogProjection coverageType,
													   CoverageTypeDraftRequestDto typeRequest) {

		List<ComponentCatalogProjection> componentCatalogs = componentCatalogRepository
				.findProjectedByCoverageTypeCoverageTypeId(coverageType.getCoverageTypeId());

		if (componentCatalogs.isEmpty()) {

			LOGGER.error("No predefined components found for coverageTypeId: {}", coverageType.getCoverageTypeId());

			throw new BadRequestException(ErrorMessageConstants.PREDEFINED_COMPONENT_STRUCTURE_IS_MISSING);
		}

		if (typeRequest.getComponents() == null || typeRequest.getComponents().isEmpty()) {

			LOGGER.error("Components are required for coverageTypeId: {}", coverageType.getCoverageTypeId());

			throw new BadRequestException(ErrorMessageConstants.ALL_PREDEFINED_COMPONENTS_ARE_REQUIRED);
		}

		Map<Integer, ComponentDraftRequestDto> requestComponents = typeRequest.getComponents().stream()
				.filter(component -> component.getComponentId() != null)
				.collect(Collectors.toMap(ComponentDraftRequestDto::getComponentId, Function.identity()));

		for (ComponentCatalogProjection component : componentCatalogs) {

			ComponentDraftRequestDto componentRequest = requestComponents.get(component.getComponentId());

			if (componentRequest == null) {

				LOGGER.error("Missing predefined component. componentId: {}", component.getComponentId());

				throw new BadRequestException(ErrorMessageConstants.ALL_PREDEFINED_COMPONENTS_ARE_REQUIRED);
			}

			validateStandardFieldCompleteness(component, componentRequest);
		}
	}

	private void validateStandardFieldCompleteness(ComponentCatalogProjection component,
												   ComponentDraftRequestDto componentRequest) {

		List<ComponentFieldCatalogProjection> fieldCatalogs = componentFieldCatalogRepository
				.findProjectedByComponentComponentId(component.getComponentId());

		if (fieldCatalogs.isEmpty()) {

			LOGGER.info("No predefined fields configured for componentId: {}", component.getComponentId());

			return;
		}

		if (componentRequest.getFields() == null || componentRequest.getFields().isEmpty()) {

			LOGGER.error("Fields are required for componentId: {}", component.getComponentId());

			throw new BadRequestException(ErrorMessageConstants.ALL_PREDEFINED_FIELD_ARE_REQUIRED);
		}

		Map<Integer, FieldDraftRequestDto> requestFields = componentRequest.getFields().stream()
				.filter(field -> field.getCoverageFieldId() != null)
				.collect(Collectors.toMap(FieldDraftRequestDto::getCoverageFieldId, Function.identity()));

		for (ComponentFieldCatalogProjection coverageField : fieldCatalogs) {

			FieldDraftRequestDto fieldRequest = requestFields.get(coverageField.getCoverageFieldId());

			if (fieldRequest == null) {

				LOGGER.error("Missing predefined field. coverageFieldId: {}", coverageField.getCoverageFieldId());

				throw new BadRequestException(ErrorMessageConstants.ALL_PREDEFINED_FIELD_ARE_REQUIRED);
			}

			if (fieldRequest.getBasePrice() == null) {

				LOGGER.error("Field base price missing. coverageFieldId: {}", coverageField.getCoverageFieldId());

				throw new BadRequestException(ErrorMessageConstants.FIELD_BASE_PRICE_REQUIRED);
			}
		}
	}

	private void processStandardAddOns(Coverage coverage, CoverageDraftRequestDto coverageRequest, User loggedInUser,
									   Plan plan, Template template) {

		LOGGER.debug("processStandardAddOns called for coverageId: {}", coverage.getCoverageId());

		if (coverageRequest.getAddOns() == null || coverageRequest.getAddOns().isEmpty()) {

			LOGGER.info("No standard add-ons found for coverageId: {}", coverage.getCoverageId());

			return;
		}

		validateStandardCoverageAddOnEligibility(coverage);

		for (AddOnDraftRequestDto addOnRequest : coverageRequest.getAddOns()) {

			LOGGER.info("Processing standard add-on. addOnId: {}", addOnRequest.getAddOnId());

			AddOn addOn = coverageRepositoryHelper.addOnRepository.findById(addOnRequest.getAddOnId())
					.orElseThrow(() -> {

						LOGGER.error("Add-on not found for id: {}", addOnRequest.getAddOnId());

						throw new ResourceNotFoundException(ErrorMessageConstants.ADDON_NOT_FOUND);
					});

			validateAddOnBelongsToCoverage(coverage.getCoverageId(), addOn.getAddOnId());

			if (addOnRequest.getBasePrice() != null) {

				saveAddOnPriceIfPresent(template, plan, addOn, addOnRequest.getBasePrice(), loggedInUser);
			}
		}
	}

	private void validateStandardCoverageAddOnEligibility(Coverage coverage) {
		if (coverage == null || coverage.getName() == null || coverage.getName().isBlank()) {
			LOGGER.error("Coverage name is missing for add-on validation");
			throw new BadRequestException(ErrorMessageConstants.INVALID_COVERAGE_FOR_ADD_ON);
		}
		boolean comprehensiveCoverage = Constants.COMPREHENSIVE.equalsIgnoreCase(coverage.getName())
				|| Constants.COMPREHENSIVE_COVERAGE.equalsIgnoreCase(coverage.getName());
		if (!comprehensiveCoverage) {
			LOGGER.error("Add-ons are not allowed for coverage: {}", coverage.getName());
			throw new BadRequestException(ErrorMessageConstants.COMPREHENSIVE_REQUIRED_FOR_ADDONS);
		}
	}

	private void processCustomCoverageTypesAndFields(Coverage coverage, CoverageDraftRequestDto coverageRequest,
													 User loggedInUser, Plan plan, Template template, TemplatePlan templatePlan) {

		if (coverageRequest.getCoverageTypes() == null || coverageRequest.getCoverageTypes().isEmpty()) {

			return;
		}

		for (CoverageTypeDraftRequestDto typeRequest : coverageRequest.getCoverageTypes()) {

			CoverageType coverageType = getOrCreateCoverageType(typeRequest, loggedInUser);

			CoverageTypeCatalog coverageTypeCatalog = getOrCreateCoverageTypeCatalog(coverage, coverageType,
					loggedInUser);

			createTemplateCoverageMapping(templatePlan, coverage, coverageTypeCatalog, loggedInUser);

			if (typeRequest.getComponents() == null || typeRequest.getComponents().isEmpty()) {

				continue;
			}

			for (ComponentDraftRequestDto componentRequest : typeRequest.getComponents()) {

				Component component = getOrCreateComponent(componentRequest, loggedInUser);

				getOrCreateComponentCatalog(coverageType, component, loggedInUser);

				if (componentRequest.getFields() == null || componentRequest.getFields().isEmpty()) {

					continue;
				}

				for (FieldDraftRequestDto fieldRequest : componentRequest.getFields()) {

					CoverageField coverageField = getOrCreateCoverageField(fieldRequest, loggedInUser);

					BigDecimal basePrice = fieldRequest.getBasePrice();

					getOrCreateComponentFieldCatalog(component, coverageField, loggedInUser);

					if (basePrice != null) {

						saveCoverageFieldPriceIfPresent(template, plan, coverageField, basePrice, loggedInUser);
					}
				}
			}
		}
	}

	private void validateCoverageTypeBelongsToCoverage(Integer coverageId, Integer coverageTypeId) {
		boolean exists = coverageRepositoryHelper.coverageTypeCatalogRepository
				.existsByCoverageCoverageIdAndCoverageTypeCoverageTypeId(coverageId, coverageTypeId);
		if (!exists) {
			throw new BadRequestException(ErrorMessageConstants.COVERAGE_TYPE_NOT_BELONG_TO_SELECTED_COVERAGE);
		}
	}

	private void validateComponentBelongsToCoverageType(Integer coverageTypeId, Integer componentId) {
		boolean exists = componentCatalogRepository
				.existsByCoverageTypeCoverageTypeIdAndComponentComponentId(coverageTypeId, componentId);
		if (!exists) {
			throw new BadRequestException(ErrorMessageConstants.COMPONENT_NOT_BELONG_TO_SELECTED_COVERAGE_TYPE);
		}
	}

	private void validateFieldBelongsToComponent(Integer componentId, Integer coverageFieldId) {
		boolean exists = componentFieldCatalogRepository
				.existsByComponentComponentIdAndCoverageFieldCoverageFieldId(componentId, coverageFieldId);
		if (!exists) {
			throw new BadRequestException(ErrorMessageConstants.COVERAGE_FIELD_NOT_BELONG_TO_SELECTED_COMPONENT);
		}
	}

	private void validateAddOnBelongsToCoverage(Integer coverageId, Integer addOnId) {
		boolean exists = coverageRepositoryHelper.coverageAddOnCatalogRepository
				.existsByCoverageCoverageIdAndAddOnAddOnId(coverageId, addOnId);
		if (!exists) {
			throw new BadRequestException(ErrorMessageConstants.ADD_ON_NOT_BELONG_TO_SELECTED_COVERAGE);
		}
	}

	private TemplatePlan createOrGetTemplatePlan(Template template, Plan plan, User loggedInUser) {
		LOGGER.debug("createOrGetTemplatePlan called for templateId: {}, planId: {}", template.getTemplateId(),
				plan.getPlanId());
		return templateRepositoryHelper.templatePlanRepository
				.findByTemplateTemplateIdAndPlanPlanId(template.getTemplateId(), plan.getPlanId()).orElseGet(() -> {
					LOGGER.info("Creating TemplatePlan for templateId: {}, planId: {}", template.getTemplateId(),
							plan.getPlanId());
					TemplatePlan templatePlan = new TemplatePlan();
					templatePlan.setTemplatePlanId(generateStringId(Constants.TEMPLATE_PLAN_ID_PREFIX));
					templatePlan.setTemplate(template);
					templatePlan.setPlan(plan);
					setCreateAudit(templatePlan, loggedInUser);
					return templateRepositoryHelper.templatePlanRepository.save(templatePlan);
				});
	}

	private void savePlanCoverages(TemplatePlan templatePlan, Plan plan, List<CoverageDraftRequestDto> coverages,
								   User loggedInUser, Status draftStatus) {

		LOGGER.debug("savePlanCoverages called for templatePlanId: {}, planId: {}", templatePlan.getTemplatePlanId(),
				plan.getPlanId());

		if (coverages == null) {

			LOGGER.warn("Coverages list is null for planId: {}", plan.getPlanId());

			return;
		}

		/*
		 * Validate duplicate request entries
		 * before processing.
		 */
		validateDuplicateRequestEntries(
				coverages);

		for (CoverageDraftRequestDto coverageRequest : coverages) {

			if (coverageRequest == null) {

				continue;
			}

			if (!Boolean.FALSE.equals(coverageRequest.getIsStandard())) {

				continue;
			}

			if (coverageRequest.getCoverageTypes() != null && coverageRequest.getCoverageTypes().size() > 5) {

				LOGGER.error("Maximum coverage subtypes exceeded for coverage: {}", coverageRequest.getCoverageName());

				throw new BadRequestException(ErrorMessageConstants.MAXIMUM_5_COVERAGE_TYPES_ALLOWED);
			}
		}

		long customCoverageCount = coverages.stream().filter(Objects::nonNull)
				.filter(coverage -> Boolean.FALSE.equals(coverage.getIsStandard())).count();

		if (customCoverageCount > 5) {

			LOGGER.error("Maximum custom coverages exceeded. Count: {}", customCoverageCount);

			throw new BadRequestException(ErrorMessageConstants.MAXIMUM_5_CUSTOM_COVERAGES_ALLOWED);
		}

		Template template = templatePlan.getTemplate();

		for (CoverageDraftRequestDto coverageRequest : coverages) {

			LOGGER.info("Processing coverageId: {}, coverageName: {}, isStandard: {}", coverageRequest.getCoverageId(),
					coverageRequest.getCoverageName(), coverageRequest.getIsStandard());

			if (coverageRequest.getIsStandard() == null) {

				throw new BadRequestException(ErrorMessageConstants.IS_STANDARD_REQUIRED);
			}

			Coverage coverage;

			if (Boolean.TRUE.equals(coverageRequest.getIsStandard())) {

				coverage = getStandardCoverage(coverageRequest);

				if (coverageRequest.getBasePrice() != null) {

					saveCoveragePriceIfPresent(template, plan, coverage, coverageRequest.getBasePrice(), loggedInUser);
				}

				processStandardCoverageTypesAndFields(coverage, coverageRequest, loggedInUser, plan, template,
						templatePlan);

				processStandardAddOns(coverage, coverageRequest, loggedInUser, plan, template);

			} else {

				coverage = getOrCreateCustomCoverage(coverageRequest, loggedInUser, draftStatus,
						template.getTemplateId());

				validateCustomStructureNames(coverageRequest);

				validateCustomStructureReuse(template.getTemplateId(), coverageRequest);

				if (coverageRequest.getBasePrice() != null) {

					saveCoveragePriceIfPresent(template, plan, coverage, coverageRequest.getBasePrice(), loggedInUser);
				}

				processCustomCoverageTypesAndFields(coverage, coverageRequest, loggedInUser, plan, template,
						templatePlan);

				processCustomAddOns(coverage, coverageRequest, loggedInUser, draftStatus, plan, template);

				/*
				 * FIX: Re-run template mapping after custom add-ons are created so
				 * CoverageAddOnCatalogId gets populated
				 */
				List<CoverageTypeCatalog> coverageTypeCatalogs = coverageRepositoryHelper.coverageTypeCatalogRepository
						.findByCoverageCoverageId(coverage.getCoverageId());

				for (CoverageTypeCatalog coverageTypeCatalog : coverageTypeCatalogs) {

					createTemplateCoverageMapping(templatePlan, coverage, coverageTypeCatalog, loggedInUser);
				}

			}
		}
	}

	private void processCustomAddOns(Coverage coverage, CoverageDraftRequestDto coverageRequest, User loggedInUser,
									 Status draftStatus, Plan plan, Template template) {
		if (coverageRequest.getAddOns() == null) {
			return;
		}
		for (AddOnDraftRequestDto addOnRequest : coverageRequest.getAddOns()) {
			AddOn addOn = getOrCreateAddOn(addOnRequest, loggedInUser, draftStatus, template.getTemplateId());
			getOrCreateCoverageAddonCatalog(coverage, addOn, loggedInUser);
			saveAddOnPriceIfPresent(template, plan, addOn, addOnRequest.getBasePrice(), loggedInUser);
		}
	}

	private Coverage getStandardCoverage(CoverageDraftRequestDto coverageRequest) {
		LOGGER.debug("getStandardCoverage called for coverageId: {}", coverageRequest.getCoverageId());
		if (coverageRequest.getCoverageId() == null) {
			LOGGER.error("CoverageId is required for standard coverage");
			throw new BadRequestException(ErrorMessageConstants.STANDARD_COVERAGE_ID_REQUIRED);
		}
		Coverage coverage = coverageRepositoryHelper.coverageRepository.findById(coverageRequest.getCoverageId())
				.orElseThrow(() -> {
					LOGGER.error("Coverage not found for id: {}", coverageRequest.getCoverageId());
					throw new ResourceNotFoundException(ErrorMessageConstants.COVERAGE_NOT_FOUND);
				});
		if (Boolean.FALSE.equals(coverage.getIsStandard())) {
			LOGGER.error("Custom coverage cannot be used as standard coverage. coverageId: {}",
					coverage.getCoverageId());
			throw new BadRequestException(ErrorMessageConstants.ONLY_PREDEFINED_STANDARD_COVERAGE_ALLOWED);
		}
		return coverage;
	}

	private Coverage getOrCreateCustomCoverage(CoverageDraftRequestDto request, User loggedInUser, Status draftStatus,
											   String currentTemplateId) {
		LOGGER.debug("getOrCreateCustomCoverage called for coverageName: {}, coverageId: {}", request.getCoverageName(),
				request.getCoverageId());
		validateCustomCoverageRequest(request);
		if (request.getCoverageId() != null && request.getCoverageName() != null
				&& !request.getCoverageName().isBlank()) {
			Coverage existingCoverage = coverageRepositoryHelper.coverageRepository.findById(request.getCoverageId())
					.orElseThrow(() -> {
						LOGGER.error("Coverage not found for id: {}", request.getCoverageId());
						throw new ResourceNotFoundException(ErrorMessageConstants.COVERAGE_NOT_FOUND);
					});
			if (!existingCoverage.getName().equalsIgnoreCase(request.getCoverageName())) {
				LOGGER.error("CoverageId and coverageName mismatch. coverageId: {}, coverageName: {}",
						request.getCoverageId(), request.getCoverageName());
				throw new BadRequestException(ErrorMessageConstants.COVERAGE_ID_AND_NAME_DO_NOT_MATCH);
			}
			validateCustomCoverageReuse(existingCoverage, currentTemplateId);
			return existingCoverage;
		}
		if (request.getCoverageId() != null) {
			Coverage existingCoverage = coverageRepositoryHelper.coverageRepository.findById(request.getCoverageId())
					.orElseThrow(() -> {
						LOGGER.error("Coverage not found for id: {}", request.getCoverageId());
						throw new ResourceNotFoundException(ErrorMessageConstants.COVERAGE_NOT_FOUND);
					});
			validateCustomCoverageReuse(existingCoverage, currentTemplateId);
			return existingCoverage;
		}
		Optional<Coverage> existingCoverageOptional = coverageRepositoryHelper.coverageRepository
				.findByNameIgnoreCase(request.getCoverageName());
		if (existingCoverageOptional.isPresent()) {
			Coverage existingCoverage = existingCoverageOptional.get();
			validateCustomCoverageReuse(existingCoverage, currentTemplateId);
			LOGGER.info("Reusing existing custom coverage with name: {}", request.getCoverageName());
			return existingCoverage;
		}
		LOGGER.info("Creating new custom coverage: {}", request.getCoverageName());
		Coverage coverage = new Coverage();
		coverage.setName(request.getCoverageName());
		coverage.setIsStandard(false);
		coverage.setStatus(draftStatus);
		setCreateAudit(coverage, loggedInUser);
		return coverageRepositoryHelper.coverageRepository.save(coverage);
	}

	private void createTemplateCoverageMapping(TemplatePlan templatePlan, Coverage coverage,
											   CoverageTypeCatalog coverageTypeCatalog, User loggedInUser) {

		LOGGER.debug("Creating template coverage mapping. templatePlanId: {}, coverageTypeCatalogId: {}",
				templatePlan.getTemplatePlanId(), coverageTypeCatalog.getCoverageTypeCatalogId());

		List<CoverageAddOnCatalog> addOnCatalogs = coverageRepositoryHelper.coverageAddOnCatalogRepository
				.findByCoverageCoverageId(coverage.getCoverageId());

		if (addOnCatalogs.isEmpty()) {

			boolean mappingExists = templateRepositoryHelper.templateCoverageRepository
					.findByTemplatePlanTemplatePlanIdAndCoverageTypeCatalogCoverageTypeCatalogIdAndCoverageAddOnCatalogIsNull(
							templatePlan.getTemplatePlanId(), coverageTypeCatalog.getCoverageTypeCatalogId())
					.isPresent();

			if (mappingExists) {
				return;
			}

			TemplateCoverage templateCoverage = new TemplateCoverage();

			templateCoverage.setTemplateCoverageId(generateStringId(Constants.TEMPLATE_COVERAGE_ID_PREFIX));

			templateCoverage.setTemplatePlan(templatePlan);

			templateCoverage.setCoverageTypeCatalog(coverageTypeCatalog);

			templateCoverage.setCoverageAddOnCatalog(null);

			setCreateAudit(templateCoverage, loggedInUser);

			templateRepositoryHelper.templateCoverageRepository.save(templateCoverage);

			return;
		}

		List<TemplateCoverage> templateCoverages = addOnCatalogs.stream()
				.filter(addOnCatalog -> templateRepositoryHelper.templateCoverageRepository
						.findByTemplatePlanTemplatePlanIdAndCoverageTypeCatalogCoverageTypeCatalogIdAndCoverageAddOnCatalogCoverageAddonCatalogId(
								templatePlan.getTemplatePlanId(), coverageTypeCatalog.getCoverageTypeCatalogId(),
								addOnCatalog.getCoverageAddonCatalogId())
						.isEmpty())
				.map(addOnCatalog -> {

					TemplateCoverage templateCoverage = new TemplateCoverage();

					templateCoverage.setTemplateCoverageId(generateStringId(Constants.TEMPLATE_COVERAGE_ID_PREFIX));

					templateCoverage.setTemplatePlan(templatePlan);

					templateCoverage.setCoverageTypeCatalog(coverageTypeCatalog);

					templateCoverage.setCoverageAddOnCatalog(addOnCatalog);

					setCreateAudit(templateCoverage, loggedInUser);

					return templateCoverage;
				}).toList();

		if (!templateCoverages.isEmpty()) {

			templateRepositoryHelper.templateCoverageRepository.saveAll(templateCoverages);
		}
	}

	private void validateCustomCoverageRequest(CoverageDraftRequestDto request) {
		if (request.getCoverageId() == null
				&& (request.getCoverageName() == null || request.getCoverageName().isBlank())) {
			LOGGER.error("Coverage name is required when coverageId is not provided");
			throw new BadRequestException(ErrorMessageConstants.COVERAGE_NAME_REQUIRED);
		}
	}

	private void validateCustomCoverageReuse(Coverage coverage, String currentTemplateId) {
		if (Boolean.TRUE.equals(coverage.getIsStandard())) {
			LOGGER.error("Standard coverage cannot be reused as custom coverage. coverageId: {}",
					coverage.getCoverageId());
			throw new BadRequestException(ErrorMessageConstants.STANDARD_COVERAGE_CANNOT_BE_USED_AS_CUSTOM_COVERAGE);
		}
		List<VehicleTypeCoveragePrice> coveragePrices = coverageRepositoryHelper.vehicleTypeCoveragePriceRepository
				.findActiveByCoverageId(coverage.getCoverageId());
		if (coveragePrices.isEmpty()) {
			LOGGER.error("Unable to determine template usage for coverageId: {}", coverage.getCoverageId());
			throw new BadRequestException(ErrorMessageConstants.UNABLE_TO_VALIDATE_CUSTOM_COVERAGE_REUSE);
		}
		boolean publishedUsageExists = coveragePrices.stream().map(VehicleTypeCoveragePrice::getTemplate)
				.filter(Objects::nonNull).filter(template -> template.getDeletedDate() == null)
				.anyMatch(template -> template.getStatus() != null
						&& TemplateStatus.PUBLISHED.name().equalsIgnoreCase(template.getStatus().getName()));
		if (publishedUsageExists) {
			LOGGER.info("Published template usage found. Global reuse allowed for coverageId: {}",
					coverage.getCoverageId());
			return;
		}
		boolean sameDraftTemplateExists = coveragePrices.stream().map(VehicleTypeCoveragePrice::getTemplate)
				.filter(Objects::nonNull).filter(template -> template.getDeletedDate() == null)
				.anyMatch(template -> template.getStatus() != null
						&& TemplateStatus.DRAFT.name().equalsIgnoreCase(template.getStatus().getName())
						&& template.getTemplateId().equals(currentTemplateId));
		if (!sameDraftTemplateExists) {
			LOGGER.error(
					"Draft custom coverage cannot be reused across templates. coverageId: {}, currentTemplateId: {}",
					coverage.getCoverageId(), currentTemplateId);
			throw new BadRequestException(
					ErrorMessageConstants.DRAFT_CUSTOM_COVERAGE_CANNOT_BE_REUSED_ACROSS_TEMPLATES);
		}
		LOGGER.info("Draft custom coverage reuse allowed within same template. coverageId: {}",
				coverage.getCoverageId());
	}

	private CoverageType getOrCreateCoverageType(CoverageTypeDraftRequestDto request, User loggedInUser) {
		LOGGER.debug("getOrCreateCoverageType called for typeName: {}, typeId: {}", request.getCoverageTypeName(),
				request.getCoverageTypeId());
		if (request.getCoverageTypeId() != null) {
			LOGGER.info("Fetching existing CoverageType with id: {}", request.getCoverageTypeId());
			return coverageRepositoryHelper.coverageTypeRepository.findById(request.getCoverageTypeId())
					.orElseThrow(() -> {
						LOGGER.error("CoverageType not found for id: {}", request.getCoverageTypeId());
						throw new ResourceNotFoundException(ErrorMessageConstants.COVERAGE_TYPE_NOT_FOUND);
					});
		}
		if (request.getCoverageTypeName() == null || request.getCoverageTypeName().isBlank()) {
			LOGGER.error("Coverage Type Name is required");
			throw new BadRequestException(ErrorMessageConstants.COVERAGE_TYPE_NAME_REQUIRED);
		}
		Optional<CoverageType> existing = coverageRepositoryHelper.coverageTypeRepository
				.findByNameIgnoreCase(request.getCoverageTypeName());
		if (existing.isPresent()) {
			LOGGER.info("Existing CoverageType found with name: {}", request.getCoverageTypeName());
			return existing.get();
		}
		LOGGER.info("Creating new CoverageType: {}", request.getCoverageTypeName());
		CoverageType type = new CoverageType();
		type.setName(request.getCoverageTypeName());
		setCreateAudit(type, loggedInUser);
		return coverageRepositoryHelper.coverageTypeRepository.save(type);
	}

	private Component getOrCreateComponent(ComponentDraftRequestDto request, User loggedInUser) {
		LOGGER.debug("getOrCreateComponent called for componentName: {}, componentId: {}", request.getComponentName(),
				request.getComponentId());
		if (request.getComponentId() != null) {
			LOGGER.info("Fetching existing Component with id: {}", request.getComponentId());
			return componentRepository.findById(request.getComponentId()).orElseThrow(() -> {
				LOGGER.error("Component not found for id: {}", request.getComponentId());
				throw new ResourceNotFoundException(ErrorMessageConstants.COMPONENT_NOT_FOUND);
			});
		}
		if (request.getComponentName() == null || request.getComponentName().isBlank()) {
			LOGGER.error("Component Name is required");
			throw new BadRequestException(ErrorMessageConstants.COMPONENT_NAME_REQUIRED);
		}
		Optional<Component> existing = componentRepository.findByNameIgnoreCase(request.getComponentName());
		if (existing.isPresent()) {
			LOGGER.info("Existing Component found with name: {}", request.getComponentName());
			return existing.get();
		}
		LOGGER.info("Creating new Component: {}", request.getComponentName());
		Component component = new Component();
		component.setName(request.getComponentName());
		setCreateAudit(component, loggedInUser);
		return componentRepository.save(component);
	}

	private CoverageField getOrCreateCoverageField(FieldDraftRequestDto request, User loggedInUser) {
		LOGGER.debug("getOrCreateCoverageField called for fieldName: {}, fieldId: {}", request.getFieldName(),
				request.getCoverageFieldId());
		if (request.getCoverageFieldId() != null) {
			return coverageRepositoryHelper.coverageFieldRepository.findById(request.getCoverageFieldId())
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.COVERAGE_FIELD_NOT_FOUND));
		}
		if (request.getFieldName() == null || request.getFieldName().isBlank()) {
			throw new BadRequestException(ErrorMessageConstants.FIELD_NAME_REQUIRED);
		}
		Optional<CoverageField> existing = coverageRepositoryHelper.coverageFieldRepository
				.findByNameIgnoreCase(request.getFieldName());
		if (existing.isPresent()) {
			return existing.get();
		}
		CoverageField field = new CoverageField();
		field.setName(request.getFieldName());
		setCreateAudit(field, loggedInUser);
		return coverageRepositoryHelper.coverageFieldRepository.save(field);
	}

	private AddOn getOrCreateAddOn(AddOnDraftRequestDto request, User loggedInUser, Status draftStatus,
								   String currentTemplateId) {
		LOGGER.debug("getOrCreateAddOn called for addOnName: {}, addOnId: {}", request.getAddOnName(),
				request.getAddOnId());
		validateAddOnRequest(request);
		if (request.getAddOnId() != null && request.getAddOnName() != null && !request.getAddOnName().isBlank()) {
			AddOn existingAddOn = coverageRepositoryHelper.addOnRepository.findById(request.getAddOnId())
					.orElseThrow(() -> {
						LOGGER.error("Add-on not found for id: {}", request.getAddOnId());
						throw new ResourceNotFoundException(ErrorMessageConstants.ADDON_NOT_FOUND);
					});
			if (!existingAddOn.getName().equalsIgnoreCase(request.getAddOnName())) {
				LOGGER.error("Add-on id and name mismatch. addOnId: {}, addOnName: {}", request.getAddOnId(),
						request.getAddOnName());
				throw new BadRequestException(ErrorMessageConstants.ADD_ON_ID_AND_NAME_DO_NOT_MATCH);
			}
			validateAddOnReuse(existingAddOn, currentTemplateId);
			return existingAddOn;
		}
		if (request.getAddOnId() != null) {
			AddOn existingAddOn = coverageRepositoryHelper.addOnRepository.findById(request.getAddOnId())
					.orElseThrow(() -> {
						LOGGER.error("Add-on not found for id: {}", request.getAddOnId());
						throw new ResourceNotFoundException(ErrorMessageConstants.ADDON_NOT_FOUND);
					});
			validateAddOnReuse(existingAddOn, currentTemplateId);
			return existingAddOn;
		}
		Optional<AddOn> existingAddOnOptional = coverageRepositoryHelper.addOnRepository
				.findByNameIgnoreCase(request.getAddOnName());
		if (existingAddOnOptional.isPresent()) {
			AddOn existingAddOn = existingAddOnOptional.get();
			validateAddOnReuse(existingAddOn, currentTemplateId);
			LOGGER.info("Reusing existing add-on with name: {}", request.getAddOnName());
			return existingAddOn;
		}
		LOGGER.info("Creating new add-on: {}", request.getAddOnName());
		AddOn addOn = new AddOn();
		addOn.setName(request.getAddOnName());
		addOn.setStatus(draftStatus);
		setCreateAudit(addOn, loggedInUser);
		return coverageRepositoryHelper.addOnRepository.save(addOn);
	}

	private void validateAddOnRequest(AddOnDraftRequestDto request) {

		if (request.getAddOnId() == null && (request.getAddOnName() == null || request.getAddOnName().isBlank())) {

			LOGGER.error("Add-on name is required when addOnId is not provided");

			throw new BadRequestException(ErrorMessageConstants.ADDON_NAME_REQUIRED);
		}
	}

	private void validateAddOnReuse(AddOn addOn, String currentTemplateId) {

		boolean predefinedAddOn = coverageRepositoryHelper.coverageAddOnCatalogRepository
				.existsByAddOnAddOnIdAndCoverageIsNotNull(addOn.getAddOnId());

		if (predefinedAddOn) {

			LOGGER.info("Predefined add-on reuse allowed globally. addOnId: {}", addOn.getAddOnId());

			return;
		}

		List<TemplateAddOnPrice> addOnPrices = templateRepositoryHelper.templateAddOnPriceRepository
				.findActiveByAddOnId(addOn.getAddOnId());

		if (addOnPrices.isEmpty()) {

			LOGGER.error("Unable to determine template usage for addOnId: {}", addOn.getAddOnId());

			throw new BadRequestException(ErrorMessageConstants.UNABLE_TO_VALIDATE_ADDON_REUSE);
		}

		boolean publishedUsageExists = addOnPrices.stream().map(TemplateAddOnPrice::getTemplate)
				.filter(Objects::nonNull).filter(template -> template.getDeletedDate() == null)
				.anyMatch(template -> template.getStatus() != null
						&& TemplateStatus.PUBLISHED.name().equalsIgnoreCase(template.getStatus().getName()));

		if (publishedUsageExists) {

			LOGGER.info("Published template usage found. Global reuse allowed for addOnId: {}", addOn.getAddOnId());

			return;
		}

		boolean sameDraftTemplateExists = addOnPrices.stream().map(TemplateAddOnPrice::getTemplate)
				.filter(Objects::nonNull).filter(template -> template.getDeletedDate() == null)
				.anyMatch(template -> template.getStatus() != null
						&& TemplateStatus.DRAFT.name().equalsIgnoreCase(template.getStatus().getName())
						&& template.getTemplateId().equals(currentTemplateId));

		if (!sameDraftTemplateExists) {

			LOGGER.error("Draft add-on cannot be reused across templates. addOnId: {}, currentTemplateId: {}",
					addOn.getAddOnId(), currentTemplateId);

			throw new BadRequestException(ErrorMessageConstants.DRAFT_ADDON_CANNOT_BE_REUSED_ACROSS_TEMPLATES);
		}

		LOGGER.info("Draft add-on reuse allowed within same template. addOnId: {}", addOn.getAddOnId());
	}

	private CoverageTypeCatalog getOrCreateCoverageTypeCatalog(Coverage coverage, CoverageType coverageType,
															   User loggedInUser) {

		LOGGER.debug("getOrCreateCoverageTypeCatalog called for coverageId: {}, coverageTypeId: {}",
				coverage.getCoverageId(), coverageType.getCoverageTypeId());

		return coverageRepositoryHelper.coverageTypeCatalogRepository
				.findByCoverageCoverageIdAndCoverageTypeCoverageTypeId(coverage.getCoverageId(),
						coverageType.getCoverageTypeId())
				.orElseGet(() -> {

					LOGGER.info("Creating CoverageTypeCatalog for coverageId: {}, coverageTypeId: {}",
							coverage.getCoverageId(), coverageType.getCoverageTypeId());

					CoverageTypeCatalog catalog = new CoverageTypeCatalog();
					catalog.setCoverage(coverage);
					catalog.setCoverageType(coverageType);

					setCreateAudit(catalog, loggedInUser);

					return coverageRepositoryHelper.coverageTypeCatalogRepository.save(catalog);
				});
	}

	private ComponentCatalog getOrCreateComponentCatalog(CoverageType coverageType, Component component,
														 User loggedInUser) {

		LOGGER.info("Entering getOrCreateComponentCatalog with coverageTypeId: {} and componentId: {}",
				coverageType.getCoverageTypeId(), component.getComponentId());

		return componentCatalogRepository.findByCoverageTypeCoverageTypeIdAndComponentComponentId(
				coverageType.getCoverageTypeId(), component.getComponentId()).orElseGet(() -> {
			LOGGER.info("ComponentCatalog not found. Creating new ComponentCatalog.");

			ComponentCatalog catalog = new ComponentCatalog();
			catalog.setCoverageType(coverageType);
			catalog.setComponent(component);

			setCreateAudit(catalog, loggedInUser);

			LOGGER.info("Saving new ComponentCatalog.");

			return componentCatalogRepository.save(catalog);
		});
	}

	private ComponentFieldCatalog getOrCreateComponentFieldCatalog(Component component, CoverageField coverageField,
																   User loggedInUser) {

		LOGGER.info("Entering getOrCreateComponentFieldCatalog with ComponentId: {} and CoverageFieldId: {}",
				component.getComponentId(), coverageField.getCoverageFieldId());

		return componentFieldCatalogRepository.findByComponentComponentIdAndCoverageFieldCoverageFieldId(
				component.getComponentId(), coverageField.getCoverageFieldId()).orElseGet(() -> {

			LOGGER.info("ComponentFieldCatalog not found. Creating new ComponentFieldCatalog.");

			ComponentFieldCatalog catalog = new ComponentFieldCatalog();
			catalog.setComponent(component);
			catalog.setCoverageField(coverageField);

			setCreateAudit(catalog, loggedInUser);

			LOGGER.info("Saving new ComponentFieldCatalog.");

			return componentFieldCatalogRepository.save(catalog);
		});
	}

	private CoverageAddOnCatalog getOrCreateCoverageAddonCatalog(Coverage coverage, AddOn addOn, User loggedInUser) {

		LOGGER.info("Entering getOrCreateCoverageAddonCatalog with CoverageId: {} and AddOnId: {}",
				coverage.getCoverageId(), addOn.getAddOnId());

		return coverageRepositoryHelper.coverageAddOnCatalogRepository
				.findByCoverageCoverageIdAndAddOnAddOnId(coverage.getCoverageId(), addOn.getAddOnId()).orElseGet(() -> {

					LOGGER.info("CoverageAddonCatalog not found. Creating new CoverageAddonCatalog.");

					CoverageAddOnCatalog catalog = new CoverageAddOnCatalog();
					catalog.setCoverage(coverage);
					catalog.setAddOn(addOn);

					setCreateAudit(catalog, loggedInUser);

					LOGGER.info("Saving new CoverageAddonCatalog.");

					return coverageRepositoryHelper.coverageAddOnCatalogRepository.save(catalog);
				});
	}

	private void saveCoveragePriceIfPresent(Template template, Plan plan, Coverage coverage, BigDecimal basePrice,
											User loggedInUser) {

		LOGGER.debug("Saving coverage price. templateId: {}, planId: {}, coverageId: {}", template.getTemplateId(),
				plan.getPlanId(), coverage.getCoverageId());

		if (basePrice == null) {

			LOGGER.debug("Base price is null. Skipping coverage price save.");

			return;
		}

		validatePrice(basePrice, ErrorMessageConstants.PRICE_MUST_BE_MULTIPLE_OF_FIFTY);

		VehicleTypeCoveragePrice price = coverageRepositoryHelper.vehicleTypeCoveragePriceRepository
				.findByTemplateTemplateIdAndPlanPlanIdAndCoverageCoverageId(template.getTemplateId(), plan.getPlanId(),
						coverage.getCoverageId())
				.orElse(null);

		if (price == null) {

			price = new VehicleTypeCoveragePrice();

			price.setVehicleTypeCoveragePriceId(generateStringId(Constants.VEHICLE_TYPE_COVERAGE_PRICE_ID_PREFIX));

			price.setTemplate(template);

			price.setPlan(plan);

			price.setCoverage(coverage);

			setCreateAudit(price, loggedInUser);

		} else {

			setUpdateAudit(price, loggedInUser);
		}

		price.setBasePrice(basePrice);

		coverageRepositoryHelper.vehicleTypeCoveragePriceRepository.save(price);
	}

	private void saveCoverageFieldPriceIfPresent(Template template, Plan plan, CoverageField coverageField,
												 BigDecimal basePrice, User loggedInUser) {

		LOGGER.debug("Saving coverage field price. templateId: {}, planId: {}, fieldId: {}", template.getTemplateId(),
				plan.getPlanId(), coverageField.getCoverageFieldId());

		if (basePrice == null) {

			LOGGER.debug("Field base price is null. Skipping field price save.");

			return;
		}

		validatePrice(basePrice, ErrorMessageConstants.PRICE_MUST_BE_MULTIPLE_OF_FIFTY);

		TemplateCoverageFieldPrice price = templateRepositoryHelper.templateCoverageFieldPriceRepository
				.findByTemplateTemplateIdAndPlanPlanIdAndCoverageFieldCoverageFieldId(template.getTemplateId(),
						plan.getPlanId(), coverageField.getCoverageFieldId())
				.orElse(null);

		if (price == null) {

			price = new TemplateCoverageFieldPrice();

			price.setTemplateCoverageFieldPriceId(generateStringId(Constants.TEMPLATE_COVERAGE_FIELD_PRICE_ID_PREFIX));

			price.setTemplate(template);

			price.setPlan(plan);

			price.setCoverageField(coverageField);

			setCreateAudit(price, loggedInUser);

		} else {

			setUpdateAudit(price, loggedInUser);
		}

		price.setBasePrice(basePrice);

		templateRepositoryHelper.templateCoverageFieldPriceRepository.save(price);
	}

	private void saveAddOnPriceIfPresent(Template template, Plan plan, AddOn addOn, BigDecimal basePrice,
										 User loggedInUser) {

		LOGGER.debug("Saving add-on price. templateId: {}, planId: {}, addOnId: {}", template.getTemplateId(),
				plan.getPlanId(), addOn.getAddOnId());

		if (basePrice == null) {

			LOGGER.debug("Add-on base price is null. Skipping add-on price save.");

			return;
		}

		validatePrice(basePrice, ErrorMessageConstants.ADD_ON_PRICE_MUST_BE_MULTIPLE_OF_FIFTY);

		TemplateAddOnPrice price = templateRepositoryHelper.templateAddOnPriceRepository
				.findByTemplateTemplateIdAndPlanPlanIdAndAddOnAddOnId(template.getTemplateId(), plan.getPlanId(),
						addOn.getAddOnId())
				.orElse(null);

		if (price == null) {

			price = new TemplateAddOnPrice();

			price.setTemplateAddOnPriceId(generateStringId(Constants.TEMPLATE_ADD_ON_PRICE_ID_PREFIX));

			price.setTemplate(template);

			price.setPlan(plan);

			price.setAddOn(addOn);

			setCreateAudit(price, loggedInUser);

		} else {

			setUpdateAudit(price, loggedInUser);
		}

		price.setBasePrice(basePrice);

		templateRepositoryHelper.templateAddOnPriceRepository.save(price);
	}

	private void validatePrice(BigDecimal price, String errorMessage) {

		LOGGER.debug("Validating price: {}", price);

		if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {

			LOGGER.error("Invalid price: {}", price);

			throw new BadRequestException(ErrorMessageConstants.PRICE_MUST_BE_GREATER_THAN_ZERO);
		}

		if (price.remainder(BigDecimal.valueOf(50)).compareTo(BigDecimal.ZERO) != 0) {

			LOGGER.error("Price must be in multiples of 50: {}", price);

			throw new BadRequestException(errorMessage);
		}
	}

	private void validateCustomStructureNames(CoverageDraftRequestDto coverageRequest) {

		if (coverageRequest.getCoverageTypes() == null) {

			return;
		}

		/*
		 * CHANGED:
		 * Validate duplicate coverage type names
		 * within same custom coverage.
		 */
		Set<String> coverageTypeNames = new HashSet<>();

		for (CoverageTypeDraftRequestDto typeRequest
				: coverageRequest.getCoverageTypes()) {

			String coverageTypeName =
					typeRequest.getCoverageTypeName();

			if (coverageTypeName == null
					|| coverageTypeName.isBlank()) {

				continue;
			}

			String normalizedCoverageTypeName =
					coverageTypeName
							.trim()
							.toLowerCase();

			if (!coverageTypeNames.add(
					normalizedCoverageTypeName)) {

				LOGGER.error(
						"Duplicate coverage type found: {}",
						coverageTypeName);

				throw new BadRequestException(
						ErrorMessageConstants.DUPLICATE_COVERAGE_TYPE_NOT_ALLOWED
				);
			}
		}

		for (CoverageTypeDraftRequestDto typeRequest : coverageRequest.getCoverageTypes()) {

			String coverageTypeName = typeRequest.getCoverageTypeName();

			if (coverageTypeName != null && coverageRepositoryHelper.coverageTypeRepository
					.existsStandardCoverageType(coverageTypeName.trim())) {

				LOGGER.error("Predefined coverage type cannot be used in custom coverage: {}", coverageTypeName);

				throw new BadRequestException(ErrorMessageConstants.PREDEFINED_COVERAGE_TYPE_CANNOT_USED);
			}

			if (typeRequest.getComponents() == null) {

				continue;
			}

			if (typeRequest.getComponents() != null && typeRequest.getComponents().size() > 7) {

				throw new BadRequestException(ErrorMessageConstants.MAXIMUM_7_COMPONENTS_ALLOWED);
			}

			Set<String> componentNames = new HashSet<>();

			for (ComponentDraftRequestDto componentRequest : typeRequest.getComponents()) {

				String componentName = componentRequest.getComponentName();

				if (componentName != null) {

					String normalizedComponentName = componentName.trim().toLowerCase();

					if (!componentNames.add(normalizedComponentName)) {

						throw new BadRequestException(ErrorMessageConstants.DUPLICATE_COMPONENT_NOT_ALLOWED);
					}
				}

				if (componentName != null && componentRepository.existsStandardComponent(componentName.trim())) {

					LOGGER.error("Predefined component cannot be used in custom coverage: {}", componentName);

					throw new BadRequestException(ErrorMessageConstants.PREDEFINED_COMPONENT_CANNOT_USED);
				}

				if (componentRequest.getFields() == null) {

					continue;
				}

				Set<String> fieldNames = new HashSet<>();

				for (FieldDraftRequestDto fieldRequest : componentRequest.getFields()) {

					String fieldName = fieldRequest.getFieldName();

					if (fieldName != null) {

						String normalizedFieldName = fieldName.trim().toLowerCase();

						if (!fieldNames.add(normalizedFieldName)) {

							throw new BadRequestException(ErrorMessageConstants.DUPLICATE_FIELD_NOT_ALLOWED);
						}
					}

					if (fieldName != null
							&& coverageRepositoryHelper.coverageFieldRepository.existsStandardField(fieldName.trim())) {

						LOGGER.error("Predefined field cannot be used in custom coverage: {}", fieldName);

						throw new BadRequestException(
								ErrorMessageConstants.PREDEFINED_FIELD_CANNOT_USED_IN_CUSTOM_COVERAGE);
					}
				}
			}
		}

		if (coverageRequest.getAddOns() != null) {

			/*
			 * CHANGED:
			 * Validate duplicate add-on names
			 * within same custom coverage.
			 */
			Set<String> addOnNames = new HashSet<>();

			for (AddOnDraftRequestDto addOnRequest
					: coverageRequest.getAddOns()) {

				String addOnName =
						addOnRequest.getAddOnName();

				if (addOnName != null
						&& !addOnName.isBlank()) {

					String normalizedAddOnName =
							addOnName
									.trim()
									.toLowerCase();

					if (!addOnNames.add(
							normalizedAddOnName)) {

						LOGGER.error(
								"Duplicate add-on found: {}",
								addOnName);

						throw new BadRequestException(
								ErrorMessageConstants.DUPLICATE_ADD_ON_NOT_ALLOWED
						);
					}
				}

				if (addOnName != null
						&& coverageRepositoryHelper
						.addOnRepository
						.existsStandardAddOn(
								addOnName.trim())) {

					LOGGER.error(
							"Predefined add-on cannot be used in custom coverage: {}",
							addOnName);

					throw new BadRequestException(
							ErrorMessageConstants
									.PREDEFINED_ADD_ON_CANNOT_USED_IN_CUSTOM_COVERAGE);
				}
			}
		}
	}

	public User getLoggedInUser() {

		String email = authUtil.getLoggedInUserEmail();

		return masterRepositoryHelper.userRepository.findByEmail(email).orElseThrow(() -> {
			LOGGER.error("User not found for email: {}", email);
			throw new ResourceNotFoundException(ErrorMessageConstants.USER_NOT_FOUND);
		});
	}

	private void setCreateAudit(BaseEntity entity, User user) {

		LOGGER.info("Setting create audit for entity");

		entity.setCreatedById(user);
		entity.setCreatedDate(DateTimeHelper.getUtcDateTime());
	}

	private void setUpdateAudit(BaseEntity entity, User user) {

		LOGGER.info("Setting update audit for entity");

		entity.setModifiedById(user);
		entity.setModifiedDate(DateTimeHelper.getUtcDateTime());
	}

	private String generateStringId(String prefix) {

		LOGGER.info("Generating ID with prefix: {}", prefix);

		return prefix.concat(Constants.HYPHEN).concat(UUID.randomUUID().toString());
	}

	@Transactional
	public TemplateDraftResponseDto publishTemplate(String templateId) {

		LOGGER.info("publishTemplate called for templateId: {}", templateId);

		Template template = templateRepositoryHelper.templateRepository.findByTemplateId(templateId).orElseThrow(() -> {

			LOGGER.error("Template not found. templateId: {}", templateId);

			throw new ResourceNotFoundException(ErrorMessageConstants.TEMPLATE_NOT_FOUND);
		});

		if (template.getDeletedDate() != null) {

			LOGGER.error("Deleted template cannot be published. templateId: {}", templateId);

			throw new BadRequestException(ErrorMessageConstants.DELETED_TEMPLATE_CANNOT_BE_PUBLISHED);
		}

		if (template.getStatus() == null
				|| !TemplateStatus.DRAFT.name().equalsIgnoreCase(template.getStatus().getName())) {

			LOGGER.error("Only draft template can be published. templateId: {}", templateId);

			throw new BadRequestException(ErrorMessageConstants.ONLY_DRAFT_TEMPLATE_CAN_BE_PUBLISHED);
		}

		List<Plan> allPlans = masterRepositoryHelper.planRepository.findAll();

		if (allPlans.isEmpty()) {

			LOGGER.error("No plans configured");

			throw new BadRequestException(ErrorMessageConstants.NO_PLANS_CONFIGURED);
		}

		for (Plan plan : allPlans) {

			validatePlanConfigurationForPublish(template, plan);
		}

		for (Plan plan : allPlans) {

			validateCustomCoveragePublishRules(template, plan);
		}

		Status publishedStatus = masterRepositoryHelper.statusRepository
				.findByNameIgnoreCase(TemplateStatus.PUBLISHED.name()).orElseThrow(() -> {

					LOGGER.error("Published status not found");

					throw new ResourceNotFoundException(ErrorMessageConstants.PUBLISHED_STATUS_NOT_FOUND);
				});

		User loggedInUser = getLoggedInUser();

		publishCustomEntities(templateId, publishedStatus, loggedInUser);

		template.setStatus(publishedStatus);

		setUpdateAudit(template, loggedInUser);

		templateRepositoryHelper.templateRepository.save(template);

		LOGGER.info("Template published successfully. templateId: {}", templateId);

		return new TemplateDraftResponseDto(template.getTemplateId(), Constants.TEMPLATE_PUBLISHED_SUCCESSFULLY);
	}

	private void validatePlanConfigurationForPublish(Template template, Plan plan) {

		LOGGER.info("Validating publish configuration for templateId: {}, planId: {}", template.getTemplateId(),
				plan.getPlanId());

		templateRepositoryHelper.templatePlanRepository
				.findByTemplateTemplateIdAndPlanPlanId(template.getTemplateId(), plan.getPlanId()).orElseThrow(() -> {

					LOGGER.error("Plan configuration missing. templateId: {}, planId: {}", template.getTemplateId(),
							plan.getPlanId());

					throw new BadRequestException(ErrorMessageConstants.ALL_PLANS_MUST_BE_CONFIGURED);
				});

		List<VehicleTypeCoveragePrice> coveragePrices = coverageRepositoryHelper.vehicleTypeCoveragePriceRepository
				.findByTemplateTemplateIdAndPlanPlanId(template.getTemplateId(), plan.getPlanId());

		if (coveragePrices.isEmpty()) {

			LOGGER.error("Coverage configuration missing. templateId: {}, planId: {}", template.getTemplateId(),
					plan.getPlanId());

			throw new BadRequestException(ErrorMessageConstants.ALL_PLANS_MUST_BE_CONFIGURED);
		}

		validateMandatoryCoveragesForPublish(coveragePrices);

		for (VehicleTypeCoveragePrice coveragePrice : coveragePrices) {

			Coverage coverage = coveragePrice.getCoverage();

			validateCoveragePricingForPublish(coveragePrice);

			validateCoverageStructureForPublish(template, plan, coverage);

			if (!Boolean.TRUE.equals(coverage.getIsStandard())) {

				validateCustomCoverageReuse(coverage, template.getTemplateId());
			}
		}

		validateAddOnReuseForPublish(template, plan);
	}

	private void validateMandatoryCoveragesForPublish(List<VehicleTypeCoveragePrice> coveragePrices) {

		List<StandardCoverageProjection> standardCoverages = coverageRepositoryHelper.coverageRepository
				.findByIsStandardTrue();

		Set<Integer> configuredCoverageIds = coveragePrices.stream().map(price -> price.getCoverage().getCoverageId())
				.collect(Collectors.toSet());

		for (StandardCoverageProjection coverage : standardCoverages) {

			if (!configuredCoverageIds.contains(coverage.getCoverageId())) {

				LOGGER.error("Mandatory coverage missing. coverageId: {}", coverage.getCoverageId());

				throw new BadRequestException(ErrorMessageConstants.ALL_MANDATORY_COVERAGES_REQUIRED);
			}
		}
	}

	private void publishCustomEntities(String templateId, Status publishedStatus, User loggedInUser) {

		List<Coverage> customCoverages = coverageRepositoryHelper.coverageRepository
				.findCustomCoveragesByTemplateId(templateId);

		for (Coverage coverage : customCoverages) {

			if (coverage.getStatus() != null
					&& TemplateStatus.PUBLISHED.name().equalsIgnoreCase(coverage.getStatus().getName())) {

				continue;
			}

			coverage.setStatus(publishedStatus);

			setUpdateAudit(coverage, loggedInUser);
		}

		if (!customCoverages.isEmpty()) {

			coverageRepositoryHelper.coverageRepository.saveAll(customCoverages);
		}

		List<AddOn> customAddOns = coverageRepositoryHelper.addOnRepository.findCustomAddOnsByTemplateId(templateId);

		for (AddOn addOn : customAddOns) {

			if (addOn.getStatus() != null
					&& TemplateStatus.PUBLISHED.name().equalsIgnoreCase(addOn.getStatus().getName())) {

				continue;
			}

			addOn.setStatus(publishedStatus);

			setUpdateAudit(addOn, loggedInUser);
		}

		if (!customAddOns.isEmpty()) {
			coverageRepositoryHelper.addOnRepository.saveAll(customAddOns);
		}
	}

	private void validateCoveragePricingForPublish(VehicleTypeCoveragePrice coveragePrice) {

		if (coveragePrice.getBasePrice() == null || coveragePrice.getBasePrice().compareTo(BigDecimal.ZERO) <= 0) {

			LOGGER.error("Invalid coverage price. coverageId: {}", coveragePrice.getCoverage().getCoverageId());

			throw new BadRequestException(ErrorMessageConstants.COVERAGE_PRICE_REQUIRED);
		}
	}

	private void validateCoverageStructureForPublish(Template template, Plan plan, Coverage coverage) {

		TemplatePlan templatePlan = templateRepositoryHelper.templatePlanRepository
				.findByTemplateTemplateIdAndPlanPlanId(template.getTemplateId(), plan.getPlanId()).orElseThrow(() -> {

					LOGGER.error("Template plan not found. templateId: {}, planId: {}", template.getTemplateId(),
							plan.getPlanId());

					throw new BadRequestException(ErrorMessageConstants.ALL_PLANS_MUST_BE_CONFIGURED);
				});

		List<CoverageTypeCatalog> coverageTypeCatalogs = coverageRepositoryHelper.coverageTypeCatalogRepository
				.findByCoverageCoverageId(coverage.getCoverageId());

		for (CoverageTypeCatalog coverageTypeCatalog : coverageTypeCatalogs) {

			Integer coverageTypeCatalogId = coverageTypeCatalog.getCoverageTypeCatalogId();

			List<TemplateCoverage> templateCoverages = templateRepositoryHelper.templateCoverageRepository
					.findAllByTemplatePlanTemplatePlanIdAndCoverageTypeCatalogCoverageTypeCatalogId(
							templatePlan.getTemplatePlanId(), coverageTypeCatalogId);

			if (templateCoverages.isEmpty()) {

				LOGGER.error("Template coverage mapping missing. coverageTypeCatalogId: {}", coverageTypeCatalogId);

				throw new BadRequestException(ErrorMessageConstants.COVERAGE_CONFIGURATION_MISSING);
			}

			CoverageType coverageType = coverageTypeCatalog.getCoverageType();

			List<ComponentCatalog> componentCatalogs = componentCatalogRepository
					.findByCoverageTypeCoverageTypeId(coverageType.getCoverageTypeId());

			for (ComponentCatalog componentCatalog : componentCatalogs) {

				Component component = componentCatalog.getComponent();

				List<ComponentFieldCatalog> fieldCatalogs = componentFieldCatalogRepository
						.findByComponentComponentId(component.getComponentId());

				for (ComponentFieldCatalog fieldCatalog : fieldCatalogs) {

					CoverageField field = fieldCatalog.getCoverageField();

					Integer fieldId = field.getCoverageFieldId();

					TemplateCoverageFieldPrice fieldPrice = templateRepositoryHelper.templateCoverageFieldPriceRepository
							.findByTemplateTemplateIdAndPlanPlanIdAndCoverageFieldCoverageFieldId(
									template.getTemplateId(), plan.getPlanId(), fieldId)
							.orElseThrow(() -> {

								LOGGER.error("Field pricing missing. fieldId: {}", fieldId);

								throw new BadRequestException(ErrorMessageConstants.ALL_FIELD_PRICING_MUST_CONFIGURE);
							});

					BigDecimal basePrice = fieldPrice.getBasePrice();

					if (basePrice == null || basePrice.compareTo(BigDecimal.ZERO) <= 0) {

						LOGGER.error("Invalid field price. fieldId: {}", fieldId);

						throw new BadRequestException(ErrorMessageConstants.ALL_FIELD_PRICING_MUST_CONFIGURE);
					}
				}
			}
		}
	}

	private void validateAddOnReuseForPublish(Template template, Plan plan) {

		List<TemplateAddOnPrice> addOnPrices = templateRepositoryHelper.templateAddOnPriceRepository
				.findByTemplateTemplateIdAndPlanPlanId(template.getTemplateId(), plan.getPlanId());

		for (TemplateAddOnPrice addOnPrice : addOnPrices) {

			AddOn addOn = addOnPrice.getAddOn();

			if (addOnPrice.getBasePrice() == null || addOnPrice.getBasePrice().compareTo(BigDecimal.ZERO) <= 0) {

				LOGGER.error("Invalid add-on price. addOnId: {}", addOn.getAddOnId());

				throw new BadRequestException(ErrorMessageConstants.ADDON_PRICE_REQUIRED);
			}

			validateAddOnReuse(addOn, template.getTemplateId());
		}
	}

	private void validateCustomStructureReuse(String currentTemplateId, CoverageDraftRequestDto coverageRequest) {

		if (coverageRequest.getCoverageTypes() == null) {
			return;
		}

		for (CoverageTypeDraftRequestDto typeRequest : coverageRequest.getCoverageTypes()) {

			validateCoverageTypeReuse(typeRequest.getCoverageTypeName(), currentTemplateId);

			if (typeRequest.getComponents() == null) {
				continue;
			}

			for (ComponentDraftRequestDto componentRequest : typeRequest.getComponents()) {

				validateComponentReuse(componentRequest.getComponentName(), currentTemplateId);

				if (componentRequest.getFields() == null) {
					continue;
				}

				for (FieldDraftRequestDto fieldRequest : componentRequest.getFields()) {

					validateFieldReuse(fieldRequest.getFieldName(), currentTemplateId);
				}
			}
		}

		if (coverageRequest.getAddOns() != null) {

			for (AddOnDraftRequestDto addOnRequest : coverageRequest.getAddOns()) {

				String addOnName = addOnRequest.getAddOnName();

				if (addOnName == null || addOnName.trim().isEmpty()) {

					continue;
				}

				Optional<AddOn> existingAddOn = coverageRepositoryHelper.addOnRepository
						.findByNameIgnoreCase(addOnName.trim());

				if (existingAddOn.isPresent()) {

					validateAddOnReuse(existingAddOn.get(), currentTemplateId);
				}
			}
		}
	}

	private void validateCoverageTypeReuse(String coverageTypeName, String currentTemplateId) {

		if (coverageTypeName == null || coverageTypeName.trim().isEmpty()) {

			return;
		}

		Optional<CoverageType> existingCoverageType = coverageRepositoryHelper.coverageTypeRepository
				.findByNameIgnoreCase(coverageTypeName.trim());

		if (existingCoverageType.isEmpty()) {

			return;
		}

		CoverageTypeCatalog catalog = coverageRepositoryHelper.coverageTypeCatalogRepository
				.findFirstByCoverageTypeCoverageTypeId(existingCoverageType.get().getCoverageTypeId()).orElse(null);

		if (catalog == null) {

			return;
		}

		validateCustomCoverageReuse(catalog.getCoverage(), currentTemplateId);
	}

	private void validateComponentReuse(
			String componentName,
			String currentTemplateId) {

		if (componentName == null
				|| componentName.trim().isEmpty()) {

			return;
		}

		Optional<Component> existingComponent =
				componentRepository.findByNameIgnoreCase(
						componentName.trim());

		if (existingComponent.isEmpty()) {

			return;
		}

		/*
		 * CHANGED:
		 * Component can belong to multiple coverage types.
		 */
		List<ComponentCatalog> componentCatalogs =
				componentCatalogRepository
						.findByComponentComponentId(
								existingComponent.get().getComponentId());

		if (componentCatalogs.isEmpty()) {

			return;
		}

		for (ComponentCatalog componentCatalog
				: componentCatalogs) {

			CoverageType coverageType =
					componentCatalog.getCoverageType();

			if (coverageType == null) {

				continue;
			}

			validateCoverageTypeReuse(
					coverageType.getName(),
					currentTemplateId);
		}
	}


	private void validateFieldReuse(
			String fieldName,
			String currentTemplateId) {

		if (fieldName == null
				|| fieldName.trim().isEmpty()) {

			return;
		}

		Optional<CoverageField> existingField =
				coverageRepositoryHelper
						.coverageFieldRepository
						.findByNameIgnoreCase(
								fieldName.trim());

		if (existingField.isEmpty()) {

			return;
		}

		/*
		 * CHANGED:
		 * Field can belong to multiple components.
		 */
		List<ComponentFieldCatalog> componentFieldCatalogs =
				componentFieldCatalogRepository
						.findByCoverageFieldCoverageFieldId(
								existingField.get()
										.getCoverageFieldId());

		if (componentFieldCatalogs.isEmpty()) {

			return;
		}

		for (ComponentFieldCatalog componentFieldCatalog
				: componentFieldCatalogs) {

			Component component =
					componentFieldCatalog.getComponent();

			if (component == null) {

				continue;
			}

			validateComponentReuse(
					component.getName(),
					currentTemplateId);
		}
	}

	private void validateBiPerPersonAndAccident(Coverage coverage, List<FieldDraftRequestDto> fields) {

		if (coverage == null || coverage.getName() == null) {
			return;
		}

		if (!Constants.LIABILITY_COVERAGE.equalsIgnoreCase(coverage.getName().trim())) {
			return;
		}

		BigDecimal biPerPerson = null;
		BigDecimal biPerAccident = null;

		for (FieldDraftRequestDto fieldRequest : fields) {

			Integer coverageFieldId = fieldRequest.getCoverageFieldId();

			if (coverageFieldId == null) {
				continue;
			}

			CoverageField coverageField = coverageRepositoryHelper.coverageFieldRepository.findById(coverageFieldId)
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.COVERAGE_FIELD_NOT_FOUND));

			String fieldName = coverageField.getName().trim();

			if (Constants.BI_PER_PERSON.equalsIgnoreCase(fieldName)) {

				biPerPerson = fieldRequest.getBasePrice();
			}

			if (Constants.BI_PER_ACCIDENT.equalsIgnoreCase(fieldName)) {

				biPerAccident = fieldRequest.getBasePrice();
			}
		}

		if (biPerPerson != null && biPerAccident != null && biPerPerson.compareTo(biPerAccident) > 0) {

			throw new BadRequestException(ErrorMessageConstants.BI_PER_PERSON_CANNOT_EXCEED_BI_PER_ACCIDENT);
		}
	}

	private void validateCustomDraftLimits(List<CoverageDraftRequestDto> coverages) {

		if (coverages == null) {
			return;
		}

		long customCoverageCount = coverages.stream().filter(c -> Boolean.FALSE.equals(c.getIsStandard())).count();

		if (customCoverageCount > 5) {

			throw new BadRequestException(ErrorMessageConstants.MAXIMUM_5_CUSTOM_COVERAGES_ALLOWED);
		}

		for (CoverageDraftRequestDto coverageRequest : coverages) {

			if (Boolean.TRUE.equals(coverageRequest.getIsStandard())) {

				continue;
			}

			if (coverageRequest.getCoverageTypes() != null && coverageRequest.getCoverageTypes().size() > 5) {

				throw new BadRequestException(ErrorMessageConstants.MAXIMUM_5_COVERAGE_TYPES_ALLOWED);
			}
		}
	}

	private void validateCustomCoveragePublishRules(Template template, Plan plan) {

		List<VehicleTypeCoveragePrice> coveragePrices = coverageRepositoryHelper.vehicleTypeCoveragePriceRepository
				.findByTemplateTemplateIdAndPlanPlanId(template.getTemplateId(), plan.getPlanId());

		for (VehicleTypeCoveragePrice coveragePrice : coveragePrices) {

			Coverage coverage = coveragePrice.getCoverage();

			if (coverage == null || Boolean.TRUE.equals(coverage.getIsStandard())) {

				continue;
			}

			List<CoverageTypeCatalog> coverageTypes = coverageRepositoryHelper.coverageTypeCatalogRepository
					.findByCoverageCoverageId(coverage.getCoverageId());

			// minimum 1 subtype
			if (coverageTypes == null || coverageTypes.isEmpty()) {

				LOGGER.error("Custom coverage must contain at least one subtype. CoverageId: {}",
						coverage.getCoverageId());

				throw new BadRequestException(ErrorMessageConstants.MINIMUM_1_COVERAGE_TYPE_REQUIRED);
			}

			for (CoverageTypeCatalog coverageType : coverageTypes) {

				List<ComponentCatalog> components = componentCatalogRepository
						.findByCoverageTypeCoverageTypeId(coverageType.getCoverageType().getCoverageTypeId());

				// minimum 1 component
				if (components == null || components.isEmpty()) {

					LOGGER.error("Custom coverage subtype must contain at least one component. CoverageTypeId: {}",
							coverageType.getCoverageType().getCoverageTypeId());

					throw new BadRequestException(ErrorMessageConstants.MINIMUM_1_COMPONENT_REQUIRED);
				}
			}
		}
	}

	/*
	 * Duplicate Validation Method
	 */
	private void validateDuplicateRequestEntries(
			List<CoverageDraftRequestDto> coverages) {

		Set<Integer> standardCoverageIds = new HashSet<>();
		Set<String> customCoverageNames = new HashSet<>();

		for (CoverageDraftRequestDto coverageRequest : coverages) {

			if (coverageRequest == null) {
				continue;
			}

			/*
			 * Coverage validation
			 */
			if (Boolean.TRUE.equals(coverageRequest.getIsStandard())) {

				Integer coverageId =
						coverageRequest.getCoverageId();

				if (coverageId != null
						&& !standardCoverageIds.add(coverageId)) {

					throw new BadRequestException(
							ErrorMessageConstants.DUPLICATE_COVERAGE);
				}

			} else {

				String coverageName =
						coverageRequest.getCoverageName();

				if (coverageName != null
						&& !coverageName.isBlank()) {

					String normalizedCoverageName =
							coverageName.trim().toLowerCase();

					if (!customCoverageNames.add(normalizedCoverageName)) {

						throw new BadRequestException(
								ErrorMessageConstants.DUPLICATE_CUSTOM_COVERAGE);

					}
				}
			}

			validateCoverageTypeDuplicates(
					coverageRequest.getCoverageTypes());

			validateAddOnDuplicates(
					coverageRequest.getAddOns());
		}
	}

	private void validateCoverageTypeDuplicates(
			List<CoverageTypeDraftRequestDto> coverageTypes) {

		if (coverageTypes == null) {
			return;
		}

		Set<Integer> coverageTypeIds = new HashSet<>();
		Set<String> coverageTypeNames = new HashSet<>();

		for (CoverageTypeDraftRequestDto typeRequest : coverageTypes) {

			if (typeRequest.getCoverageTypeId() != null) {

				if (!coverageTypeIds.add(
						typeRequest.getCoverageTypeId())) {

					throw new BadRequestException(
							ErrorMessageConstants.DUPLICATE_COVERAGE_TYPE);

				}

			} else if (typeRequest.getCoverageTypeName() != null
					&& !typeRequest.getCoverageTypeName().isBlank()) {

				String normalizedCoverageTypeName =
						typeRequest.getCoverageTypeName()
								.trim()
								.toLowerCase();

				if (!coverageTypeNames.add(
						normalizedCoverageTypeName)) {

					throw new BadRequestException(
							ErrorMessageConstants.DUPLICATE_COVERAGE_TYPE_NOT_ALLOWED);
				}
			}

			validateComponentDuplicates(
					typeRequest.getComponents());
		}
	}

	private void validateComponentDuplicates(
			List<ComponentDraftRequestDto> components) {

		if (components == null) {
			return;
		}

		Set<Integer> componentIds = new HashSet<>();
		Set<String> componentNames = new HashSet<>();

		for (ComponentDraftRequestDto componentRequest : components) {

			if (componentRequest.getComponentId() != null) {

				if (!componentIds.add(
						componentRequest.getComponentId())) {

					throw new BadRequestException(
							ErrorMessageConstants.DUPLICATE_COMPONENT);
				}

			} else if (componentRequest.getComponentName() != null
					&& !componentRequest.getComponentName().isBlank()) {

				String normalizedComponentName =
						componentRequest.getComponentName()
								.trim()
								.toLowerCase();

				if (!componentNames.add(
						normalizedComponentName)) {

					throw new BadRequestException(
							ErrorMessageConstants.DUPLICATE_COMPONENT_NOT_ALLOWED);
				}
			}

			validateFieldDuplicates(
					componentRequest.getFields());
		}
	}

	private void validateFieldDuplicates(
			List<FieldDraftRequestDto> fields) {

		if (fields == null) {
			return;
		}

		Set<Integer> fieldIds = new HashSet<>();
		Set<String> fieldNames = new HashSet<>();

		for (FieldDraftRequestDto fieldRequest : fields) {

			if (fieldRequest.getCoverageFieldId() != null) {

				if (!fieldIds.add(
						fieldRequest.getCoverageFieldId())) {

					throw new BadRequestException(
							ErrorMessageConstants.DUPLICATE_FIELD);
				}

			} else if (fieldRequest.getFieldName() != null
					&& !fieldRequest.getFieldName().isBlank()) {

				String normalizedFieldName =
						fieldRequest.getFieldName()
								.trim()
								.toLowerCase();

				if (!fieldNames.add(
						normalizedFieldName)) {

					throw new BadRequestException(
							ErrorMessageConstants.DUPLICATE_FIELD_NOT_ALLOWED);
				}
			}
		}
	}

	private void validateAddOnDuplicates(
			List<AddOnDraftRequestDto> addOns) {

		if (addOns == null) {
			return;
		}

		Set<Integer> addOnIds = new HashSet<>();
		Set<String> addOnNames = new HashSet<>();

		for (AddOnDraftRequestDto addOnRequest : addOns) {

			if (addOnRequest.getAddOnId() != null) {

				if (!addOnIds.add(
						addOnRequest.getAddOnId())) {

					throw new BadRequestException(
							ErrorMessageConstants.DUPLICATE_ADD_ON);

				}

			} else if (addOnRequest.getAddOnName() != null
					&& !addOnRequest.getAddOnName().isBlank()) {

				String normalizedAddOnName =
						addOnRequest.getAddOnName()
								.trim()
								.toLowerCase();

				if (!addOnNames.add(
						normalizedAddOnName)) {

					throw new BadRequestException(
							ErrorMessageConstants.DUPLICATE_ADD_ON_NOT_ALLOWED);
				}
			}
		}
	}
}