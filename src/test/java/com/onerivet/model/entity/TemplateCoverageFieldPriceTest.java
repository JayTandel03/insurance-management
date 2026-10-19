package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class TemplateCoverageFieldPriceTest {

    @Test
    void shouldSetAndGetAllFields() {

        TemplateCoverageFieldPrice entity = new TemplateCoverageFieldPrice();

        Template template = new Template();
        CoverageField coverageField = new CoverageField();
        Plan plan = new Plan();

        LocalDateTime deletedDate = LocalDateTime.now();
        BigDecimal basePrice = BigDecimal.valueOf(1500);

        entity.setTemplateCoverageFieldPriceId("TCFP1");
        entity.setTemplate(template);
        entity.setCoverageField(coverageField);
        entity.setPlan(plan);
        entity.setBasePrice(basePrice);
        entity.setDeletedDate(deletedDate);

        assertEquals("TCFP1", entity.getTemplateCoverageFieldPriceId());
        assertEquals(template, entity.getTemplate());
        assertEquals(coverageField, entity.getCoverageField());
        assertEquals(plan, entity.getPlan());
        assertEquals(basePrice, entity.getBasePrice());
        assertEquals(deletedDate, entity.getDeletedDate());
    }
    
}