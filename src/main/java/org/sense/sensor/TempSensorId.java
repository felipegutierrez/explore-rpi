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
		return new Tuple2<Integer, Double>(id, currentTemp);
	}
}
