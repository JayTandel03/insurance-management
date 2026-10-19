package com.onerivet.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.onerivet.model.dto.CoverageResponseDto;
import com.onerivet.model.dto.response.GenericResponse;
import com.onerivet.model.projection.AddOnProjection;
import com.onerivet.service.AddOnService;
import com.onerivet.service.CoverageService;
import com.onerivet.util.Constants;

@RestController
@RequestMapping("/api/coverages")
public class CoverageController {

    private static final Logger logger = LoggerFactory.getLogger(CoverageController.class);

    private final CoverageService coverageService;
    private final AddOnService addOnService;

    public CoverageController(CoverageService coverageService, AddOnService addOnService) {
        this.coverageService = coverageService;
        this.addOnService = addOnService;
    }

    @PreAuthorize(Constants.HAS_ADMIN)
    @GetMapping
    public GenericResponse<List<CoverageResponseDto>> getCoverage() {
        logger.info("Fetching published coverages");

        List<CoverageResponseDto> response =
                coverageService.getCoverage();

        return new GenericResponse<>(response);
    }

    @PreAuthorize(Constants.HAS_ADMIN)
    @GetMapping("/{coverageId}/add-ons")
    public GenericResponse<List<AddOnProjection>> getAddOns(
            @PathVariable Integer coverageId){

        logger.info("Fetching add-ons for coverageId: {}",
                coverageId);

        return new GenericResponse<>(
                addOnService.getAddOns(coverageId)
        );
    }
}