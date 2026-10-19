package com.onerivet.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.onerivet.exception.ResourceNotFoundException;
import com.onerivet.model.entity.Plan;
import com.onerivet.model.entity.Template;
import com.onerivet.model.entity.TemplatePlan;
import com.onerivet.model.entity.VehicleType;
import com.onerivet.repository.TemplatePlanRepository;
import com.onerivet.repository.TemplateRepository;

class TemplateHelperServiceTest {

	private TemplateRepository templateRepository;
	private TemplatePlanRepository templatePlanRepository;
	private TemplateHelperService templateHelperService;

	@BeforeEach
	void setUp() {

		templateRepository = mock(TemplateRepository.class);
		templatePlanRepository = mock(TemplatePlanRepository.class);
		templateHelperService = new TemplateHelperService(templateRepository, templatePlanRepository);
	}

	@Test
	void shouldReturnTemplate() {

		Template template = new Template();

		when(templateRepository.findById("T1")).thenReturn(Optional.of(template));
		
		Template result = templateHelperService.getTemplate("T1");
		assertEquals(template, result);
	}

	@Test
	void shouldThrowExceptionWhenTemplateNotFound() {

		when(templateRepository.findById("T1")).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> templateHelperService.getTemplate("T1"));
	}

	@Test
	void shouldReturnVehicleTypeId() {

		VehicleType vehicleType = new VehicleType();
		vehicleType.setVehicleTypeId(1);

		Template template = new Template();
		template.setVehicleType(vehicleType);

		Integer result = templateHelperService.getVehicleTypeId(template);

		assertEquals(1, result);
	}

	@Test
	void shouldReturnPlanIds() {

		Plan plan1 = new Plan();
		plan1.setPlanId(101);

		Plan plan2 = new Plan();
		plan2.setPlanId(102);

		TemplatePlan templatePlan1 = new TemplatePlan();
		templatePlan1.setPlan(plan1);

		TemplatePlan templatePlan2 = new TemplatePlan();
		templatePlan2.setPlan(plan2);

		when(templatePlanRepository.getPlans("T1")).thenReturn(List.of(templatePlan1, templatePlan2));

		List<Integer> result = templateHelperService.getPlanIds("T1");
		assertEquals(2, result.size());
		assertEquals(101, result.get(0));
		assertEquals(102, result.get(1));
	}

	@Test
	void shouldValidatePlansSuccessfully() {

		List<Integer> planIds = List.of(1, 2);
		assertDoesNotThrow(() -> templateHelperService.validatePlans(planIds, "T1"));
	}

	@Test
	void shouldThrowExceptionWhenPlansAreEmpty() {

		List<Integer> planIds = List.of();
		assertThrows(ResourceNotFoundException.class, () -> templateHelperService.validatePlans(planIds, "T1"));
	}

}