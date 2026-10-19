package com.onerivet.model.projection;

public class PlanProjection {

    private Integer planId;
    private String plan;

    public PlanProjection(Integer planId, String plan) {
        this.planId = planId;
        this.plan = plan;
    }

    public Integer getPlanId() {
        return planId;
    }

    public String getPlan() {
        return plan;
    }
}