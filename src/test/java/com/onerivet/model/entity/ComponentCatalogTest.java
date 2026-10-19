package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class ComponentCatalogTest {

    @Test
    void shouldSetAndGetAllFields() {

        ComponentCatalog obj = new ComponentCatalog();

        CoverageType coverageType = new CoverageType();
        coverageType.setCoverageTypeId(1);

        Component component = new Component();
        component.setComponentId(10);

        obj.setComponentCatalogId(100);
        obj.setCoverageType(coverageType);
        obj.setComponent(component);

        assertEquals(100, obj.getComponentCatalogId());
        assertEquals(coverageType, obj.getCoverageType());
        assertEquals(component, obj.getComponent());
    }

    @Test
    void shouldHandleNullValues() {

        ComponentCatalog obj = new ComponentCatalog();

        obj.setComponentCatalogId(null);
        obj.setCoverageType(null);
        obj.setComponent(null);

        assertNull(obj.getComponentCatalogId());
        assertNull(obj.getCoverageType());
        assertNull(obj.getComponent());
    }
    
}