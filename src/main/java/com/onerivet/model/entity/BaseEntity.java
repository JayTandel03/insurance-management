package com.onerivet.model.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[CreatedById]")
	private User createdById;

	@CreationTimestamp
	@Column(name = "[CreatedDate]", nullable = false, updatable = false)
	private LocalDateTime createdDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[ModifiedById]")
	private User modifiedById;

	@UpdateTimestamp
	@Column(name = "[ModifiedDate]")
	private LocalDateTime modifiedDate;

	public User getCreatedById() {
		return createdById;
	}

	public void setCreatedById(User createdById) {
		this.createdById = createdById;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public User getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(User modifiedById) {
		this.modifiedById = modifiedById;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}