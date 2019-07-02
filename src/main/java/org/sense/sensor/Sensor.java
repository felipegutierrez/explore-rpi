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

public class Sensor implements Supplier<Tuple4<SensorKey, Long, Integer, String>> {
	private static final long serialVersionUID = 4880399913069158528L;
	private SensorKey key;
	private int currentValue = 0;
	private Random randomGenerator;
	private RandomCity<Cities> randomCity;

	public Sensor(SensorKey key) {
		this.key = key;
		this.randomGenerator = new Random();
		this.randomCity = new RandomCity<Cities>(Cities.class);
	}

	public Sensor(Integer id, SensorType sensorType, Platform platform) {
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
		if (SensorType.TICKET.equals(key.getSensorType())) {
			currentValue = randomGenerator.nextInt(500) + 1;
		} else {
			currentValue = 0;
		}
		String origin = randomCity.random().getValue();
		return Tuple4.of(key, Calendar.getInstance().getTimeInMillis(), currentValue, origin);
	}
}
