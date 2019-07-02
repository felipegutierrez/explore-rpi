package org.sense.util;

public enum SensorType {
	TEMPERATURE("TEMP"), LIFT_VIBRATION("LIFT_V"), COUNTER_PEOPLE("COUNT_PE"), COUNTER_TRAINS("COUNT_TR"),
	COUNTER_TICKETS("COUNT_TI"), TICKET("TICKET");

	private String value;

	SensorType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
