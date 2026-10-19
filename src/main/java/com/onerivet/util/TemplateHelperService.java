package com.onerivet.util;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.onerivet.exception.ResourceNotFoundException;
import com.onerivet.model.entity.Template;
import com.onerivet.repository.TemplatePlanRepository;
import com.onerivet.repository.TemplateRepository;

@Service
public class TemplateHelperService {

	private static final Logger log = LoggerFactory.getLogger(TemplateHelperService.class);

	private final TemplateRepository templateRepository;
	private final TemplatePlanRepository templatePlanRepository;

	public TemplateHelperService(TemplateRepository templateRepository, TemplatePlanRepository templatePlanRepository) {
		this.templateRepository = templateRepository;
		this.templatePlanRepository = templatePlanRepository;
	}

	public Template getTemplate(String templateId) {
		return templateRepository.findById(templateId).orElseThrow(() -> {
			log.error("Template not found: {}", templateId);
			return new ResourceNotFoundException(ErrorMessageConstants.TEMPLATE_NOT_FOUND);
		});
	}

	public Integer getVehicleTypeId(Template template) {
		return template.getVehicleType().getVehicleTypeId();
	}

	public List<Integer> getPlanIds(String templateId) {
		return templatePlanRepository.getPlans(templateId).stream()
				.map(templatePlan -> templatePlan.getPlan().getPlanId()).toList();
	}

	public void validatePlans(List<Integer> planIds, String templateId) {
		
		if (planIds.isEmpty()) {
			log.error("No plans found for template: {}", templateId);
			throw new ResourceNotFoundException(ErrorMessageConstants.PLANS_NOT_FOUND);
		}
	}

}
