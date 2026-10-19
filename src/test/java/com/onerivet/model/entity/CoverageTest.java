package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class CoverageTest {

    @Test
    void shouldSetAndGetAllFields() {

        Coverage coverage = new Coverage();

        Status status = new Status();
        status.setName("ACTIVE");

        coverage.setCoverageId(1);
        coverage.setName("Collision");
        coverage.setStatus(status);
        coverage.setIsStandard(true);

        assertEquals(1, coverage.getCoverageId());
        assertEquals("Collision", coverage.getName());
        assertEquals(status, coverage.getStatus());
        assertTrue(coverage.getIsStandard());
    }

    @Test
    void shouldHandleNullValues() {

        Coverage coverage = new Coverage();

        coverage.setCoverageId(null);
        coverage.setName(null);
        coverage.setStatus(null);
        coverage.setIsStandard(null);

        assertNull(coverage.getCoverageId());
        assertNull(coverage.getName());
        assertNull(coverage.getStatus());
        assertNull(coverage.getIsStandard());
    }

    @Test
    void shouldCreateDefaultObject() {

        Coverage coverage = new Coverage();

        assertNotNull(coverage);
        assertNull(coverage.getCoverageId());
        assertNull(coverage.getName());
        assertNull(coverage.getStatus());
        assertNull(coverage.getIsStandard());
    }

    @Test
    void shouldUpdateValuesCorrectly() {

        Coverage coverage = new Coverage();

        coverage.setCoverageId(1);
        coverage.setName("Old");
        coverage.setIsStandard(false);

        coverage.setCoverageId(2);
        coverage.setName("New");
        coverage.setIsStandard(true);

        assertEquals(2, coverage.getCoverageId());
        assertEquals("New", coverage.getName());
        assertTrue(coverage.getIsStandard());
    }
    
}