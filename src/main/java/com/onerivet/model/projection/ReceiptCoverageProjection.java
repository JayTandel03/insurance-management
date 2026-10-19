package com.onerivet.model.projection;

import java.math.BigDecimal;

public class ReceiptCoverageProjection {

	private Integer coverageId;
	private String coverageName;
	private Integer coverageTypeId;
	private String coverageTypeName;
	private BigDecimal premium;

	public ReceiptCoverageProjection(Integer coverageId, String coverageName, Integer coverageTypeId,
			String coverageTypeName, BigDecimal premium) {

		this.coverageId = coverageId;
		this.coverageName = coverageName;
		this.coverageTypeId = coverageTypeId;
		this.coverageTypeName = coverageTypeName;
		this.premium = premium;
	}

	public Integer getCoverageId() {
		return coverageId;
	}

	public String getCoverageName() {
		return coverageName;
	}

	public Integer getCoverageTypeId() {
		return coverageTypeId;
	}

	public String getCoverageTypeName() {
		return coverageTypeName;
	}

	public BigDecimal getPremium() {
		return premium;
	}
}
