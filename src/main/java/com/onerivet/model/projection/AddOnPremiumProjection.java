package com.onerivet.model.projection;

import java.math.BigDecimal;

public class AddOnPremiumProjection {

	private final Integer addOnId;
	private final String name;
	private final BigDecimal basePrice;

	public AddOnPremiumProjection(Integer addOnId, String name, BigDecimal basePrice) {
		this.addOnId = addOnId;
		this.name = name;
		this.basePrice = basePrice;
	}
 
	public Integer getAddOnId() {
		return addOnId;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getBasePrice() {
		return basePrice;
	}
}