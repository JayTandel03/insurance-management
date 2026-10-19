package com.onerivet.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.onerivet.exception.ResourceNotFoundException;
import com.onerivet.model.dto.PlanResponseDto;
import com.onerivet.model.projection.PlanProjection;
import com.onerivet.repository.PlanRepository;
import com.onerivet.util.ErrorMessageConstants;

@Service
public class PlanService {

    private static final Logger logger =
            LoggerFactory.getLogger(PlanService.class);

    private final PlanRepository planRepository;

    private final ModelMapper modelMapper;


    public PlanService(PlanRepository planRepository, ModelMapper modelMapper) {
        this.planRepository = planRepository;
        this.modelMapper = modelMapper;
    }

    public List<PlanResponseDto> getPlans() {

        logger.info("Fetching plans");

        List<PlanProjection> planProjectionList =
                planRepository.findPlans();

        if (planProjectionList == null || planProjectionList.isEmpty()) {
            logger.error("No plans found");
            throw new ResourceNotFoundException(ErrorMessageConstants.PLANS_NOT_FOUND);
        }

        List<PlanResponseDto> result = planProjectionList.stream()
                .map(planProjection -> modelMapper.map(planProjection, PlanResponseDto.class))
                .toList();

        logger.info("Fetched {} plans", result.size());

        return result;
    }
}