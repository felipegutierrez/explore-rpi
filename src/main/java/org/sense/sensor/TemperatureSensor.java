package org.sense.sensor;

import java.util.Random;

import org.apache.edgent.function.Supplier;
import org.apache.flink.api.java.tuple.Tuple2;
import org.sense.util.Platform;
import org.sense.util.SensorKey;
import org.sense.util.SensorType;

public class TemperatureSensor implements Supplier<Tuple2<SensorKey, Double>> {

	private static final long serialVersionUID = 6417287643985289776L;
	private SensorKey key;
	private double currentTemp = 65.0;
	private Random rand;

	public TemperatureSensor(SensorKey key) {
		this.key = key;
		this.rand = new Random();
	}

	public TemperatureSensor(Integer id, Platform platform) {
		this.key = new SensorKey(id, SensorType.TEMPERATURE, platform);
		this.rand = new Random();
	}

	@Override
	public Tuple2<SensorKey, Double> get() {
		// Change the current temperature some random amount
		double newTemp = rand.nextGaussian() + currentTemp;
		currentTemp = newTemp;

		// Enable this to test fake values
		// @formatter:off
		if (key.getId().equals(1) || key.getId().equals(2) || key.getId().equals(3)) {
			currentTemp = 25.0;
		} else if (key.getId().equals(4) || key.getId().equals(5) || key.getId().equals(6)) {
			currentTemp = 27.0;
		} else if (key.getId().equals(7) || key.getId().equals(8) || key.getId().equals(9)) {
			currentTemp = 23.0;
		}
		// @formatter:on

		return new Tuple2<SensorKey, Double>(key, currentTemp);
	}
}
