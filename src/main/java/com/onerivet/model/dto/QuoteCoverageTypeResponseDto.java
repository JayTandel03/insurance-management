package com.onerivet.model.dto;

public class QuoteCoverageTypeResponseDto {
	private Integer coverageTypeId;
	private String coverageType;

	public QuoteCoverageTypeResponseDto(Integer coverageTypeId, String coverageType) {
		this.coverageTypeId = coverageTypeId;
		this.coverageType = coverageType;
	}

	public Integer getCoverageTypeId() {
		return coverageTypeId;
	}

	public String getCoverageType() {
		return coverageType;
	}
}