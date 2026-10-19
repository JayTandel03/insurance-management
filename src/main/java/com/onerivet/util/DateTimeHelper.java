package com.onerivet.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class DateTimeHelper {

    private DateTimeHelper() {
    	 throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    public static LocalDateTime getUtcDateTime() {

        return LocalDateTime.now(ZoneOffset.UTC);
    }
}