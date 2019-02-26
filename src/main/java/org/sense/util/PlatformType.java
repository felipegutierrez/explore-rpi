package org.sense.util;

public enum PlatformType {
	INTERNATIONAL("INT"), REGIONAL("REG"), CITY("CIT"), UNDEFINED("UND");

	private String value;

	PlatformType(String value) {
		this.value = value;
	}

	public String getValue() {
		if (value == null)
			return "";
		return value;
	}
}
