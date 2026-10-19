package com.onerivet.model.dto;

public class QuoteOptionResponseDto {

    private Integer optionId;
    private String optionLabel;

    public QuoteOptionResponseDto(Integer optionId,
                             String optionLabel) {
        this.optionId = optionId;
        this.optionLabel = optionLabel;
    }

	public Integer getOptionId() {
		return optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	public String getOptionLabel() {
		return optionLabel;
	}

	public void setOptionLabel(String optionLabel) {
		this.optionLabel = optionLabel;
	}
}