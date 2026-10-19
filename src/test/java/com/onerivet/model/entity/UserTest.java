package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void shouldSetAndGetAllFields() {

        User user = new User();

        Gender gender = new Gender();
        Role role = new Role();
        User modifiedBy = new User();

        LocalDate dob = LocalDate.of(2000, 1, 1);
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime modified = created.plusHours(1);
        LocalDateTime deleted = created.plusDays(1);

        user.setUserId("U1");
        user.setFirstName("Nain");
        user.setLastName("Prajapati");
        user.setDateOfBirth(dob);
        user.setGender(gender);
        user.setEmail("nain.prajapati@gmail.com");
        user.setPhoneNumber("9876543210");
        user.setAddress("India");
        user.setRole(role);
        user.setCreatedDate(created);
        user.setModifiedById(modifiedBy);
        user.setModifiedDate(modified);
        user.setDeletedDate(deleted);

        assertEquals("U1", user.getUserId());
        assertEquals("Nain", user.getFirstName());
        assertEquals("Prajapati", user.getLastName());
        assertEquals(dob, user.getDateOfBirth());
        assertEquals(gender, user.getGender());
        assertEquals("nain.prajapati@gmail.com", user.getEmail());
        assertEquals("9876543210", user.getPhoneNumber());
        assertEquals("India", user.getAddress());
        assertEquals(role, user.getRole());
        assertEquals(created, user.getCreatedDate());
        assertEquals(modifiedBy, user.getModifiedById());
        assertEquals(modified, user.getModifiedDate());
        assertEquals(deleted, user.getDeletedDate());
    }

    @Test
    void shouldHandleNullValues() {

        User user = new User();

        user.setUserId(null);
        user.setFirstName(null);
        user.setLastName(null);
        user.setDateOfBirth(null);
        user.setGender(null);
        user.setEmail(null);
        user.setPhoneNumber(null);
        user.setAddress(null);
        user.setRole(null);
        user.setCreatedDate(null);
        user.setModifiedById(null);
        user.setModifiedDate(null);
        user.setDeletedDate(null);

        assertNull(user.getUserId());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getDateOfBirth());
        assertNull(user.getGender());
        assertNull(user.getEmail());
        assertNull(user.getPhoneNumber());
        assertNull(user.getAddress());
        assertNull(user.getRole());
        assertNull(user.getCreatedDate());
        assertNull(user.getModifiedById());
        assertNull(user.getModifiedDate());
        assertNull(user.getDeletedDate());
    }

    @Test
    void shouldCreateDefaultObject() {

        User user = new User();

        assertNotNull(user);
        assertNull(user.getUserId());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
    }

    @Test
    void shouldUpdateValuesCorrectly() {

        User user = new User();

        user.setFirstName("Old");
        user.setFirstName("New");

        assertEquals("New", user.getFirstName());
    }

    @Test
    void shouldHandleSelfReferenceModifiedBy() {

        User user = new User();

        user.setModifiedById(user);

        assertEquals(user, user.getModifiedById());
    }
    
}