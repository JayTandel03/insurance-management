package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class QuestionTest {

	@Test
	void shouldSetAndGetAllFields() {

		Question question = new Question();

		VehicleType vehicleType = new VehicleType();
		vehicleType.setVehicleTypeId(1);

		InputType inputType = new InputType();
		inputType.setInputTypeId(1);

		Status status = new Status();
		status.setStatusId(1);

		LocalDateTime deletedDate = LocalDateTime.now();

		question.setQuestionId(1);
		question.setVehicleType(vehicleType);
		question.setQuestionType(2);
		question.setQuestion("Enter Name");
		question.setHelpText("Provide full name");
		question.setInputType(inputType);
		question.setStatus(status);
		question.setIsMandatory(true);
		question.setIsActive(true);
		question.setDeletedDate(deletedDate);

		assertEquals(1, question.getQuestionId());
		assertEquals(vehicleType, question.getVehicleType());
		assertEquals(2, question.getQuestionType());
		assertEquals("Enter Name", question.getQuestion());
		assertEquals("Provide full name", question.getHelpText());
		assertEquals(inputType, question.getInputType());
		assertEquals(status, question.getStatus());
		assertEquals(true, question.getIsMandatory());
		assertEquals(true, question.getIsActive());
		assertEquals(deletedDate, question.getDeletedDate());
	}

	@Test
	void shouldHandleNullValues() {

		Question question = new Question();

		question.setQuestionId(null);
		question.setVehicleType(null);
		question.setQuestionType(null);
		question.setQuestion(null);
		question.setHelpText(null);
		question.setInputType(null);
		question.setStatus(null);
		question.setIsMandatory(null);
		question.setIsActive(null);
		question.setDeletedDate(null);

		assertNull(question.getQuestionId());
		assertNull(question.getVehicleType());
		assertNull(question.getQuestionType());
		assertNull(question.getQuestion());
		assertNull(question.getHelpText());
		assertNull(question.getInputType());
		assertNull(question.getStatus());
		assertNull(question.getIsMandatory());
		assertNull(question.getIsActive());
		assertNull(question.getDeletedDate());
	}
}
