package com.onerivet.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class BadRequestExceptionTest {

    @Test
    void shouldCreateException() {
        BadRequestException ex = new BadRequestException("error");
        assertEquals("error", ex.getMessage());
    }
    
}