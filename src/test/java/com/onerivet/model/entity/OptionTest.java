package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class OptionTest {

    @Test
    void shouldSetAndGetAllFields() {

        Option option = new Option();

        option.setOptionId(1);
        option.setOptionLabel("Yes");

        assertEquals(1, option.getOptionId());
        assertEquals("Yes", option.getOptionLabel());
    }

    @Test
    void shouldHandleNullValues() {

        Option option = new Option();

        option.setOptionId(null);
        option.setOptionLabel(null);

        assertNull(option.getOptionId());
        assertNull(option.getOptionLabel());
    }
}

