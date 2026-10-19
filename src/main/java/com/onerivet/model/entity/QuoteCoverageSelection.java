package com.onerivet.model.entity;

import java.time.LocalDateTime;

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
@Table(name = "[QuoteCoverageSelection]", schema = Constants.QUOTE_SCHEMA)
public class QuoteCoverageSelection extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "[QuoteCoverageSelectionId]")
	private Integer quoteCoverageSelectionId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[QuoteId]")
	private Quote quote;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[CoverageId]")
	private Coverage coverage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[CoverageTypeId]")
	private CoverageType coverageType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[AddOnId]")
	private AddOn addOn;

	@Column(name = "[DeletedDate]")
	private LocalDateTime deletedDate;

	public Integer getQuoteCoverageSelectionId() {
		return quoteCoverageSelectionId;
	}

	public void setQuoteCoverageSelectionId(Integer quoteCoverageSelectionId) {
		this.quoteCoverageSelectionId = quoteCoverageSelectionId;
	}

	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
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

	public AddOn getAddOn() {
		return addOn;
	}

	public void setAddOn(AddOn addOn) {
		this.addOn = addOn;
	}

	public LocalDateTime getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(LocalDateTime deletedDate) {
		this.deletedDate = deletedDate;
	}
}