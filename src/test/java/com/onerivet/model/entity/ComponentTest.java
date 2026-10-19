package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class ComponentTest {

    @Test
    void shouldSetAndGetFields() {

        Component comp = new Component();

        comp.setComponentId(1);
        comp.setName("Engine");

        assertEquals(1, comp.getComponentId());
        assertEquals("Engine", comp.getName());
    }

    @Test
    void shouldHandleNullValues() {

        Component comp = new Component();

        comp.setComponentId(null);
        comp.setName(null);

        assertNull(comp.getComponentId());
        assertNull(comp.getName());
    }

    @Test
    void shouldUpdateValues() {

        Component comp = new Component();

        comp.setName("Old");
        comp.setName("New");

        assertEquals("New", comp.getName());
    }

    @Test
    void shouldCreateDefaultObject() {

        Component comp = new Component();

        assertNotNull(comp);
        assertNull(comp.getComponentId());
        assertNull(comp.getName());
    }
    
}