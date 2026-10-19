package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class TemplatePlanTest {

    @Test
    void shouldSetAndGetAllFields() {

        TemplatePlan tp = new TemplatePlan();

        Template template = new Template();
        template.setTemplateId("T1");

        Plan plan = new Plan();
        plan.setPlanId(1);

        LocalDateTime now = LocalDateTime.now();

        tp.setTemplatePlanId("TP1");
        tp.setTemplate(template);
        tp.setPlan(plan);
        tp.setDeletedDate(now);

        assertEquals("TP1", tp.getTemplatePlanId());
        assertEquals(template, tp.getTemplate());
        assertEquals(plan, tp.getPlan());
        assertEquals(now, tp.getDeletedDate());
    }

    @Test
    void shouldHandleNullValues() {

        TemplatePlan tp = new TemplatePlan();

        tp.setTemplatePlanId(null);
        tp.setTemplate(null);
        tp.setPlan(null);
        tp.setDeletedDate(null);

        assertNull(tp.getTemplatePlanId());
        assertNull(tp.getTemplate());
        assertNull(tp.getPlan());
        assertNull(tp.getDeletedDate());
    }
    
}