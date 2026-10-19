package com.onerivet.model.dto;

import java.math.BigDecimal;

public class CoveragePremiumDto {
	private Integer coverageId;
	private String name;
	private String coverageType;
	private BigDecimal basePrice;

	public Integer getCoverageId() {
		return coverageId;
	}

	public void setCoverageId(Integer coverageId) {
		this.coverageId = coverageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoverageType() {
		return coverageType;
	}

	public void setCoverageType(String coverageType) {
		this.coverageType = coverageType;
	}

	public BigDecimal getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(BigDecimal basePrice) {
		this.basePrice = basePrice;
	}
}