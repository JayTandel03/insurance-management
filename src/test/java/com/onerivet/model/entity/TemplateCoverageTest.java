package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class TemplateCoverageTest {

    @Test
    void shouldSetAndGetAllFields() {

        TemplateCoverage tc = new TemplateCoverage();

        TemplatePlan tp = new TemplatePlan();
        tp.setTemplatePlanId("TP1");

        CoverageTypeCatalog typeCatalog = new CoverageTypeCatalog();
        CoverageAddOnCatalog addOnCatalog = new CoverageAddOnCatalog();

        LocalDateTime now = LocalDateTime.now();

        tc.setTemplateCoverageId("TC1");
        tc.setTemplatePlan(tp);
        tc.setCoverageTypeCatalog(typeCatalog);
        tc.setCoverageAddOnCatalog(addOnCatalog);
        tc.setDeletedDate(now);

        assertEquals("TC1", tc.getTemplateCoverageId());
        assertEquals(tp, tc.getTemplatePlan());
        assertEquals(typeCatalog, tc.getCoverageTypeCatalog());
        assertEquals(addOnCatalog, tc.getCoverageAddOnCatalog());
        assertEquals(now, tc.getDeletedDate());
    }

    @Test
    void shouldHandleNullValues() {

        TemplateCoverage tc = new TemplateCoverage();

        tc.setTemplateCoverageId(null);
        tc.setTemplatePlan(null);
        tc.setCoverageTypeCatalog(null);
        tc.setCoverageAddOnCatalog(null);
        tc.setDeletedDate(null);

        assertNull(tc.getTemplateCoverageId());
        assertNull(tc.getTemplatePlan());
        assertNull(tc.getCoverageTypeCatalog());
        assertNull(tc.getCoverageAddOnCatalog());
        assertNull(tc.getDeletedDate());
    }
    
}