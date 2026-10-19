package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class VehicleTypeCoveragePriceTest {

    @Test
    void shouldSetAndGetAllFields() {

        VehicleTypeCoveragePrice obj = new VehicleTypeCoveragePrice();

        Coverage coverage = new Coverage();
        coverage.setCoverageId(100);

        Plan plan = new Plan();
        plan.setPlanId(10);

        obj.setVehicleTypeCoveragePriceId("1");
        obj.setCoverage(coverage);
        obj.setBasePrice(new BigDecimal("5000.00"));
        obj.setPlan(plan);

        assertEquals("1", obj.getVehicleTypeCoveragePriceId());
        assertEquals(coverage, obj.getCoverage());
        assertEquals(new BigDecimal("5000.00"), obj.getBasePrice());
        assertEquals(plan, obj.getPlan());
    }

    @Test
    void shouldHandleNullValues() {

        VehicleTypeCoveragePrice obj = new VehicleTypeCoveragePrice();

        obj.setVehicleTypeCoveragePriceId(null);
        obj.setCoverage(null);
        obj.setBasePrice(null);
        obj.setPlan(null);

        assertNull(obj.getVehicleTypeCoveragePriceId());
        assertNull(obj.getCoverage());
        assertNull(obj.getBasePrice());
        assertNull(obj.getPlan());
    }
    
    @Test
    void shouldSetAndGetTemplate() {

        VehicleTypeCoveragePrice vehicleTypeCoveragePrice = new VehicleTypeCoveragePrice();

        Template template = new Template();
        template.setTemplateId("T1");

        vehicleTypeCoveragePrice.setTemplate(template);
        assertEquals(template, vehicleTypeCoveragePrice.getTemplate());
    }

    @Test
    void shouldSetAndGetDeletedDate() {

        VehicleTypeCoveragePrice vehicleTypeCoveragePrice = new VehicleTypeCoveragePrice();
        LocalDateTime deletedDate = LocalDateTime.now();

        vehicleTypeCoveragePrice.setDeletedDate(deletedDate);
        assertEquals(deletedDate, vehicleTypeCoveragePrice.getDeletedDate());
    }

}