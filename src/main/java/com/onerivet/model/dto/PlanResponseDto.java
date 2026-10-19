package com.onerivet.model.dto;

public class PlanResponseDto {
    private Integer planId;
    private String plan;

    public PlanResponseDto() {}

    public PlanResponseDto(Integer planId, String plan) {
        this.planId = planId;
        this.plan = plan;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}