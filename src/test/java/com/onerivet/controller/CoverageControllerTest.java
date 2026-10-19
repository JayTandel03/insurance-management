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
import com.onerivet.exception.GlobalExceptionHandler;
import com.onerivet.model.dto.CoverageResponseDto;
import com.onerivet.model.projection.AddOnProjection;
import com.onerivet.service.AddOnService;
import com.onerivet.service.CoverageService;

class CoverageControllerTest {

    @Mock
    private CoverageService coverageService;

    @Mock 
    private AddOnService addOnService;

    @InjectMocks
    private CoverageController coverageController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(coverageController)
                .setControllerAdvice(new GlobalExceptionHandler<>())
                .build();
    }

    @Test 
    void shouldReturnCoverage() throws Exception {

        CoverageResponseDto dto = new CoverageResponseDto();
        dto.setCoverageId(1);
        dto.setCoverage("Coverage1");

        when(coverageService.getCoverage())
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/coverages")
                        .param("templateId", "T1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].coverageId").value(1));
    }

    @Test
    void shouldReturnNotFound_whenTemplateMissing() throws Exception {

        when(coverageService.getCoverage())
                .thenThrow(new RuntimeException("Template not found"));

        mockMvc.perform(get("/api/coverages")
                        .param("templateId", "T1"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldReturnAddOns() throws Exception {

        AddOnProjection addon = new AddOnProjection(1, "Addon1");

        when(addOnService.getAddOns(1))
                .thenReturn(List.of(addon));

        mockMvc.perform(get("/api/coverages/1/add-ons")
                        .param("templateId", "T1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("Addon1"));
    }

    @Test
    void shouldReturnBadRequest_whenInvalidParam() throws Exception {

        when(addOnService.getAddOns(0))
                .thenThrow(new IllegalArgumentException("Invalid Parameter"));

        mockMvc.perform(get("/api/coverages/0/add-ons")
                        .param("templateId", "T1"))
                .andExpect(status().isBadRequest());
    }
    
}