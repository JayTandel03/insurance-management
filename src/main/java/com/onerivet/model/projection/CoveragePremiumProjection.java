package com.onerivet.model.projection;

import java.math.BigDecimal;

public class CoveragePremiumProjection {
	private final Integer coverageId;
	private final String name;
	private final String coverageType;
	private final BigDecimal basePrice;

	public CoveragePremiumProjection(Integer coverageId, String name, String coverageType, BigDecimal basePrice) {

		this.coverageId = coverageId;
		this.name = name;
		this.coverageType = coverageType;
		this.basePrice = basePrice;
	}

	public Integer getCoverageId() {
		return coverageId;
	}

	public String getName() {
		return name;
	}

	public String getCoverageType() {
		return coverageType;
	}

	public BigDecimal getBasePrice() {
		return basePrice;
	}
}