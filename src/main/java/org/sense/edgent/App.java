package org.sense.edgent;

import java.util.concurrent.TimeUnit;

import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import org.sense.sensor.TempSensor;
import org.sense.sensor.Ultrasonic;

public class App {
	public static void main(String[] args) throws Exception {

		Ultrasonic sonic = new Ultrasonic(0, // ECO 11
				1, // TRIG 22
				1000, // REJECTION_START ; long
				23529411 // REJECTION_TIME ; long
		);
		System.out.println("Start");
		while (true) {
			System.out.println("distance " + sonic.getDistance() + "mm");
			Thread.sleep(1000); // 1s
		}
		
		/**

		System.out.println("Hello World!");
		TempSensor sensor = new TempSensor();
		DirectProvider dp = new DirectProvider();
		Topology topology = dp.newTopology();

		TStream<Double> tempReadings = topology.poll(sensor, 1, TimeUnit.MILLISECONDS);
		TStream<Double> filteredReadings = tempReadings.filter(reading -> reading < 50 || reading > 80);
		filteredReadings.print();

		dp.submit(topology);
		 */
	}
}
