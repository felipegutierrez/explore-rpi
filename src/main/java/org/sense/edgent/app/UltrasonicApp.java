package org.sense.edgent.app;

import org.sense.sensor.Ultrasonic;

/**
 * export JAVA_TOOL_OPTIONS="-Dpi4j.linking=dynamic"
 * 
 * @author Felipe
 *
 */
public class UltrasonicApp {
	public UltrasonicApp() throws Exception {
		Ultrasonic sonic = new Ultrasonic();
		System.out.println("Started.......");
		sonic.run();
	}
}
