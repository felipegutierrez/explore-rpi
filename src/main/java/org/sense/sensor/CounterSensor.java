package org.sense.sensor;

import java.util.Random;

import org.apache.edgent.function.Supplier;
import org.apache.flink.api.java.tuple.Tuple2;
import org.sense.util.Platform;
import org.sense.util.SensorKey;
import org.sense.util.SensorType;

public class CounterSensor implements Supplier<Tuple2<SensorKey, Integer>> {

	private static final long serialVersionUID = -900693619248120949L;
	private SensorKey key;
	private int currentValue = 0;
	private Random rand;

	public CounterSensor(SensorKey key) {
		this.key = key;
		this.rand = new Random();
	}

	public CounterSensor(Integer id, SensorType sensorType, Platform platform) {
		this.key = new SensorKey(id, sensorType, platform);
		this.rand = new Random();
	}

	@Override
	public Tuple2<SensorKey, Integer> get() {
		// Change the current temperature some random amount
		int newTemp = (int) rand.nextGaussian() + currentValue;
		currentValue = newTemp;

		// Enable this to test fake values
		// @formatter:off
		if (key.getId().equals(1) || key.getId().equals(2) || key.getId().equals(3)) {
			currentValue = 25;
		} else if (key.getId().equals(4) || key.getId().equals(5) || key.getId().equals(6)) {
			currentValue = 27;
		} else if (key.getId().equals(7) || key.getId().equals(8) || key.getId().equals(9)) {
			currentValue = 23;
		}
		// @formatter:on

		return Tuple2.of(key, currentValue);
	}
}
