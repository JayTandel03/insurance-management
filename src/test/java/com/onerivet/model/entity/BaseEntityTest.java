package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class BaseEntityTest {

    @Test
    void shouldSetAndGetAllFields() {

        BaseEntity base = new BaseEntity();

        User createdBy = new User();
        User modifiedBy = new User();

        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime modifiedDate = createdDate.plusHours(2);

        base.setCreatedById(createdBy);
        base.setCreatedDate(createdDate);
        base.setModifiedById(modifiedBy);
        base.setModifiedDate(modifiedDate);

        assertEquals(createdBy, base.getCreatedById());
        assertEquals(createdDate, base.getCreatedDate());
        assertEquals(modifiedBy, base.getModifiedById());
        assertEquals(modifiedDate, base.getModifiedDate());
    }

    @Test
    void shouldHandleNullValues() {

        BaseEntity base = new BaseEntity();

        base.setCreatedById(null);
        base.setCreatedDate(null);
        base.setModifiedById(null);
        base.setModifiedDate(null);

        assertNull(base.getCreatedById());
        assertNull(base.getCreatedDate());
        assertNull(base.getModifiedById());
        assertNull(base.getModifiedDate());
    }

    @Test
    void shouldCreateDefaultObject() {

        BaseEntity base = new BaseEntity();

        assertNotNull(base);
        assertNull(base.getCreatedById());
        assertNull(base.getCreatedDate());
        assertNull(base.getModifiedById());
        assertNull(base.getModifiedDate());
    }

    @Test
    void shouldUpdateValuesCorrectly() {

        BaseEntity base = new BaseEntity();

        User user1 = new User();
        User user2 = new User();

        base.setCreatedById(user1);
        base.setModifiedById(user1);

        base.setCreatedById(user2);
        base.setModifiedById(user2);

        assertEquals(user2, base.getCreatedById());
        assertEquals(user2, base.getModifiedById());
    }

    @Test
    void shouldHandleDateUpdatesCorrectly() {

        BaseEntity base = new BaseEntity();

        LocalDateTime date1 = LocalDateTime.now();
        LocalDateTime date2 = date1.plusDays(1);

        base.setCreatedDate(date1);
        base.setModifiedDate(date1);

        base.setCreatedDate(date2);
        base.setModifiedDate(date2);

        assertEquals(date2, base.getCreatedDate());
        assertEquals(date2, base.getModifiedDate());
    }
    
}