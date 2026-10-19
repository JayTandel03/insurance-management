package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class CoverageTypeTest {

    @Test
    void shouldSetAndGetFields() {

        CoverageType ct = new CoverageType();

        ct.setCoverageTypeId(10);
        ct.setName("Full Coverage");

        assertEquals(10, ct.getCoverageTypeId());
        assertEquals("Full Coverage", ct.getName());
    }

    @Test
    void shouldHandleNullValues() {

        CoverageType ct = new CoverageType();

        ct.setCoverageTypeId(null);
        ct.setName(null);

        assertNull(ct.getCoverageTypeId());
        assertNull(ct.getName());
    }

    @Test
    void shouldUpdateValues() {

        CoverageType ct = new CoverageType();

        ct.setName("Old");
        ct.setName("Updated");

        assertEquals("Updated", ct.getName());
    }

    @Test
    void shouldCreateDefaultObject() {

        CoverageType ct = new CoverageType();

        assertNotNull(ct);
        assertNull(ct.getCoverageTypeId());
        assertNull(ct.getName());
    }
    
}