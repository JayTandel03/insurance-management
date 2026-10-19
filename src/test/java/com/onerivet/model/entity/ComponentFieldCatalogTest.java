package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class ComponentFieldCatalogTest {

    @Test
    void shouldSetAndGetAllFields() {

        ComponentFieldCatalog obj = new ComponentFieldCatalog();

        Component component = new Component();
        component.setComponentId(1);

        CoverageField field = new CoverageField();
        field.setCoverageFieldId(1000);

        obj.setComponentFieldCatalogId(200);
        obj.setComponent(component);
        obj.setCoverageField(field);

        assertEquals(200, obj.getComponentFieldCatalogId());
        assertEquals(component, obj.getComponent());
        assertEquals(field, obj.getCoverageField());
    }

    @Test
    void shouldHandleNullValues() {

        ComponentFieldCatalog obj = new ComponentFieldCatalog();

        obj.setComponentFieldCatalogId(null);
        obj.setComponent(null);
        obj.setCoverageField(null);

        assertNull(obj.getComponentFieldCatalogId());
        assertNull(obj.getComponent());
        assertNull(obj.getCoverageField());
    }
    
}