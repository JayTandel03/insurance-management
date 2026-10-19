package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class CoverageTypeCatalogTest {

    @Test
    void shouldSetAndGetAllFields() {

        CoverageTypeCatalog obj = new CoverageTypeCatalog();

        Coverage coverage = new Coverage();
        coverage.setCoverageId(1);

        CoverageType type = new CoverageType();
        type.setCoverageTypeId(10);

        obj.setCoverageTypeCatalogId(200);
        obj.setCoverage(coverage);
        obj.setCoverageType(type);

        assertEquals(200, obj.getCoverageTypeCatalogId());
        assertEquals(coverage, obj.getCoverage());
        assertEquals(type, obj.getCoverageType());
    }

    @Test
    void shouldHandleNullValues() {

        CoverageTypeCatalog obj = new CoverageTypeCatalog();

        obj.setCoverageTypeCatalogId(null);
        obj.setCoverage(null);
        obj.setCoverageType(null);

        assertNull(obj.getCoverageTypeCatalogId());
        assertNull(obj.getCoverage());
        assertNull(obj.getCoverageType());
    }
    
}