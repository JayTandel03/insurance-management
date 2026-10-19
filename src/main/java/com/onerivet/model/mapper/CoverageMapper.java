package com.onerivet.model.mapper;

import java.util.ArrayList;
import com.onerivet.model.dto.CoverageComponentResponseDto;
import com.onerivet.model.dto.CoverageFieldResponseDto;
import com.onerivet.model.dto.CoverageResponseDto;
import com.onerivet.model.dto.CoverageTypeResponseDto;
import com.onerivet.model.projection.CoverageComponentProjection;
import com.onerivet.model.projection.CoverageProjection;

public class CoverageMapper {

	private CoverageMapper() {
		throw new IllegalStateException("Mapper class");
	}

	public static CoverageResponseDto createCoverageResponseDto(CoverageProjection coverageProjection) {
		CoverageResponseDto coverageResponseDto = new CoverageResponseDto();
		coverageResponseDto.setCoverageId(coverageProjection.getCoverageId());
		coverageResponseDto.setCoverage(coverageProjection.getCoverage());
		coverageResponseDto.setIsStandard(coverageProjection.getIsStandard());
		coverageResponseDto.setCoverageTypes(new ArrayList<>());

		return coverageResponseDto;
	}

	public static CoverageTypeResponseDto createCoverageTypeResponseDto(CoverageProjection coverageProjection,CoverageResponseDto coverageResponseDto) {
		CoverageTypeResponseDto coverageTypeResponseDto = new CoverageTypeResponseDto();
		coverageTypeResponseDto.setCoverageTypeId(coverageProjection.getCoverageTypeId());
		coverageTypeResponseDto.setCoverageType(coverageProjection.getCoverageType());
		coverageTypeResponseDto.setComponents(new ArrayList<>());
		coverageResponseDto.getCoverageTypes().add(coverageTypeResponseDto);

		return coverageTypeResponseDto;
	}

	public static CoverageComponentResponseDto createComponentResponseDto(CoverageComponentProjection coverageComponentProjection, CoverageTypeResponseDto coverageTypeResponseDto) {
		CoverageComponentResponseDto componentResponseDto = new CoverageComponentResponseDto();
		componentResponseDto.setComponentId(coverageComponentProjection.getComponentId());
		componentResponseDto.setComponent(coverageComponentProjection.getComponent());
		componentResponseDto.setFields(new ArrayList<>());
		coverageTypeResponseDto.getComponents().add(componentResponseDto);

		return componentResponseDto;
	}

	public static CoverageFieldResponseDto createFieldResponseDto(CoverageComponentProjection coverageComponentProjection) {
		return new CoverageFieldResponseDto(coverageComponentProjection.getCoverageFieldId(),
				coverageComponentProjection.getCoverageField());
	}

}