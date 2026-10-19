package com.onerivet.service.helper;

import org.springframework.stereotype.Component;
import com.onerivet.repository.TemplateAddOnPriceRepository;
import com.onerivet.repository.TemplateCoverageFieldPriceRepository;
import com.onerivet.repository.TemplateCoverageRepository;
import com.onerivet.repository.TemplatePlanRepository;
import com.onerivet.repository.TemplateRepository;

@Component
public class TemplateRepositoryHelper {

	public final TemplateRepository templateRepository;
	public final TemplatePlanRepository templatePlanRepository;
	public final TemplateAddOnPriceRepository templateAddOnPriceRepository;
	public final TemplateCoverageRepository templateCoverageRepository;
	public final TemplateCoverageFieldPriceRepository templateCoverageFieldPriceRepository;

	public TemplateRepositoryHelper(TemplateRepository templateRepository,
			TemplatePlanRepository templatePlanRepository, TemplateAddOnPriceRepository templateAddOnPriceRepository,
			TemplateCoverageRepository templateCoverageRepository,
			TemplateCoverageFieldPriceRepository templateCoverageFieldPriceRepository) {

		this.templateRepository = templateRepository;
		this.templatePlanRepository = templatePlanRepository;
		this.templateAddOnPriceRepository = templateAddOnPriceRepository;
		this.templateCoverageRepository = templateCoverageRepository;
		this.templateCoverageFieldPriceRepository = templateCoverageFieldPriceRepository;
	}

	public TemplateRepository getTemplateRepository() {
		return templateRepository;
	}

	public TemplatePlanRepository getTemplatePlanRepository() {
		return templatePlanRepository;
	}

	public TemplateAddOnPriceRepository getTemplateAddOnPriceRepository() {
		return templateAddOnPriceRepository;
	}

	public TemplateCoverageRepository getTemplateCoverageRepository() {
		return templateCoverageRepository;
	}

	public TemplateCoverageFieldPriceRepository getTemplateCoverageFieldPriceRepository() {
		return templateCoverageFieldPriceRepository;
	}
}