package com.onerivet.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.onerivet.util.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "[Plan]", schema = Constants.REF_SCHEMA)
public class Plan {

	@Id
	@Column(name = "[PlanId]")
	private Integer planId;

	@Column(name = "[Plan]")
	private String name;

	@Column(name = "[PlanFactor]")
	private BigDecimal planFactor;

	@Column(name = "[CreatedDate]")
	private LocalDateTime createdDate;

	@Column(name = "[DeletedDate]")
	private LocalDateTime deletedDate;

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPlanFactor() {
		return planFactor;
	}

	public void setPlanFactor(BigDecimal planFactor) {
		this.planFactor = planFactor;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(LocalDateTime deletedDate) {
		this.deletedDate = deletedDate;
	}
}