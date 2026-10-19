package com.onerivet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.onerivet.exception.ResourceNotFoundException;
import com.onerivet.model.enums.TemplateStatus;
import com.onerivet.model.projection.AddOnProjection;
import com.onerivet.repository.AddOnRepository;
import com.onerivet.repository.CoverageRepository;
import com.onerivet.util.ErrorMessageConstants;

@ExtendWith(MockitoExtension.class)
class AddOnServiceTest {

	@Mock
	private AddOnRepository addOnRepository;

	@Mock
	private CoverageRepository coverageRepository;

	@InjectMocks
	private AddOnService addOnService;

	private AddOnProjection mockProjection;

	@BeforeEach
	void setUp() {

		mockProjection = new AddOnProjection() {
			@Override
			public Integer getAddOnId() {
				return 1;
			}

			@Override
			public String getName() {
				return "Zero Depreciation";
			}
		};
	}

	@Test
	void getAddOns_ValidCoverageId_ReturnsAddOnList() {
		Integer validCoverageId = 3;
		List<AddOnProjection> expectedAddOns = List.of(mockProjection);

		when(coverageRepository.existsById(validCoverageId)).thenReturn(true);
		when(addOnRepository.findAddOns(validCoverageId, TemplateStatus.PUBLISHED.name())).thenReturn(expectedAddOns);

		List<AddOnProjection> actualAddOns = addOnService.getAddOns(validCoverageId);

		assertNotNull(actualAddOns);
		assertEquals(1, actualAddOns.size());
		assertEquals("Zero Depreciation", actualAddOns.get(0).getName());

		verify(coverageRepository).existsById(validCoverageId);
		verify(addOnRepository).findAddOns(validCoverageId, TemplateStatus.PUBLISHED.name());
	}

	@Test
	void getAddOns_NoAddOnsFound_ReturnsEmptyList() {
		Integer validCoverageId = 4;

		when(coverageRepository.existsById(validCoverageId)).thenReturn(true);
		when(addOnRepository.findAddOns(validCoverageId, TemplateStatus.PUBLISHED.name()))
				.thenReturn(Collections.emptyList());

		List<AddOnProjection> actualAddOns = addOnService.getAddOns(validCoverageId);

		assertNotNull(actualAddOns);
		assertTrue(actualAddOns.isEmpty());
	}

	@Test
	void getAddOns_CoverageIdNull_ThrowsIllegalArgumentException() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			addOnService.getAddOns(null);
		});

		assertEquals(ErrorMessageConstants.INVALID_PARAMETER, exception.getMessage());
		verifyNoInteractions(coverageRepository, addOnRepository);
	}

	@Test
	void getAddOns_CoverageIdLessThanZero_ThrowsIllegalArgumentException() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			addOnService.getAddOns(-5);
		});

		assertEquals(ErrorMessageConstants.INVALID_PARAMETER, exception.getMessage());
		verifyNoInteractions(coverageRepository, addOnRepository);
	}

	@Test
	void getAddOns_CoverageIdIsZero_ThrowsIllegalArgumentException() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			addOnService.getAddOns(0);
		});

		assertEquals(ErrorMessageConstants.INVALID_PARAMETER, exception.getMessage());
		verifyNoInteractions(coverageRepository, addOnRepository);
	}

	@Test
	void getAddOns_CoverageDoesNotExist_ThrowsResourceNotFoundException() {
		Integer nonExistentCoverageId = 5;
		when(coverageRepository.existsById(nonExistentCoverageId)).thenReturn(false);

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			addOnService.getAddOns(nonExistentCoverageId);
		});

		assertEquals(ErrorMessageConstants.COVERAGE_NOT_FOUND, exception.getMessage());
		verifyNoInteractions(addOnRepository);
	}

	@Test
	void getAddOns_CoverageDoesNotSupportAddons_ThrowsResourceNotFoundException() {
		Integer unsupportedCoverageId = 2;
		when(coverageRepository.existsById(unsupportedCoverageId)).thenReturn(true);

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			addOnService.getAddOns(unsupportedCoverageId);
		});

		assertEquals(ErrorMessageConstants.ADDONS_NOT_SUPPORTED, exception.getMessage());
		verifyNoInteractions(addOnRepository);
	}

}
