package org.sense.edgent.app;

import java.util.concurrent.TimeUnit;

import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import org.sense.sensor.UltrasonicStream;

public class UltrasonicEdgentApp {

	public UltrasonicEdgentApp() {

		System.out.println("HCSR04 Ultrasonic distance sensor - filter");

		UltrasonicStream sensor = new UltrasonicStream();

		DirectProvider dp = new DirectProvider();

		Topology topology = dp.newTopology();

		TStream<Double> tempReadings = topology.poll(sensor, 3, TimeUnit.SECONDS);

		TStream<Double> filteredReadings = tempReadings.filter(reading -> {
			boolean threshold = reading < 20 || reading > 80;
			if (!threshold) {
				System.out.println(String.format("Threshold reached: %s cm", reading));
			}
			return threshold;
		});
		filteredReadings.print();

		dp.submit(topology);
	}
}
