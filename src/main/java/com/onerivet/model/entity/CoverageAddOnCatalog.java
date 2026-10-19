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
@Table(name = "[CoverageAddonCatalog]", schema = Constants.COVERAGE_SCHEMA)
public class CoverageAddOnCatalog extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "[CoverageAddonCatalogId]")
	private Integer coverageAddonCatalogId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[CoverageId]")
	private Coverage coverage;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[AddOnId]")
	private AddOn addOn;

	public Integer getCoverageAddonCatalogId() {
		return coverageAddonCatalogId;
	}

	public void setCoverageAddonCatalogId(Integer coverageAddOnCatalogId) {
		this.coverageAddonCatalogId = coverageAddOnCatalogId;
	}

	public Coverage getCoverage() {
		return coverage;
	}

	public void setCoverage(Coverage coverage) {
		this.coverage = coverage;
	}

	public AddOn getAddOn() {
		return addOn;
	}

	public void setAddOn(AddOn addOn) {
		this.addOn = addOn;
	}
	
}