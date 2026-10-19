package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class InputTypeTest {

    @Test
    void shouldSetAndGetAllFields() {

        InputType inputType = new InputType();

        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime deletedDate = LocalDateTime.now();

        inputType.setInputTypeId(1);
        inputType.setInputType("Text");
        inputType.setCreatedDate(createdDate);
        inputType.setDeletedDate(deletedDate);

        assertEquals(1, inputType.getInputTypeId());
        assertEquals("Text", inputType.getInputType());
        assertEquals(createdDate, inputType.getCreatedDate());
        assertEquals(deletedDate, inputType.getDeletedDate());
    }

    @Test
    void shouldHandleNullValues() {

        InputType inputType = new InputType();

        inputType.setInputTypeId(null);
        inputType.setInputType(null);
        inputType.setCreatedDate(null);
        inputType.setDeletedDate(null);

        assertNull(inputType.getInputTypeId());
        assertNull(inputType.getInputType());
        assertNull(inputType.getCreatedDate());
        assertNull(inputType.getDeletedDate());
    }
}
