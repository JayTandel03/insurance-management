package com.onerivet.model.projection;

public class InputTypeProjection {

	private final Integer inputTypeId;
	private final String inputType;

	public InputTypeProjection(Integer inputTypeId, String inputType) {
		this.inputTypeId = inputTypeId;
		this.inputType = inputType;
	}

	public Integer getInputTypeId() {
		return inputTypeId;
	}

	public String getInputType() {
		return inputType;
	}
}
