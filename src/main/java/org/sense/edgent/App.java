package org.sense.edgent;

import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.sense.edgent.app.AdaptableFilterRangeApp;
import org.sense.edgent.app.CountingSensorsAndStreamsMqttTrainStationsApp;
import org.sense.edgent.app.MultipleSensorsAndStreamsMqttTrainStationsApp;
import org.sense.edgent.app.MultipleSensorsMqttTrainStationsApp;
import org.sense.edgent.app.TempMultipleSensorMqttApp;
import org.sense.edgent.app.TempSensorApp;
import org.sense.edgent.app.TempSensorMqttApp;
import org.sense.edgent.app.TempSensorUnionMqttApp;
import org.sense.edgent.app.TempSensorWindowApp;
import org.sense.edgent.app.UltrasonicApp;
import org.sense.edgent.app.UltrasonicEdgentApp;
import org.sense.edgent.app.UltrasonicEdgentWindowApp;

public class App {
	public static void main(String[] args) throws Exception {

		BasicConfigurator.configure();

		int app = 0;
		do {
			// @formatter:off
			System.out.println("0  - exit");
			System.out.println("1  - TempSensor using Apache Edgent");
			System.out.println("2  - Ultrasonic sensor raw implementation");
			System.out.println("3  - Ultrasonic sensor filter with Apache Edgent");
			System.out.println("4  - Ultrasonic sensor window average with Apache Edgent");
			System.out.println("5  - TempSensor window average with Apache Edgent");
			System.out.println("6  - TempSensor MQTT connector with Apache Edgent");
			System.out.println("7  - TempSensor union MQTT connector with Apache Edgent");
			System.out.println("8  - TempSensor adaptive filter MQTT connector with Apache Edgent");
			System.out.println("9  - Multiple temperature sensors using MQTT connector with Edgent");
			System.out.println("10 - Multiple sensors on train stations using MQTT connector with Edgent");
			System.out.println("11 - Multiple sensors and streams on train stations using MQTT connector with Edgent");
			System.out.println("12 - Counting people using MQTT connector with Edgent");
			// @formatter:on

			String msg = "0";
			String ipAddress = null;
			if (args != null && args.length > 0) {
				msg = args[0];
				if (msg.matches("-?\\d+")) {
					System.out.println("    Application choosed: " + msg);
				} else {
					msg = "999";
				}
				if (args.length > 1) {
					ipAddress = args[1];
				}
			} else {
				System.out.print("     Please enter which application you want to run: ");
				msg = (new Scanner(System.in)).nextLine();
			}

			// @formatter:off
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
			case 5:
				System.out.println("App 5 selected");
				new TempSensorWindowApp();
				app = 0;
				break;
			case 6:
				System.out.println("App 6 selected");
				System.out.println("Open a terminal and type: 'mosquitto_sub -h 127.0.0.1 -t topic-edgent' ");
				System.out.println("to receive values from the mqtt publisher");
				new TempSensorMqttApp();
				app = 0;
				break;
			case 7:
				System.out.println("App 7 selected");
				new TempSensorUnionMqttApp();
				app = 0;
				break;
			case 8:
				System.out.println("App 8 selected");
				System.out.println("use 'mosquitto_pub -h 127.0.0.1 -t topic-parameter -m \"[70.0..120.0]\"' ");
				System.out.println("on the terminal to change the parameters at runtime.");
				new AdaptableFilterRangeApp();
				app = 0;
				break;
			case 9:
				System.out.println("App 9 (Multiple temperature sensors) selected");
				System.out.println("Open a terminal and type: 'mosquitto_sub -h 127.0.0.1 -t topic-edgent' ");
				System.out.println("to receive values from the mqtt publisher");
				new TempMultipleSensorMqttApp();
				app = 0;
				break;
			case 10:
				System.out.println("App 10 (Multiple sensors on train stations) selected");
				if (ipAddress == null) {
					ipAddress = "127.0.0.1";
				}
				System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t topic-station-01' ");
				System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t topic-station-02' ");
				System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t \'#\'");
				new MultipleSensorsMqttTrainStationsApp(ipAddress, "1883");
				app = 0;
				break;
			case 11:
				System.out.println("App 11 (Multiple sensors and streams on train stations) selected");
				if (ipAddress == null) {
					ipAddress = "127.0.0.1";
				}
				System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t topic-station-01-temp' ");
				System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t topic-station-01-lift' ");
				System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t topic-station-01-people' ");
				System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t topic-station-01-trains' ");
				System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t topic-station-01-tickets' ");
				System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t topic-station-02-temp' ");
				System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t topic-station-02-lift' ");
				System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t topic-station-02-people' ");
				System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t topic-station-02-trains' ");
				System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t topic-station-02-tickets' ");
				System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t \'#\'");
				new MultipleSensorsAndStreamsMqttTrainStationsApp(ipAddress, "1883");
				app = 0;
				break;
			case 12:
				System.out.println("App 12 (Counting people and tickets streams on train stations) selected");
				if (ipAddress == null) {
					ipAddress = "127.0.0.1";
				}
				System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t topic-station-01-people' ");
				System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t topic-station-01-tickets' ");
				System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t topic-station-02-people' ");
				System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t topic-station-02-tickets' ");
				System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t \'#\'");
				new CountingSensorsAndStreamsMqttTrainStationsApp(ipAddress, "1883");
				app = 0;
				break;
			default:
				System.out.println("No application selected [" + app + "] ");
				break;
			}
			// @formatter:on
		} while (app != 0);
	}
}
