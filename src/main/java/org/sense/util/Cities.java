package org.sense.util;

public enum Cities {
	PARIS("Paris"), ROME("Rome"), LONDON("London"), BERLIN("Berlin"), BARCELONA("Barcelona"), AMSTERDAM("Amsterdam"),
	StPETERSBURG("St. Petersburg"), ISTANBUL("Istanbul"), ATHENS("Athens"), COPENHAGEN("Copenhagen"), MADRID("Madrid"),
	BRUSSELS("Brussels"), BUDAPEST("Budapest"), MUNICH("Munich"), EDINBURGH("Edinburgh"), PRAGUE("Prague");

	private String value;

	Cities(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
