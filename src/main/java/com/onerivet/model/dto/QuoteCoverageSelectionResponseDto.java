package com.onerivet.model.dto;

import java.math.BigDecimal;
import java.util.List;

public class QuoteCoverageSelectionResponseDto {

	private Integer coverageId;
	private String coverage;
	private BigDecimal basePrice;
	private List<QuoteCoverageTypeResponseDto> coverageTypes;
	private List<AddOnResponseDto> addOns;

	public QuoteCoverageSelectionResponseDto(Integer coverageId, String coverage, BigDecimal basePrice,
			List<QuoteCoverageTypeResponseDto> coverageTypes, List<AddOnResponseDto> addOns) {
		this.coverageId = coverageId;
		this.coverage = coverage;
		this.basePrice = basePrice;
		this.coverageTypes = coverageTypes;
		this.addOns = addOns;
	}

	public Integer getCoverageId() {
		return coverageId;
	}

	public String getCoverage() {
		return coverage;
	}

	public BigDecimal getBasePrice() {
		return basePrice;
	}

	public List<QuoteCoverageTypeResponseDto> getCoverageTypes() {
		return coverageTypes;
	}

	public List<AddOnResponseDto> getAddOns() {
		return addOns;
	}
}