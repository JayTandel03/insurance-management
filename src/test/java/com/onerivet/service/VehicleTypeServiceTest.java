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
import com.onerivet.model.dto.VehicleTypeResponseDto;
import com.onerivet.model.projection.VehicleTypeProjection;
import com.onerivet.repository.VehicleTypeRepository;
import com.onerivet.util.ErrorMessageConstants;

class VehicleTypeServiceTest {

    @Mock
    private VehicleTypeRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private VehicleTypeService vehicleTypeService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnVehicleTypes_whenDataExists() {

        VehicleTypeProjection projection = new VehicleTypeProjection(1, "Car");

        when(repository.findVehicleTypes())
                .thenReturn(List.of(projection));

        VehicleTypeResponseDto dto = new VehicleTypeResponseDto();
        dto.setVehicleTypeId(1);
        dto.setVehicle("Car");

        when(modelMapper.map(projection, VehicleTypeResponseDto.class))
                .thenReturn(dto);

        List<VehicleTypeResponseDto> result =
                vehicleTypeService.getVehicleTypes();

        assertEquals(1, result.size());
        assertEquals("Car", result.get(0).getVehicle());
    }

    @Test
    void shouldThrowException_whenNoVehicleTypesFound_emptyList() {

        when(repository.findVehicleTypes())
                .thenReturn(Collections.emptyList());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> vehicleTypeService.getVehicleTypes()
        );

        assertEquals(ErrorMessageConstants.VEHICLETYPE_NOT_FOUND, ex.getMessage());
    }

    @Test
    void shouldThrowException_whenNoVehicleTypesFound_null() {

        when(repository.findVehicleTypes())
                .thenReturn(null);

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> vehicleTypeService.getVehicleTypes()
        );

        assertEquals(ErrorMessageConstants.VEHICLETYPE_NOT_FOUND, ex.getMessage());
    }
    
}