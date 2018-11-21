package org.sense.sensor;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;

public class Ultrasonic {

	// GPIO Pins
	private static GpioPinDigitalOutput sensorTriggerPin;
	private static GpioPinDigitalInput sensorEchoPin;

	final static GpioController gpio = GpioFactory.getInstance();

	public void run() throws InterruptedException {
		// Trigger pin as OUTPUT
		sensorTriggerPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04);
		// Echo pin as INPUT
		sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_DOWN);

		for (int count = 0; count < 10; count++) {
			try {
				Thread.sleep(2000);
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

				double distanceCM = ((((endTime - startTime) / 1e3) / 2) / 29.1);
				// Printing out the distance in centimeters
				System.out.println("Distance: " + distanceCM + " centimeters");
				Thread.sleep(1000);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
