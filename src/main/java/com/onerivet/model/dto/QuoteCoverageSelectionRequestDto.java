package com.onerivet.model.dto;

import java.util.List;
import com.onerivet.util.ErrorMessageConstants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class QuoteCoverageSelectionRequestDto {

	@NotNull(message = ErrorMessageConstants.COVERAGE_REQUIRED)
	@Positive(message = ErrorMessageConstants.INVALID_COVERAGE_ID)
	private Integer coverageId;

	@NotEmpty(message = "At least one coverage sub-type is required")
	private List<@Positive(message = ErrorMessageConstants.INVALID_COVERAGE_TYPE_ID) Integer> coverageTypeIds;

	private List<@Positive(message = ErrorMessageConstants.INVALID_ADD_ON_ID)Integer> addOnIds;

	public QuoteCoverageSelectionRequestDto() {

	}

	public QuoteCoverageSelectionRequestDto(Integer coverageId, List<Integer> coverageTypeIds, List<Integer> addOnIds) {
	
		this.coverageId = coverageId;
		this.coverageTypeIds = coverageTypeIds;
		this.addOnIds = addOnIds;
	}

	public Integer getCoverageId() {
		return coverageId;
	}

	public void setCoverageId(Integer coverageId) {
		this.coverageId = coverageId;
	}

	public List<Integer> getCoverageTypeIds() {
		return coverageTypeIds;
	}

	public void setCoverageTypeIds(List<Integer> coverageTypeIds) {
		this.coverageTypeIds = coverageTypeIds;
	}

	public List<Integer> getAddOnIds() {
		return addOnIds;
	}

	public void setAddOnIds(List<Integer> addOnIds) {
		this.addOnIds = addOnIds;
	}
}