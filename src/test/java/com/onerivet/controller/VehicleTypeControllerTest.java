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
import com.onerivet.model.dto.VehicleTypeResponseDto;
import com.onerivet.service.VehicleTypeService;

class VehicleTypeControllerTest {

    @Mock
    private VehicleTypeService service;

    @InjectMocks
    private VehicleTypeController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldReturnVehicleTypes() throws Exception {

        VehicleTypeResponseDto dto = new VehicleTypeResponseDto();
        dto.setVehicleTypeId(1);
        dto.setVehicle("Car");

        when(service.getVehicleTypes()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/vehicle-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].vehicle").value("Car"));
    }
    
}