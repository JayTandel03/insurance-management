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
@Table(name = "[AddOn]", schema = Constants.COVERAGE_SCHEMA)
public class AddOn extends BaseEntity{	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "[AddOnId]")
	private Integer addOnId;

	@Column(name = "[AddOn]")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[StatusId]")
	private Status status;

	public Integer getAddOnId() {
		return addOnId;
	}

	public void setAddOnId(Integer addOnId) {
		this.addOnId = addOnId;
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
	
}