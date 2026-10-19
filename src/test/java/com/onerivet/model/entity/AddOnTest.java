package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class AddOnTest {

    @Test
    void shouldSetAndGetAllFields() {

        AddOn addOn = new AddOn();

        VehicleType vt = new VehicleType();
        vt.setVehicleTypeId(1);

        Plan plan = new Plan();
        plan.setPlanId(10);

        addOn.setAddOnId(100);
        addOn.setName("Zero Depreciation");

        assertEquals(100, addOn.getAddOnId());
        assertEquals("Zero Depreciation", addOn.getName());

    }

    @Test
    void shouldHandleNullValues() {

        AddOn addOn = new AddOn();

        addOn.setAddOnId(null);
        addOn.setName(null);

        assertNull(addOn.getAddOnId());
        assertNull(addOn.getName());

    }
    
    @Test
    void shouldSetAndGetStatus() {

        AddOn addOn = new AddOn();

        Status status = new Status();
        status.setStatusId(1);
        status.setName("ACTIVE");

        addOn.setStatus(status);

        assertEquals(status, addOn.getStatus());
    }
    
}