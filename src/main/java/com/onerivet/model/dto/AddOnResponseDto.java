package com.onerivet.model.dto;

public class AddOnResponseDto {
	private Integer addOnId;
	private String addOn;

	public AddOnResponseDto(Integer addOnId, String addOn) {
		this.addOnId = addOnId;
		this.addOn = addOn;
	}

	public Integer getAddOnId() {
		return addOnId;
	}

	public void setAddOnId(Integer addOnId) {
		this.addOnId = addOnId;
	}

	public String getAddOn() {
		return addOn;
	}

	public void setAddOn(String addOn) {
		this.addOn = addOn;
	}

}