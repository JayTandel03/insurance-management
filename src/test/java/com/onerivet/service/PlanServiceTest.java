package com.onerivet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import com.onerivet.exception.ResourceNotFoundException;
import com.onerivet.model.dto.PlanResponseDto;
import com.onerivet.model.projection.PlanProjection;
import com.onerivet.repository.PlanRepository;
import com.onerivet.util.ErrorMessageConstants;

class PlanServiceTest {

    @Mock
    private PlanRepository planRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PlanService planService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnPlans_whenDataExists() {

        PlanProjection projection = new PlanProjection(1, "Basic");

        when(planRepository.findPlans())
                .thenReturn(List.of(projection));

        PlanResponseDto dto = new PlanResponseDto();
        dto.setPlanId(1);
        dto.setPlan("Basic");

        when(modelMapper.map(projection, PlanResponseDto.class))
                .thenReturn(dto);

        List<PlanResponseDto> result = planService.getPlans();

        assertEquals(1, result.size());
        assertEquals("Basic", result.get(0).getPlan());
    }

    @Test
    void shouldThrowException_whenPlansEmpty() {

        when(planRepository.findPlans())
                .thenReturn(Collections.emptyList());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> planService.getPlans()
        );

        assertEquals(ErrorMessageConstants.PLANS_NOT_FOUND, ex.getMessage());
    }

    @Test
    void shouldThrowException_whenPlansNull() {  

        when(planRepository.findPlans())
                .thenReturn(null);

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> planService.getPlans()
        );

        assertEquals(ErrorMessageConstants.PLANS_NOT_FOUND, ex.getMessage());
    }
    
}