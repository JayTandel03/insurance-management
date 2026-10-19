package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class RoleTest {

    @Test
    void shouldSetAndGetAllFields() {

        Role role = new Role();

        LocalDateTime created = LocalDateTime.now();
        LocalDateTime deleted = created.plusDays(1);

        role.setRoleId("ADMIN");
        role.setName("Administrator");
        role.setCreatedDate(created);
        role.setDeletedDate(deleted);

        assertEquals("ADMIN", role.getRoleId());
        assertEquals("Administrator", role.getName());
        assertEquals(created, role.getCreatedDate());
        assertEquals(deleted, role.getDeletedDate());
    }

    @Test
    void shouldHandleNullValues() {

        Role role = new Role();

        role.setRoleId(null);
        role.setName(null);
        role.setCreatedDate(null);
        role.setDeletedDate(null);

        assertNull(role.getRoleId());
        assertNull(role.getName());
        assertNull(role.getCreatedDate());
        assertNull(role.getDeletedDate());
    }

    @Test
    void shouldCreateDefaultObject() {

        Role role = new Role();

        assertNotNull(role);
        assertNull(role.getRoleId());
        assertNull(role.getName());
    }

    @Test
    void shouldUpdateValuesCorrectly() {

        Role role = new Role();

        role.setName("User");
        role.setName("Admin");

        assertEquals("Admin", role.getName());
    }

    @Test
    void shouldHandleDateUpdatesCorrectly() {

        Role role = new Role();

        LocalDateTime date1 = LocalDateTime.now();
        LocalDateTime date2 = date1.plusHours(10);

        role.setCreatedDate(date1);
        role.setDeletedDate(date1);

        role.setCreatedDate(date2);
        role.setDeletedDate(date2);

        assertEquals(date2, role.getCreatedDate());
        assertEquals(date2, role.getDeletedDate());
    }
    
}