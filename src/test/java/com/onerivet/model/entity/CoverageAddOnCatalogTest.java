package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class CoverageAddOnCatalogTest {

    @Test
    void shouldSetAndGetAllFields() {

        CoverageAddOnCatalog obj = new CoverageAddOnCatalog();

        Coverage coverage = new Coverage();
        coverage.setCoverageId(1);

        AddOn addOn = new AddOn();
        addOn.setAddOnId(10);

        obj.setCoverageAddonCatalogId(100);
        obj.setCoverage(coverage);
        obj.setAddOn(addOn);

        assertEquals(100, obj.getCoverageAddonCatalogId());
        assertEquals(coverage, obj.getCoverage());
        assertEquals(addOn, obj.getAddOn());
    }

    @Test
    void shouldHandleNullValues() {

        CoverageAddOnCatalog obj = new CoverageAddOnCatalog();

        obj.setCoverageAddonCatalogId(null);
        obj.setCoverage(null);
        obj.setAddOn(null);

        assertNull(obj.getCoverageAddonCatalogId());
        assertNull(obj.getCoverage());
        assertNull(obj.getAddOn());
    }
    
}