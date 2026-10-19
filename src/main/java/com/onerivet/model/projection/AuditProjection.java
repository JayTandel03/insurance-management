package com.onerivet.model.projection;

import java.time.LocalDateTime;

import com.onerivet.util.Constants;

public class AuditProjection {

	private final LocalDateTime createdDate;
	private final LocalDateTime modifiedDate;
	private final String name;

	public AuditProjection(LocalDateTime createdDate, LocalDateTime modifiedDate, String name) {
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.name = name != null ? name.trim() : Constants.BLANK;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public String getName() {
		return name;
	}
}
