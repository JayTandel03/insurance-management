package com.onerivet.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import com.onerivet.model.dto.CoverageComponentResponseDto;
import com.onerivet.model.dto.CoverageFieldResponseDto;
import com.onerivet.model.dto.CoverageResponseDto;
import com.onerivet.model.dto.CoverageTypeResponseDto;
import com.onerivet.model.projection.CoverageComponentProjection;
import com.onerivet.model.projection.CoverageProjection;

class CoverageMapperTest {

	@Test
	void shouldCreateCoverageResponseDto() {

		CoverageProjection projection = mock(CoverageProjection.class);

		when(projection.getCoverageId()).thenReturn(1);
		when(projection.getCoverage()).thenReturn("Collision Coverage");
		when(projection.getIsStandard()).thenReturn(true);

		CoverageResponseDto responseDto = CoverageMapper.createCoverageResponseDto(projection);

		assertEquals(1, responseDto.getCoverageId());
		assertEquals("Collision Coverage", responseDto.getCoverage());
		assertEquals(true, responseDto.getIsStandard());
		assertTrue(responseDto.getCoverageTypes().isEmpty());
	}

	@Test
	void shouldCreateCoverageTypeResponseDto() {

		CoverageProjection projection = mock(CoverageProjection.class);

		when(projection.getCoverageTypeId()).thenReturn(10);
		when(projection.getCoverageType()).thenReturn("Comprehensive");

		CoverageResponseDto coverageResponseDto = new CoverageResponseDto();
		coverageResponseDto.setCoverageTypes(new ArrayList<>());

		CoverageTypeResponseDto responseDto = CoverageMapper.createCoverageTypeResponseDto(projection,
				coverageResponseDto);

		assertEquals(10, responseDto.getCoverageTypeId());
		assertEquals("Comprehensive", responseDto.getCoverageType());
		assertTrue(responseDto.getComponents().isEmpty());
		assertEquals(1, coverageResponseDto.getCoverageTypes().size());
	}

	@Test
	void shouldCreateComponentResponseDto() {

		CoverageComponentProjection projection = mock(CoverageComponentProjection.class);

		when(projection.getComponentId()).thenReturn(100);
		when(projection.getComponent()).thenReturn("Engine");

		CoverageTypeResponseDto coverageTypeResponseDto = new CoverageTypeResponseDto();
		coverageTypeResponseDto.setComponents(new ArrayList<>());

		CoverageComponentResponseDto responseDto = CoverageMapper.createComponentResponseDto(projection,
				coverageTypeResponseDto);

		assertEquals(100, responseDto.getComponentId());
		assertEquals("Engine", responseDto.getComponent());
		assertTrue(responseDto.getFields().isEmpty());
		assertEquals(1, coverageTypeResponseDto.getComponents().size());
	}

	@Test
	void shouldCreateFieldResponseDto() {

		CoverageComponentProjection projection = mock(CoverageComponentProjection.class);

		when(projection.getCoverageFieldId()).thenReturn(1000);
		when(projection.getCoverageField()).thenReturn("VIN Number");

		CoverageFieldResponseDto responseDto = CoverageMapper.createFieldResponseDto(projection);

		assertEquals(1000, responseDto.getFieldId());
		assertEquals("VIN Number", responseDto.getField());
	}

	@Test
	void shouldThrowExceptionForPrivateConstructor() throws Exception {

		Constructor<CoverageMapper> constructor = CoverageMapper.class.getDeclaredConstructor();

		constructor.setAccessible(true);

		InvocationTargetException exception = org.junit.jupiter.api.Assertions
				.assertThrows(InvocationTargetException.class, constructor::newInstance);

		assertTrue(exception.getCause() instanceof IllegalStateException);
		assertEquals("Mapper class", exception.getCause().getMessage());
	}

}