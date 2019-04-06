package org.sense.sensor;

import java.util.Calendar;
import java.util.Random;

import org.apache.edgent.function.Supplier;
import org.apache.flink.api.java.tuple.Tuple4;
import org.sense.util.Cities;
import org.sense.util.Platform;
import org.sense.util.RandomCity;
import org.sense.util.SensorKey;
import org.sense.util.SensorType;

public class CounterSensor implements Supplier<Tuple4<SensorKey, Long, Integer, String>> {

	private static final long serialVersionUID = -900693619248120949L;
	private SensorKey key;
	private int currentValue = 0;
	private Random randomGenerator;
	private RandomCity<Cities> randomCity;

	public CounterSensor(SensorKey key) {
		this.key = key;
		this.randomGenerator = new Random();
		this.randomCity = new RandomCity<Cities>(Cities.class);
	}

	public CounterSensor(Integer id, SensorType sensorType, Platform platform) {
		this.key = new SensorKey(id, sensorType, platform);
		this.randomGenerator = new Random();
		this.randomCity = new RandomCity<Cities>(Cities.class);
	}

	/**
	 * This generates random numbers between 1 to 500 people or tickets, or between
	 * 1 to 3 trains.
	 */
	@Override
	public Tuple4<SensorKey, Long, Integer, String> get() {
		// Change the current value some random amount
		if (SensorType.COUNTER_PEOPLE.equals(key.getSensorType())
				|| SensorType.COUNTER_TICKETS.equals(key.getSensorType())) {
			currentValue = randomGenerator.nextInt(500) + 1;
		} else if (SensorType.COUNTER_TRAINS.equals(key.getSensorType())) {
			currentValue = randomGenerator.nextInt(3) + 1;
		} else {
			currentValue = 0;
		}
		String origin = randomCity.random().getValue();
		String dest = randomCity.random().getValue();
		return Tuple4.of(key, Calendar.getInstance().getTimeInMillis(), currentValue, origin + "-" + dest);
	}
}
