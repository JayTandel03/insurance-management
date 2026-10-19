package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class CoverageFieldTest {

    @Test
    void shouldSetAndGetAllFields() {

        CoverageField field = new CoverageField();

        field.setCoverageFieldId(100);
        field.setName("Damage Cover");

        assertEquals(100, field.getCoverageFieldId());
        assertEquals("Damage Cover", field.getName());

    }

    @Test
    void shouldHandleNullValues() {

        CoverageField field = new CoverageField();

        field.setCoverageFieldId(null);
        field.setName(null);

        assertNull(field.getCoverageFieldId());
        assertNull(field.getName());
        
    }

    @Test
    void shouldUpdateValues() {

        CoverageField field = new CoverageField();

        field.setName("Old");
        field.setName("New");

        assertEquals("New", field.getName());
    }

    @Test
    void shouldCreateDefaultObject() {

        CoverageField field = new CoverageField();

        assertNotNull(field);
        assertNull(field.getCoverageFieldId());
        assertNull(field.getName());
    }
    
}