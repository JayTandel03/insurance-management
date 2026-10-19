package com.onerivet.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.onerivet.model.dto.CoverageComponentResponseDto;
import com.onerivet.model.dto.CoverageResponseDto;
import com.onerivet.model.dto.CoverageTypeResponseDto;
import com.onerivet.model.enums.TemplateStatus;
import com.onerivet.model.mapper.CoverageMapper;
import com.onerivet.model.projection.CoverageComponentProjection;
import com.onerivet.model.projection.CoverageProjection;
import com.onerivet.repository.CoverageRepository;

@Service
public class CoverageService {

	private static final Logger log = LoggerFactory.getLogger(CoverageService.class);

	private final CoverageRepository coverageRepository;

	public CoverageService(CoverageRepository coverageRepository) {
		this.coverageRepository = coverageRepository;
	}

	public List<CoverageResponseDto> getCoverage() {

		log.info("Fetching published coverages");

		List<CoverageProjection> coverageProjectionList = coverageRepository
				.findPublishedCoverages(TemplateStatus.PUBLISHED.name());

		List<CoverageComponentProjection> coverageComponentProjectionList = coverageRepository
				.findPublishedCoverageComponents(TemplateStatus.PUBLISHED.name());

		log.info("Total coverages fetched: {}", coverageProjectionList.size());

		return mapToCoverageResponseDtoList(coverageProjectionList, coverageComponentProjectionList);
	}

	private List<CoverageResponseDto> mapToCoverageResponseDtoList(List<CoverageProjection> coverageProjectionList,
			List<CoverageComponentProjection> coverageComponentProjectionList) {
		Map<Integer, CoverageResponseDto> coverageResponseDtoMap = new LinkedHashMap<>();

		for (CoverageProjection coverageProjection : coverageProjectionList) {
			CoverageResponseDto coverageDto = coverageResponseDtoMap.computeIfAbsent(coverageProjection.getCoverageId(),
					id -> CoverageMapper.createCoverageResponseDto(coverageProjection));
			CoverageTypeResponseDto coverageTypeDto = coverageDto.getCoverageTypes().stream()
					.filter(coverageType -> coverageType.getCoverageTypeId()
							.equals(coverageProjection.getCoverageTypeId()))
					.findFirst()
					.orElseGet(() -> CoverageMapper.createCoverageTypeResponseDto(coverageProjection, coverageDto));
			addComponents(coverageTypeDto, coverageProjection.getCoverageTypeId(), coverageComponentProjectionList);
		}

		log.info("Final mapped response size: {}", coverageResponseDtoMap.size());
		return new ArrayList<>(coverageResponseDtoMap.values());
	}

	private void addComponents(CoverageTypeResponseDto coverageTypeDto, Integer coverageTypeId,
			List<CoverageComponentProjection> coverageComponentProjectionList) {
		for (CoverageComponentProjection componentProjection : coverageComponentProjectionList) {
			if (!coverageTypeId.equals(componentProjection.getCoverageTypeId())) {
				continue;
			}
			CoverageComponentResponseDto componentDto = coverageTypeDto.getComponents().stream()
					.filter(component -> component.getComponentId().equals(componentProjection.getComponentId()))
					.findFirst()
					.orElseGet(() -> CoverageMapper.createComponentResponseDto(componentProjection, coverageTypeDto));
			addField(componentDto, componentProjection);
		}
	}

	private void addField(CoverageComponentResponseDto componentDto, CoverageComponentProjection componentProjection) {
		if (componentProjection.getCoverageFieldId() == null) {
			return; 
		}
		boolean fieldExists = componentDto.getFields().stream()
				.anyMatch(field -> field.getFieldId().equals(componentProjection.getCoverageFieldId()));

		if (!fieldExists) {
			componentDto.getFields().add(CoverageMapper.createFieldResponseDto(componentProjection));
		}
	}
}