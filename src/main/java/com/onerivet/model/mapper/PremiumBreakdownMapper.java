package com.onerivet.model.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.onerivet.model.dto.AddOnPremiumDto;
import com.onerivet.model.dto.CoveragePremiumDto;
import com.onerivet.model.projection.AddOnPremiumProjection;
import com.onerivet.model.projection.CoveragePremiumProjection;

@Component
public class PremiumBreakdownMapper {

	private final ModelMapper modelMapper; 

	public PremiumBreakdownMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public CoveragePremiumDto mapCoverageDto(CoveragePremiumProjection projection) {
		return modelMapper.map(projection, CoveragePremiumDto.class);
	}

	public AddOnPremiumDto mapAddOnDto(AddOnPremiumProjection projection) {
		return modelMapper.map(projection, AddOnPremiumDto.class);
	}
} 