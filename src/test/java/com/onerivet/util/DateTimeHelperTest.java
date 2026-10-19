package com.onerivet.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;

class DateTimeHelperTest {

	@Test
	void shouldReturnUtcDateTime() {

		LocalDateTime before = LocalDateTime.now(ZoneOffset.UTC);
		LocalDateTime result = DateTimeHelper.getUtcDateTime();
		LocalDateTime after = LocalDateTime.now(ZoneOffset.UTC);

		assertNotNull(result);

		boolean valid = (result.isEqual(before) || result.isAfter(before))
				&& (result.isEqual(after) || result.isBefore(after));
		assertEquals(true, valid);
	}

	@Test
	void shouldThrowExceptionForPrivateConstructor() throws Exception {

		Constructor<DateTimeHelper> constructor = DateTimeHelper.class.getDeclaredConstructor();
		constructor.setAccessible(true);

		InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);

		assertEquals(IllegalStateException.class, exception.getCause().getClass());
		assertEquals(Constants.UTILITY_CLASS, exception.getCause().getMessage());
	}

}