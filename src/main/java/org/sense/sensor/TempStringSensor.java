package org.sense.sensor;

import java.util.Random;

import org.apache.edgent.function.Supplier;

public class TempStringSensor implements Supplier<String> {

	private static final long serialVersionUID = 8723195417630127710L;

	double currentTemp = 65.0;

	Random rand;

	public TempStringSensor() {
		rand = new Random();
	}

	@Override
	public String get() {
		// Change the current temperature some random amount
		double newTemp = rand.nextGaussian() + currentTemp;
		currentTemp = newTemp;

		return String.valueOf(currentTemp);
	}
}
