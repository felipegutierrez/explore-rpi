package org.sense.sensor;

import java.util.Random;

import org.apache.edgent.function.Supplier;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.types.Either;
import org.apache.flink.types.Either.Left;

public class TempEitherSensor implements Supplier<Either<String, Tuple2<Double, Double>>> {

	private static final long serialVersionUID = 8723195417630127710L;

	double currentTemp = 65.0;

	Random rand;

	public TempEitherSensor() {
		rand = new Random();
	}

	@Override
	public Either<String, Tuple2<Double, Double>> get() {
		// Change the current temperature some random amount
		double newTemp = rand.nextGaussian() + currentTemp;
		currentTemp = newTemp;

		return new Left<String, Tuple2<Double, Double>>(String.valueOf(currentTemp));
	}
}
