package com.onerivet.model.projection;

public class OptionDependencyProjection {

	private final Integer optionId;
	private final String optionLabel;

	public OptionDependencyProjection(Integer optionId, String optionLabel) {
		this.optionId = optionId;
		this.optionLabel = optionLabel;
	}

	public Integer getOptionId() {
		return optionId;
	}

	public String getOptionLabel() {
		return optionLabel;
	}
}