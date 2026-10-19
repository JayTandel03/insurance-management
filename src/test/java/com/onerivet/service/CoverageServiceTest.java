package com.onerivet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.onerivet.model.dto.CoverageComponentResponseDto;
import com.onerivet.model.dto.CoverageResponseDto;
import com.onerivet.model.dto.CoverageTypeResponseDto;
import com.onerivet.model.enums.TemplateStatus;
import com.onerivet.model.projection.CoverageComponentProjection;
import com.onerivet.model.projection.CoverageProjection;
import com.onerivet.repository.CoverageRepository;

@ExtendWith(MockitoExtension.class)
class CoverageServiceTest {

    @Mock
    private CoverageRepository coverageRepository;

    @InjectMocks
    private CoverageService coverageService;

    private CoverageProjection mockCoverageProjection;
    private CoverageComponentProjection mockComponentProjection;

    @BeforeEach
    void setup() {
        mockCoverageProjection = mock(CoverageProjection.class);
        mockComponentProjection = mock(CoverageComponentProjection.class);
    }

    @Test
    void shouldReturnPublishedCoverages() {
        when(mockCoverageProjection.getCoverageId()).thenReturn(1);
        when(mockCoverageProjection.getCoverage()).thenReturn("Comprehensive");
        when(mockCoverageProjection.getIsStandard()).thenReturn(true);
        when(mockCoverageProjection.getCoverageTypeId()).thenReturn(10);
        when(mockCoverageProjection.getCoverageType()).thenReturn("Own Damage");

        when(coverageRepository.findPublishedCoverages(TemplateStatus.PUBLISHED.name()))
                .thenReturn(List.of(mockCoverageProjection));
        when(coverageRepository.findPublishedCoverageComponents(TemplateStatus.PUBLISHED.name()))
                .thenReturn(Collections.emptyList());

        List<CoverageResponseDto> result = coverageService.getCoverage();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(coverageRepository).findPublishedCoverages(TemplateStatus.PUBLISHED.name());
        verify(coverageRepository).findPublishedCoverageComponents(TemplateStatus.PUBLISHED.name());
    }

    @Test
    void shouldReturnEmptyListWhenNoCoverageFound() {
        when(coverageRepository.findPublishedCoverages(TemplateStatus.PUBLISHED.name())).thenReturn(Collections.emptyList());
        when(coverageRepository.findPublishedCoverageComponents(TemplateStatus.PUBLISHED.name()))
                .thenReturn(Collections.emptyList());

        List<CoverageResponseDto> result = coverageService.getCoverage();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldMapComponentAndFieldSuccessfully() {
        when(mockCoverageProjection.getCoverageId()).thenReturn(1);
        when(mockCoverageProjection.getCoverageTypeId()).thenReturn(10);

        when(mockComponentProjection.getCoverageTypeId()).thenReturn(10);
        when(mockComponentProjection.getComponentId()).thenReturn(100);
        when(mockComponentProjection.getCoverageFieldId()).thenReturn(1000);

        when(coverageRepository.findPublishedCoverages(TemplateStatus.PUBLISHED.name()))
                .thenReturn(List.of(mockCoverageProjection));
        when(coverageRepository.findPublishedCoverageComponents(TemplateStatus.PUBLISHED.name()))
                .thenReturn(List.of(mockComponentProjection));

        List<CoverageResponseDto> result = coverageService.getCoverage();

        assertEquals(1, result.size());
        CoverageResponseDto coverageDto = result.get(0);

        assertEquals(1, coverageDto.getCoverageTypes().size());
        CoverageTypeResponseDto typeDto = coverageDto.getCoverageTypes().get(0);

        assertEquals(1, typeDto.getComponents().size());
        CoverageComponentResponseDto componentDto = typeDto.getComponents().get(0);

        assertEquals(1, componentDto.getFields().size());
    }

    @Test
    void shouldSkipFieldWhenCoverageFieldIdIsNull() {
        when(mockCoverageProjection.getCoverageId()).thenReturn(1);
        when(mockCoverageProjection.getCoverageTypeId()).thenReturn(10);

        when(mockComponentProjection.getCoverageTypeId()).thenReturn(10);
        when(mockComponentProjection.getComponentId()).thenReturn(100);
        when(mockComponentProjection.getCoverageFieldId()).thenReturn(null);

        when(coverageRepository.findPublishedCoverages(TemplateStatus.PUBLISHED.name()))
                .thenReturn(List.of(mockCoverageProjection));
        when(coverageRepository.findPublishedCoverageComponents(TemplateStatus.PUBLISHED.name()))
                .thenReturn(List.of(mockComponentProjection));

        List<CoverageResponseDto> result = coverageService.getCoverage();

        assertEquals(1, result.size());
        assertTrue(result.get(0).getCoverageTypes().get(0).getComponents().get(0).getFields().isEmpty());
    }

    @Test
    void shouldGroupMultipleCoverageTypesAndComponentsCorrectly() {
        CoverageProjection coverage1TypeA = mock(CoverageProjection.class);
        when(coverage1TypeA.getCoverageId()).thenReturn(1);
        when(coverage1TypeA.getCoverageTypeId()).thenReturn(10);

        CoverageProjection coverage1TypeB = mock(CoverageProjection.class);
        when(coverage1TypeB.getCoverageId()).thenReturn(1);
        when(coverage1TypeB.getCoverageTypeId()).thenReturn(20);

        CoverageComponentProjection compA = mock(CoverageComponentProjection.class);
        when(compA.getCoverageTypeId()).thenReturn(10);
        when(compA.getComponentId()).thenReturn(100);
        when(compA.getCoverageFieldId()).thenReturn(1000);

        CoverageComponentProjection compB = mock(CoverageComponentProjection.class);
        when(compB.getCoverageTypeId()).thenReturn(20);
        when(compB.getComponentId()).thenReturn(200);
        when(compB.getCoverageFieldId()).thenReturn(2000);

        when(coverageRepository.findPublishedCoverages(TemplateStatus.PUBLISHED.name()))
                .thenReturn(List.of(coverage1TypeA, coverage1TypeB));
        when(coverageRepository.findPublishedCoverageComponents(TemplateStatus.PUBLISHED.name()))
                .thenReturn(List.of(compA, compB));

        List<CoverageResponseDto> result = coverageService.getCoverage();

        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getCoverageTypes().size());
    }
 
    @Test
    void shouldSkipComponentWhenCoverageTypeIdDoesNotMatch() {
        when(mockCoverageProjection.getCoverageId()).thenReturn(1);
        when(mockCoverageProjection.getCoverageTypeId()).thenReturn(10);
        when(mockComponentProjection.getCoverageTypeId()).thenReturn(99);

        when(coverageRepository.findPublishedCoverages(TemplateStatus.PUBLISHED.name()))
                .thenReturn(List.of(mockCoverageProjection));
        when(coverageRepository.findPublishedCoverageComponents(TemplateStatus.PUBLISHED.name()))
                .thenReturn(List.of(mockComponentProjection));

        List<CoverageResponseDto> result = coverageService.getCoverage();

        assertEquals(1, result.size());
        assertTrue(result.get(0).getCoverageTypes().get(0).getComponents().isEmpty());
    }

    @Test
    void shouldPreventDuplicateFieldsBeingAddedToSameComponent() {
        when(mockCoverageProjection.getCoverageId()).thenReturn(1);
        when(mockCoverageProjection.getCoverageTypeId()).thenReturn(10);

        CoverageComponentProjection initialRecord = mock(CoverageComponentProjection.class);
        when(initialRecord.getCoverageTypeId()).thenReturn(10);
        when(initialRecord.getComponentId()).thenReturn(100);
        when(initialRecord.getCoverageFieldId()).thenReturn(1000);

        CoverageComponentProjection duplicateRecord = mock(CoverageComponentProjection.class);
        when(duplicateRecord.getCoverageTypeId()).thenReturn(10);
        when(duplicateRecord.getComponentId()).thenReturn(100);
        when(duplicateRecord.getCoverageFieldId()).thenReturn(1000);

        when(coverageRepository.findPublishedCoverages(TemplateStatus.PUBLISHED.name()))
                .thenReturn(List.of(mockCoverageProjection));
        when(coverageRepository.findPublishedCoverageComponents(TemplateStatus.PUBLISHED.name()))
                .thenReturn(List.of(initialRecord, duplicateRecord));

        List<CoverageResponseDto> result = coverageService.getCoverage();

        var components = result.get(0).getCoverageTypes().get(0).getComponents();
        assertEquals(1, components.get(0).getFields().size());
    }

}