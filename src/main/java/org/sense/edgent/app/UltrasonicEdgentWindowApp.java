package org.sense.edgent.app;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.edgent.function.Functions;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.TWindow;
import org.apache.edgent.topology.Topology;
import org.sense.sensor.UltrasonicStream;

public class UltrasonicEdgentWindowApp {

	public UltrasonicEdgentWindowApp() {

		System.out.println("HCSR04 Ultrasonic distance sensor - window average");

		UltrasonicStream sensor = new UltrasonicStream();

		DirectProvider dp = new DirectProvider();

		Topology topology = dp.newTopology();

		TStream<Double> readings = topology.poll(sensor, 1, TimeUnit.SECONDS);

		TWindow<Double, Integer> window = readings.last(10, TimeUnit.SECONDS, Functions.unpartitioned());

		TStream<Double> batchResults = window.batch((List<Double> list, Integer partition) -> {
			double avg = 0.0;
			System.out.print("value:");
			for (Double d : list) {
				System.out.print(String.format(" %s + ", d));
				avg += d;
			}
			if (list.size() > 0) {
				System.out.print(String.format(" - size: %s. ", list.size()));
				avg /= list.size();
			}
			System.out.print("Average in centimeters: ");
			return avg;
		});

		batchResults.print();

		dp.submit(topology);
	}
}
