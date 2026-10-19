package com.onerivet.model.entity;

import com.onerivet.util.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "[CoverageType]", schema = Constants.COVERAGE_SCHEMA)
public class CoverageType extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "[CoverageTypeId]")
	private Integer coverageTypeId;

	@Column(name = "[CoverageType]")
	private String name;
	
	public Integer getCoverageTypeId() {
		return coverageTypeId;
	}

	public void setCoverageTypeId(Integer coverageTypeId) {
		this.coverageTypeId = coverageTypeId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}