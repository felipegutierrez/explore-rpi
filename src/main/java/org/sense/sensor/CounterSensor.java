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
	private Random randomGenerator;

	public CounterSensor(SensorKey key) {
		this.key = key;
		this.randomGenerator = new Random();
	}

	public CounterSensor(Integer id, SensorType sensorType, Platform platform) {
		this.key = new SensorKey(id, sensorType, platform);
		this.randomGenerator = new Random();
	}

	@Override
	public Tuple2<SensorKey, Integer> get() {
		// Change the current value some random amount
		if (SensorType.COUNTER_PEOPLE.equals(key.getSensorType())
				|| SensorType.COUNTER_TICKETS.equals(key.getSensorType())) {
			currentValue = randomGenerator.nextInt(200) + 1;
		} else if (SensorType.COUNTER_TRAINS.equals(key.getSensorType())) {
			currentValue = randomGenerator.nextInt(5) + 1;
		} else {
			currentValue = 0;
		}
		return Tuple2.of(key, currentValue);
	}
}
