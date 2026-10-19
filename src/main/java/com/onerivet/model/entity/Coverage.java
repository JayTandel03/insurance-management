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
@Table(name = "[Coverage]", schema = Constants.COVERAGE_SCHEMA)
public class Coverage extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "[CoverageId]")
	private Integer coverageId;

	@Column(name = "[Coverage]")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="[StatusId]")
	private Status status;
	
	@Column(name="[IsStandard]")
	private Boolean isStandard;
	
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Boolean getIsStandard() {
		return isStandard;
	}

	public void setIsStandard(Boolean isStandard) {
		this.isStandard = isStandard;
	}

}