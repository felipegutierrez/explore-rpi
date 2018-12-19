package org.sense.edgent.app;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.edgent.function.Functions;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.TWindow;
import org.apache.edgent.topology.Topology;
import org.sense.sensor.TempSensor;

public class TempSensorWindowApp {

	public TempSensorWindowApp() {
		System.out.println("TempSensorWindow Hello World!");
		TempSensor sensor = new TempSensor();
		DirectProvider dp = new DirectProvider();
		Topology topology = dp.newTopology();

		TStream<Double> tempReadings = topology.poll(sensor, 100, TimeUnit.MILLISECONDS);

		TWindow<Double, Integer> window = tempReadings.last(5, TimeUnit.SECONDS, Functions.unpartitioned());

		TStream<Double> batchResults = window.batch((List<Double> list, Integer partition) -> {
			double avg = 0.0;
			System.out.print("value:");
			for (Double d : list) {
				System.out.print(String.format(" %s + ", d));
				avg += d;
			}
			if (list.size() > 0) {
				System.out.print(String.format("\nsize: %s - ", list.size()));
				avg /= list.size();
			}
			System.out.print("Temperature average: ");
			return avg;
		});

		batchResults.print();

		dp.submit(topology);
	}
}
