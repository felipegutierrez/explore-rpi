package org.sense.edgent;

import java.util.Scanner;

import org.sense.edgent.app.TempSensorApp;
import org.sense.edgent.app.UltrasonicApp;
import org.sense.edgent.app.UltrasonicEdgentApp;
import org.sense.edgent.app.UltrasonicEdgentWindowApp;

public class App {
	public static void main(String[] args) throws Exception {

		int app = 0;
		do {
			System.out.println("0 - exit");
			System.out.println("1 - TempSensor using Apache Edgent");
			System.out.println("2 - Ultrasonic sensor raw implementation");
			System.out.println("3 - Ultrasonic sensor filter with Apache Edgent");
			System.out.println("4 - Ultrasonic sensor window average with Apache Edgent");
			System.out.print("    Please enter which application you want to run: ");

			String msg = (new Scanner(System.in)).nextLine();
			app = Integer.valueOf(msg);
			switch (app) {
			case 0:
				System.out.println("bis später");
				break;
			case 1:
				System.out.println("App 1 selected");
				new TempSensorApp();
				app = 0;
				break;
			case 2:
				System.out.println("App 2 selected");
				new UltrasonicApp();
				app = 0;
				break;
			case 3:
				System.out.println("App 3 selected");
				new UltrasonicEdgentApp();
				app = 0;
				break;
			case 4:
				System.out.println("App 4 selected");
				new UltrasonicEdgentWindowApp();
				app = 0;
				break;
			default:
				System.out.println("No application selected [" + app + "] ");
				break;
			}
		} while (app != 0);
	}
}
