package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class PlanTest {

    @Test
    void shouldSetAndGetAllFields() {

        Plan plan = new Plan();

        LocalDateTime created = LocalDateTime.now();
        LocalDateTime deleted = created.plusDays(1);

        plan.setPlanId(1);
        plan.setName("Basic Plan");
        plan.setCreatedDate(created);
        plan.setDeletedDate(deleted);

        assertEquals(1, plan.getPlanId());
        assertEquals("Basic Plan", plan.getName());
        assertEquals(created, plan.getCreatedDate());
        assertEquals(deleted, plan.getDeletedDate());
    }

    @Test
    void shouldHandleNullValues() {

        Plan plan = new Plan();

        plan.setPlanId(null);
        plan.setName(null);
        plan.setCreatedDate(null);
        plan.setDeletedDate(null);

        assertNull(plan.getPlanId());
        assertNull(plan.getName());
        assertNull(plan.getCreatedDate());
        assertNull(plan.getDeletedDate());
    }

    @Test
    void shouldCreateDefaultObject() {

        Plan plan = new Plan();

        assertNotNull(plan);
        assertNull(plan.getPlanId());
        assertNull(plan.getName());
    }

    @Test
    void shouldUpdateValuesCorrectly() {

        Plan plan = new Plan();

        plan.setName("Old Plan");
        plan.setName("Updated Plan");

        assertEquals("Updated Plan", plan.getName());
    }

    @Test
    void shouldHandleDateUpdatesCorrectly() {

        Plan plan = new Plan();

        LocalDateTime date1 = LocalDateTime.now();
        LocalDateTime date2 = date1.plusHours(4);

        plan.setCreatedDate(date1);
        plan.setDeletedDate(date1);

        plan.setCreatedDate(date2);
        plan.setDeletedDate(date2);

        assertEquals(date2, plan.getCreatedDate());
        assertEquals(date2, plan.getDeletedDate());
    }
    
}