package com.onerivet.model.entity;

import com.onerivet.util.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "[CoverageTypeCatalog]", schema = Constants.COVERAGE_SCHEMA)
public class CoverageTypeCatalog extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "[CoverageTypeCatalogId]")
	private Integer coverageTypeCatalogId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[CoverageId]")
	private Coverage coverage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[CoverageTypeId]")
	private CoverageType coverageType;
	
	public Integer getCoverageTypeCatalogId() {
		return coverageTypeCatalogId;
	}

	public void setCoverageTypeCatalogId(Integer coverageTypeCatalogId) {
		this.coverageTypeCatalogId = coverageTypeCatalogId;
	}

	public Coverage getCoverage() {
		return coverage;
	}

	public void setCoverage(Coverage coverage) {
		this.coverage = coverage;
	}

	public CoverageType getCoverageType() {
		return coverageType;
	}

	public void setCoverageType(CoverageType coverageType) {
		this.coverageType = coverageType;
	}
	
}