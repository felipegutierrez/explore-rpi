package org.sense.sensor;

import java.util.Random;

import org.apache.edgent.function.Supplier;
import org.apache.flink.api.java.tuple.Tuple2;

public class TempSensorId implements Supplier<Tuple2<Integer, Double>> {

	private static final long serialVersionUID = -5149721359058547357L;
	private Integer id;
	private double currentTemp = 65.0;
	private Random rand;

	public TempSensorId(Integer id) {
		this.id = id;
		this.rand = new Random();
	}

	@Override
	public Tuple2<Integer, Double> get() {
		// Change the current temperature some random amount
		double newTemp = rand.nextGaussian() + currentTemp;
		currentTemp = newTemp;

		// Enable this to test fake values
		// if (id.equals(1) || id.equals(2) || id.equals(3)) {
		// currentTemp = 40.0;
		// } else if (id.equals(4) || id.equals(5) || id.equals(6)) {
		// currentTemp = 50.0;
		// } else if (id.equals(7) || id.equals(8) || id.equals(9)) {
		// currentTemp = 60.0;
		// }

		return new Tuple2<Integer, Double>(id, currentTemp);
	}
}
