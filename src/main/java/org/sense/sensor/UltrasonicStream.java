package org.sense.sensor;

import org.apache.edgent.function.Supplier;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;

public class UltrasonicStream implements Supplier<Double> {

	private static final long serialVersionUID = -6511218542753341056L;

	private static GpioPinDigitalOutput sensorTriggerPin;
	private static GpioPinDigitalInput sensorEchoPin;
	private static final GpioController gpio = GpioFactory.getInstance();

	double currentDistance = -1.0;

	public UltrasonicStream() {
		// Trigger pin as OUTPUT
		sensorTriggerPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04);
		// Echo pin as INPUT
		sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_DOWN);
	}

	@Override
	public Double get() {
		try {
			System.out.print("Distance in centimeters: ");
			currentDistance = getDistance();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentDistance;
	}

	public double getDistance() throws InterruptedException {

		double distanceCM = -1;
		try {
			// Thread.sleep(2000);
			sensorTriggerPin.high(); // Make trigger pin HIGH
			Thread.sleep((long) 0.01);// Delay for 10 microseconds
			sensorTriggerPin.low(); // Make trigger pin LOW

			// Wait until the ECHO pin gets HIGH
			while (sensorEchoPin.isLow()) {

			}
			// Store the current time to calculate ECHO pin HIGH time.
			long startTime = System.nanoTime();
			// Wait until the ECHO pin gets LOW
			while (sensorEchoPin.isHigh()) {

			}
			// Store the echo pin HIGH end time to calculate ECHO pin HIGH time.
			long endTime = System.nanoTime();

			distanceCM = ((((endTime - startTime) / 1e3) / 2) / 29.1);
			// Printing out the distance in centimeters
			// System.out.println("Distance: " + distanceCM + " centimeters");

			return distanceCM;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return distanceCM;
	}
}
