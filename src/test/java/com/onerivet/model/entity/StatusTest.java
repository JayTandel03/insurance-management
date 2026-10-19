package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class StatusTest {

    @Test
    void shouldSetAndGetAllFields() {

        Status status = new Status();

        LocalDateTime created = LocalDateTime.now();
        LocalDateTime deleted = created.plusDays(1);

        status.setStatusId(1);
        status.setName("ACTIVE");
        status.setCreatedDate(created);
        status.setDeletedDate(deleted);

        assertEquals(1, status.getStatusId());
        assertEquals("ACTIVE", status.getName());
        assertEquals(created, status.getCreatedDate());
        assertEquals(deleted, status.getDeletedDate());
    }

    @Test
    void shouldHandleNullValues() {

        Status status = new Status();

        status.setStatusId(null);
        status.setName(null);
        status.setCreatedDate(null);
        status.setDeletedDate(null);

        assertNull(status.getStatusId());
        assertNull(status.getName());
        assertNull(status.getCreatedDate());
        assertNull(status.getDeletedDate());
    }

    @Test
    void shouldCreateDefaultObject() {

        Status status = new Status();

        assertNotNull(status);
        assertNull(status.getStatusId());
        assertNull(status.getName());
    }

    @Test
    void shouldUpdateValuesCorrectly() {

        Status status = new Status();

        status.setName("DRAFT");
        status.setName("PUBLISHED");

        assertEquals("PUBLISHED", status.getName());
    }

    @Test
    void shouldHandleDateUpdatesCorrectly() {

        Status status = new Status();

        LocalDateTime date1 = LocalDateTime.now();
        LocalDateTime date2 = date1.plusHours(3);

        status.setCreatedDate(date1);
        status.setDeletedDate(date1);

        status.setCreatedDate(date2);
        status.setDeletedDate(date2);

        assertEquals(date2, status.getCreatedDate());
        assertEquals(date2, status.getDeletedDate());
    }
    
}