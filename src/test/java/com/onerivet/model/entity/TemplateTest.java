package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class TemplateTest {

    @Test
    void shouldSetAndGetAllFields() {

        Template template = new Template();

        VehicleType vehicleType = new VehicleType();
        vehicleType.setVehicleTypeId(1);

        Status status = new Status();
        status.setName("ACTIVE");

        LocalDateTime now = LocalDateTime.now();

        template.setTemplateId("T1");
        template.setName("Test Template");
        template.setVehicleType(vehicleType);
        template.setStatus(status);
        template.setDeletedDate(now);

        assertEquals("T1", template.getTemplateId());
        assertEquals("Test Template", template.getName());
        assertEquals(vehicleType, template.getVehicleType());
        assertEquals(status, template.getStatus());
        assertEquals(now, template.getDeletedDate());
    }

    @Test
    void shouldHandleNullValues() {

        Template template = new Template();

        template.setTemplateId(null);
        template.setName(null);
        template.setVehicleType(null);
        template.setStatus(null);
        template.setDeletedDate(null);

        assertNull(template.getTemplateId());
        assertNull(template.getName());
        assertNull(template.getVehicleType());
        assertNull(template.getStatus());
        assertNull(template.getDeletedDate());
    }

    @Test
    void shouldCreateDefaultObject() {

        Template template = new Template();

        assertNotNull(template);
        assertNull(template.getTemplateId());
        assertNull(template.getName());
        assertNull(template.getVehicleType());
        assertNull(template.getStatus());
        assertNull(template.getDeletedDate());
    }

    @Test
    void shouldUpdateValuesCorrectly() {

        Template template = new Template();

        template.setTemplateId("OLD");
        template.setName("Old Name");

        template.setTemplateId("NEW");
        template.setName("New Name");

        assertEquals("NEW", template.getTemplateId());
        assertEquals("New Name", template.getName());
    }

    @Test
    void shouldHandleDeletedDateCorrectly() {

        Template template = new Template();

        LocalDateTime date1 = LocalDateTime.now();
        LocalDateTime date2 = date1.plusDays(1);

        template.setDeletedDate(date1);
        assertEquals(date1, template.getDeletedDate());

        template.setDeletedDate(date2);
        assertEquals(date2, template.getDeletedDate());
    }
    
}