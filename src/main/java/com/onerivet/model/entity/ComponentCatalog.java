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
@Table(name = "[ComponentCatalog]", schema = Constants.COVERAGE_SCHEMA)
public class ComponentCatalog extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "[ComponentCatalogId]")
	private Integer componentCatalogId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[CoverageTypeId]")
	private CoverageType coverageType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[ComponentId]")
	private Component component;

	public Integer getComponentCatalogId() {
		return componentCatalogId;
	}

	public void setComponentCatalogId(Integer componentCatalogId) {
		this.componentCatalogId = componentCatalogId;
	}

	public CoverageType getCoverageType() {
		return coverageType;
	}

	public void setCoverageType(CoverageType coverageType) {
		this.coverageType = coverageType;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

}