package com.onerivet.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.onerivet.model.dto.PlanResponseDto;
import com.onerivet.model.dto.response.GenericResponse;
import com.onerivet.service.PlanService;
import com.onerivet.util.Constants;

@RestController
@RequestMapping("/api/plans")
public class PlanController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlanController.class);

	private final PlanService planService;

	public PlanController(PlanService planService) {
		this.planService = planService;
	}

	@PreAuthorize(Constants.HAS_ADMIN)
	@GetMapping
	public GenericResponse<List<PlanResponseDto>> getPlans() {

		LOGGER.info("Fetching plans");

		return new GenericResponse<>(planService.getPlans());
	}
}