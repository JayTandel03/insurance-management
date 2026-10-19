package com.onerivet.model.entity;

import java.time.LocalDateTime;
import com.onerivet.util.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "[Status]", schema = Constants.REF_SCHEMA)
public class Status {

	@Id
	@Column(name = "[StatusId]")
	private Integer statusId;

	@Column(name = "[Status]")
	private String name;

	@Column(name = "[CreatedDate]")
	private LocalDateTime createdDate;

	@Column(name = "[DeletedDate]")
	private LocalDateTime deletedDate;

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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