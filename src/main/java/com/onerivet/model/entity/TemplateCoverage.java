package com.onerivet.model.entity;

import java.time.LocalDateTime;
import com.onerivet.util.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "[TemplateCoverage]", schema = Constants.TEMPLATE_SCHEMA)
public class TemplateCoverage extends BaseEntity {

	@Id
	@Column(name = "[TemplateCoverageId]")
	private String templateCoverageId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[TemplatePlanId]")
	private TemplatePlan templatePlan;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[CoverageTypeCatalogId]")
	private CoverageTypeCatalog coverageTypeCatalog;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[CoverageAddonCatalogId]")
	private CoverageAddOnCatalog coverageAddOnCatalog;
	
	@Column(name = "[DeletedDate]")
	private LocalDateTime deletedDate;

	public String getTemplateCoverageId() {
		return templateCoverageId;
	}

	public void setTemplateCoverageId(String templateCoverageId) {
		this.templateCoverageId = templateCoverageId;
	}

	public TemplatePlan getTemplatePlan() {
		return templatePlan;
	}

	public void setTemplatePlan(TemplatePlan templatePlan) {
		this.templatePlan = templatePlan;
	}

	public CoverageTypeCatalog getCoverageTypeCatalog() {
		return coverageTypeCatalog;
	}

	public void setCoverageTypeCatalog(CoverageTypeCatalog coverageTypeCatalog) {
		this.coverageTypeCatalog = coverageTypeCatalog;
	}

	public CoverageAddOnCatalog getCoverageAddOnCatalog() {
		return coverageAddOnCatalog;
	}

	public void setCoverageAddOnCatalog(CoverageAddOnCatalog coverageAddOnCatalog) {
		this.coverageAddOnCatalog = coverageAddOnCatalog;
	}

	public LocalDateTime getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(LocalDateTime deletedDate) {
		this.deletedDate = deletedDate;
	}
	
}