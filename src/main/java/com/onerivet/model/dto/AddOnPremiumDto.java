package com.onerivet.model.dto;

import java.math.BigDecimal;

public class AddOnPremiumDto {

    private Integer addOnId;
    private String name;
    private BigDecimal basePrice;
    
    public Integer getAddOnId() {
        return addOnId;
    }

    public void setAddOnId(Integer addOnId) {
        this.addOnId = addOnId;
    } 

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }
}