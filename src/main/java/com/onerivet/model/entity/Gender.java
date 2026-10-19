package com.onerivet.model.entity;

import java.time.LocalDateTime;
import com.onerivet.util.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "[Gender]", schema = Constants.REF_SCHEMA)
public class Gender {
	
	@Id
	@Column(name = "[GenderId]")
	private Integer genderId;

	@Column(name = "[Gender]")
	private String name;

	@Column(name = "[CreatedDate]")
	private LocalDateTime createdDate;

	@Column(name = "[DeletedDate]")
	private LocalDateTime deletedDate;
	
	public Integer getGenderId() {
		return genderId;
	}

	public void setGenderId(Integer genderId) {
		this.genderId = genderId;
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