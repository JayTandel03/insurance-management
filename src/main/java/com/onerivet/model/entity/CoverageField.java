package com.onerivet.model.entity;

import com.onerivet.util.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "[CoverageField]", schema = Constants.COVERAGE_SCHEMA)
public class CoverageField extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "[CoverageFieldId]")
	private Integer coverageFieldId;

	@Column(name = "[CoverageField]")
	private String name;
	
	public Integer getCoverageFieldId() {
		return coverageFieldId;
	}

	public void setCoverageFieldId(Integer coverageFieldId) {
		this.coverageFieldId = coverageFieldId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}