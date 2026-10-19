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
@Table(name = "[ComponentFieldCatalog]", schema = Constants.COVERAGE_SCHEMA)
public class ComponentFieldCatalog extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "[ComponentFieldCatalogId]")
	private Integer componentFieldCatalogId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[ComponentId]")
	private Component component;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[CoverageFieldId]")
	private CoverageField coverageField;

	public Integer getComponentFieldCatalogId() {
		return componentFieldCatalogId;
	}

	public void setComponentFieldCatalogId(Integer componentFieldCatalogId) {
		this.componentFieldCatalogId = componentFieldCatalogId;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public CoverageField getCoverageField() {
		return coverageField;
	}

	public void setCoverageField(CoverageField coverageField) {
		this.coverageField = coverageField;
	}

}