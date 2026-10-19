package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class TemplateAddOnPriceTest {

	@Test
	void shouldSetAndGetAllFields() {

		TemplateAddOnPrice entity = new TemplateAddOnPrice();

		Template template = new Template();
		AddOn addOn = new AddOn();
		Plan plan = new Plan();

		LocalDateTime deletedDate = LocalDateTime.now();
		BigDecimal basePrice = BigDecimal.valueOf(2500);

		entity.setTemplateAddOnPriceId("TAOP1");
		entity.setTemplate(template);
		entity.setAddOn(addOn);
		entity.setPlan(plan);
		entity.setBasePrice(basePrice);
		entity.setDeletedDate(deletedDate);

		assertEquals("TAOP1", entity.getTemplateAddOnPriceId());
		assertEquals(template, entity.getTemplate());
		assertEquals(addOn, entity.getAddOn());
		assertEquals(plan, entity.getPlan());
		assertEquals(basePrice, entity.getBasePrice());
		assertEquals(deletedDate, entity.getDeletedDate());
	}
	
}