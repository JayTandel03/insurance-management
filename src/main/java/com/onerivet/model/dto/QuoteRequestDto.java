package com.onerivet.model.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onerivet.util.Constants;
import com.onerivet.util.ErrorMessageConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class QuoteRequestDto {

    private String quoteId;

    @NotNull(message = ErrorMessageConstants.VEHICLE_TYPE_REQUIRED)
    @Positive(message = Constants.INVALID_VEHICLE_TYPE)
    private Integer vehicleTypeId;

    @JsonProperty("CarRegistrationYear")
    private Integer carRegistrationYear;

    @JsonProperty("CarVariantId")
    private Integer carVariantId;

    @JsonProperty("MotorcycleRegistrationYear")
    private Integer motorCycleRegistrationYear;

    @JsonProperty("MotorcycleVariantId")
    private Integer motorCycleVariantId;

    @Valid
    private List<QuoteAnswerRequestDto> answers;

    private String templateId;

    private Integer planId;

    @Valid
    private List<QuoteCoverageSelectionRequestDto> coverageSelections;

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public Integer getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(Integer vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public Integer getCarRegistrationYear() {
        return carRegistrationYear;
    }

    public void setCarRegistrationYear(Integer carRegistrationYear) {
        this.carRegistrationYear = carRegistrationYear;
    }

    public Integer getCarVariantId() {
        return carVariantId;
    }

    public void setCarVariantId(Integer carVariantId) {
        this.carVariantId = carVariantId;
    }


    public List<QuoteAnswerRequestDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QuoteAnswerRequestDto> answers) {
        this.answers = answers;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public List<QuoteCoverageSelectionRequestDto> getCoverageSelections() {
        return coverageSelections;
    }

    public void setCoverageSelections(List<QuoteCoverageSelectionRequestDto> coverageSelections) {
        this.coverageSelections = coverageSelections;
    }

    public Integer getMotorCycleRegistrationYear() {
        return motorCycleRegistrationYear;
    }

    public void setMotorCycleRegistrationYear(Integer motorCycleRegistrationYear) {
        this.motorCycleRegistrationYear = motorCycleRegistrationYear;
    }

    public Integer getMotorCycleVariantId() {
        return motorCycleVariantId;
    }

    public void setMotorCycleVariantId(Integer motorCycleVariantId) {
        this.motorCycleVariantId = motorCycleVariantId;
    }
}
