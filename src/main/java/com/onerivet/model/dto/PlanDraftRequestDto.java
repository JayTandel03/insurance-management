package com.onerivet.model.dto;
import java.util.List;
import jakarta.validation.Valid;

import jakarta.validation.constraints.NotNull;
public class PlanDraftRequestDto {
    @NotNull(message = "Plan is required")
    private Integer planId;
    @Valid
    private List<CoverageDraftRequestDto> coverages;
    public Integer getPlanId() {
        return planId;
    }
    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
    public List<CoverageDraftRequestDto> getCoverages() {
        return coverages;
    }
    public void setCoverages(List<CoverageDraftRequestDto> coverages) {
        this.coverages = coverages;
    }
}