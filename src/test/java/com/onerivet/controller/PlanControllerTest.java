package com.onerivet.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.onerivet.model.dto.PlanResponseDto;
import com.onerivet.service.PlanService;

class PlanControllerTest {

    @Mock
    private PlanService service;

    @InjectMocks
    private PlanController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldReturnPlans() throws Exception {

        PlanResponseDto dto = new PlanResponseDto();
        dto.setPlanId(1);
        dto.setPlan("Basic");

        when(service.getPlans()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/plans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].plan").value("Basic"));
    }
    
}
