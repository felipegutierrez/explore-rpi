package org.sense.sensor;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class Ultrasonic {

	private int PIN_ECHO;
	private int PIN_TRIG;
	private long REJECTION_START = 1000; // ns
	private long REJECTION_TIME = 1000; // ns

	// gpio controller ; using io.gpio
	private GpioController gpio;
	// gpio output pins; using io.gpio, digital pins
	private GpioPinDigitalOutput pin_trig;
	private GpioPinDigitalInput pin_echo;

	public Ultrasonic(int ECHO, int TRIG, long REJ_START, long REJ_TIME) { // GPIO
		this.PIN_ECHO = ECHO;
		this.PIN_TRIG = TRIG;
		this.REJECTION_START = REJ_START;
		this.REJECTION_TIME = REJ_TIME;

		gpio = GpioFactory.getInstance();// create gpio controller , io.gpio

		// motor_1_left_en=gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01,
		// "motor_1_left_en", PinState.HIGH);
		pin_trig = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(PIN_TRIG), "pin_trig", PinState.HIGH);// pin,tag,initial-state
		pin_trig.setShutdownOptions(true, PinState.LOW);

		pin_echo = gpio.provisionDigitalInputPin(RaspiPin.getPinByAddress(PIN_ECHO), PinPullResistance.PULL_DOWN);// pin,tag,initial-state

	}

	public int getDistance() throws Exception { // in milimeters
		int distance = 0;
		long start_time, end_time, rejection_start = 0, rejection_time = 0;
		// Start ranging- trig should be in high state for 10us to generate ultrasonic
		// signal
		// this will generate 8 cycle sonic burst.
		// produced signal would looks like, _|-----|
		pin_trig.low();
		busyWaitMicros(2);
		pin_trig.high();
		busyWaitMicros(10);
		pin_trig.low();

		// echo pin high time is propotional to the distance _|----|
		// distance calculation
		while (pin_echo.isLow()) { // wait until echo get high
			busyWaitNanos(1); // wait 1ns
			rejection_start++;
			if (rejection_start == REJECTION_START)
				return -1; // infinity
		}
		start_time = System.nanoTime();

		while (pin_echo.isHigh()) { // wait until echo get low
			busyWaitNanos(1); // wait 1ns
			rejection_time++;
			if (rejection_time == REJECTION_TIME)
				return -1; // infinity
		}
		end_time = System.nanoTime();

		distance = (int) ((end_time - start_time) / 5882.35294118); // distance in mm
		// distance=(end_time-start_time)/(200*29.1); //distance in mm
		return distance;
	}

	public static void busyWaitMicros(long micros) {
		long waitUntil = System.nanoTime() + (micros * 1_000);
		while (waitUntil > System.nanoTime()) {
			;
		}
	}

	public static void busyWaitNanos(long nanos) {
		long waitUntil = System.nanoTime() + nanos;
		while (waitUntil > System.nanoTime()) {
			;
		}
	}
}
