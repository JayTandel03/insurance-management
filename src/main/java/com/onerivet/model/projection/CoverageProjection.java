package com.onerivet.model.projection;

public class CoverageProjection {

	private Integer coverageId;
	private String coverage;
	private Boolean isStandard;
	private Integer coverageTypeId;
	private String coverageType;

	public CoverageProjection(Integer coverageId, String coverage, Boolean isStandard, Integer coverageTypeId,String coverageType) {
		this.coverageId = coverageId;
		this.coverage = coverage;
		this.isStandard = isStandard;
		this.coverageTypeId = coverageTypeId;
		this.coverageType = coverageType;
	}

	public Integer getCoverageId() {
		return coverageId;
	}

	public String getCoverage() {
		return coverage;
	}

	public Boolean getIsStandard() {
		return isStandard;
	}

	public Integer getCoverageTypeId() {
		return coverageTypeId;
	}

	public String getCoverageType() {
		return coverageType;
	}

}