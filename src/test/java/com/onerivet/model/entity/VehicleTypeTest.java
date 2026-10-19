package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class VehicleTypeTest {

    @Test
    void shouldSetAndGetAllFields() {

        VehicleType vt = new VehicleType();

        LocalDateTime created = LocalDateTime.now();
        LocalDateTime deleted = created.plusDays(1);

        vt.setVehicleTypeId(1);
        vt.setVehicle("Car");
        vt.setCreatedDate(created);
        vt.setDeletedDate(deleted);

        assertEquals(1, vt.getVehicleTypeId());
        assertEquals("Car", vt.getVehicle());
        assertEquals(created, vt.getCreatedDate());
        assertEquals(deleted, vt.getDeletedDate());
    }

    @Test
    void shouldHandleNullValues() {

        VehicleType vt = new VehicleType();

        vt.setVehicleTypeId(null);
        vt.setVehicle(null);
        vt.setCreatedDate(null);
        vt.setDeletedDate(null);

        assertNull(vt.getVehicleTypeId());
        assertNull(vt.getVehicle());
        assertNull(vt.getCreatedDate());
        assertNull(vt.getDeletedDate());
    }

    @Test
    void shouldCreateDefaultObject() {

        VehicleType vt = new VehicleType();

        assertNotNull(vt);
        assertNull(vt.getVehicleTypeId());
        assertNull(vt.getVehicle());
    }

    @Test
    void shouldUpdateValuesCorrectly() {

        VehicleType vt = new VehicleType();

        vt.setVehicle("Bike");
        vt.setVehicle("Car");

        assertEquals("Car", vt.getVehicle());
    }

    @Test
    void shouldHandleDateUpdatesCorrectly() {

        VehicleType vt = new VehicleType();

        LocalDateTime date1 = LocalDateTime.now();
        LocalDateTime date2 = date1.plusHours(2);

        vt.setCreatedDate(date1);
        vt.setDeletedDate(date1);

        vt.setCreatedDate(date2);
        vt.setDeletedDate(date2);

        assertEquals(date2, vt.getCreatedDate());
        assertEquals(date2, vt.getDeletedDate());
    }
    
}