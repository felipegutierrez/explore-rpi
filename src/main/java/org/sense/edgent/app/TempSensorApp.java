package org.sense.edgent.app;

import java.util.concurrent.TimeUnit;

import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import org.sense.sensor.TempSensor;

public class TempSensorApp {

	public TempSensorApp() {
		System.out.println("TempSensor Hello World!");
		TempSensor sensor = new TempSensor();
		DirectProvider dp = new DirectProvider();
		Topology topology = dp.newTopology();

		TStream<Double> tempReadings = topology.poll(sensor, 1, TimeUnit.MILLISECONDS);
		TStream<Double> filteredReadings = tempReadings.filter(reading -> reading < 50 || reading > 80);
		filteredReadings.print();

		dp.submit(topology);
	}
}
