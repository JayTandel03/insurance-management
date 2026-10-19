package com.onerivet.model.enums;

import com.onerivet.util.ErrorMessageConstants;

public enum VehicleAgeSlabEnum {

	ZERO_TO_THREE("0 to less than 3 years", 0, 2),
	THREE_TO_FIVE("3 to less than 5 years", 3, 4),
	FIVE_TO_TEN("5 to less than 10 years", 5, 9),
	TEN_PLUS("10+ years", 10, Integer.MAX_VALUE);

	private final String value;
	private final Integer min;
	private final Integer max;

	VehicleAgeSlabEnum(String value, Integer min, Integer max) {
		this.value = value;
		this.min = min;
		this.max = max;
	}

	public static String fromAge(Integer vehicleAge) {

		for (VehicleAgeSlabEnum slab : values()) {
			if (vehicleAge >= slab.min && vehicleAge <= slab.max) {
				return slab.value;
			}
		}
		throw new IllegalArgumentException(ErrorMessageConstants.INVALID_VEHICLE_AGE);
	}
}