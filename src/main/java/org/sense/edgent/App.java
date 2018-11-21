package org.sense.edgent;

import java.util.Scanner;

import org.sense.edgent.app.TempSensorApp;
import org.sense.edgent.app.UltrasonicApp;
import org.sense.edgent.app.UltrasonicEdgentApp;

public class App {
	public static void main(String[] args) throws Exception {

		int app = 0;
		do {
			System.out.println("0 - exit");
			System.out.println("1 - TempSensor using Apache Edgent");
			System.out.println("2 - Ultrasonic sensor raw implementation");
			System.out.println("3 - Ultrasonic sensor with Apache Edgent");
			System.out.print("Please enter which application you want to run: ");

			String msg = (new Scanner(System.in)).nextLine();
			app = Integer.valueOf(msg);
			switch (app) {
			case 1:
				System.out.println("App 1 selected");
				TempSensorApp tempSensorApp = new TempSensorApp();
				app = 0;
				break;
			case 2:
				System.out.println("App 2 selected");
				new UltrasonicApp();
				app = 0;
				break;
			case 3:
				System.out.println("App 3 selected");
				UltrasonicEdgentApp ultrasonicEdgentApp = new UltrasonicEdgentApp();
				app = 0;
				break;
			default:
				System.out.println("No application selected [" + app + "] ");
				app = 0;
				break;
			}
		} while (app != 0);
	}
}
