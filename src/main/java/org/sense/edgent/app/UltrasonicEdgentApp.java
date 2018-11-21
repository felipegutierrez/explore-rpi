package org.sense.edgent.app;

import java.util.concurrent.TimeUnit;

import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import org.sense.sensor.UltrasonicStream;

public class UltrasonicEdgentApp {

	public UltrasonicEdgentApp() {

		System.out.println("HCSR04 Ultrasonic distance sensor");

		UltrasonicStream sensor = new UltrasonicStream();

		DirectProvider dp = new DirectProvider();

		Topology topology = dp.newTopology();

		TStream<Double> tempReadings = topology.poll(sensor, 3, TimeUnit.SECONDS);

		TStream<Double> filteredReadings = tempReadings.filter(reading -> reading < 50 || reading > 80);

		System.out.println("filter added: tempReadings.filter(reading -> reading < 50 || reading > 80);");

		filteredReadings.print();

		dp.submit(topology);
	}
}
