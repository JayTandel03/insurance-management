package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class GenderTest {

    @Test
    void shouldSetAndGetAllFields() {

        Gender gender = new Gender();

        LocalDateTime created = LocalDateTime.now();
        LocalDateTime deleted = created.plusDays(1);

        gender.setGenderId(1);
        gender.setName("Male");
        gender.setCreatedDate(created);
        gender.setDeletedDate(deleted);

        assertEquals(1, gender.getGenderId());
        assertEquals("Male", gender.getName());
        assertEquals(created, gender.getCreatedDate());
        assertEquals(deleted, gender.getDeletedDate());
    }

    @Test
    void shouldHandleNullValues() {

        Gender gender = new Gender();

        gender.setGenderId(null);
        gender.setName(null);
        gender.setCreatedDate(null);
        gender.setDeletedDate(null);

        assertNull(gender.getGenderId());
        assertNull(gender.getName());
        assertNull(gender.getCreatedDate());
        assertNull(gender.getDeletedDate());
    }

    @Test
    void shouldCreateDefaultObject() {

        Gender gender = new Gender();

        assertNotNull(gender);
        assertNull(gender.getGenderId());
        assertNull(gender.getName());
    }

    @Test
    void shouldUpdateValuesCorrectly() {

        Gender gender = new Gender();

        gender.setName("Male");
        gender.setName("Female");

        assertEquals("Female", gender.getName());
    }

    @Test
    void shouldHandleDateUpdatesCorrectly() {

        Gender gender = new Gender();

        LocalDateTime date1 = LocalDateTime.now();
        LocalDateTime date2 = date1.plusHours(5);

        gender.setCreatedDate(date1);
        gender.setDeletedDate(date1);

        gender.setCreatedDate(date2);
        gender.setDeletedDate(date2);

        assertEquals(date2, gender.getCreatedDate());
        assertEquals(date2, gender.getDeletedDate());
    }
    
}