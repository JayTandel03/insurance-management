package com.onerivet.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.onerivet.model.dto.response.GenericResponse;
import com.onerivet.model.projection.VehicleVariantProjection;
import com.onerivet.service.VehicleVariantService;
import com.onerivet.util.Constants;

@RestController
@RequestMapping("/api/vehicle-types")
public class VehicleVariantController {

    private final VehicleVariantService vehicleVariantService;

    public VehicleVariantController(VehicleVariantService vehicleVariantService) {
        this.vehicleVariantService = vehicleVariantService;
    }

    @PreAuthorize(Constants.HAS_USER)
    @GetMapping("/{vehicleTypeId}/variants")
    public GenericResponse<List<VehicleVariantProjection>> getVehicleVariants(
            @PathVariable Integer vehicleTypeId) {

        return new GenericResponse<>(
                vehicleVariantService.getVariantsByVehicleTypeId(vehicleTypeId));
    }

}