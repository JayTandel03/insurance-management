package com.onerivet.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OptionRequestDto {

	@NotBlank(message = "Option label is required")
	@Size(min = 2, max = 60, message = "Option label should be between 2 and 60 characters")
	@Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Option label only accepts alphabets and numbers")
	private String optionLabel;

	public OptionRequestDto() {
	}

	public OptionRequestDto(String optionLabel) {

		this.optionLabel = optionLabel;
	}

	public String getOptionLabel() {
		return optionLabel;
	}

	public void setOptionLabel(String optionLabel) {
		this.optionLabel = optionLabel;
	}
}