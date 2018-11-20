package org.sense.sensor;

import org.apache.edgent.function.Supplier;

public class UltrasonicEdgent implements Supplier<Double> {

	@Override
	public Double get() {
		double newTemp = 3.14;
		return newTemp;
	}
}
