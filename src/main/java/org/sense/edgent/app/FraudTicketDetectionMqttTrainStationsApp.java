package org.sense.edgent.app;

import java.util.concurrent.TimeUnit;

import org.apache.edgent.connectors.mqtt.MqttConfig;
import org.apache.edgent.connectors.mqtt.MqttStreams;
import org.apache.edgent.function.Function;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import org.apache.flink.api.java.tuple.Tuple4;
import org.sense.sensor.Sensor;
import org.sense.util.Platform;
import org.sense.util.PlatformType;
import org.sense.util.SensorKey;
import org.sense.util.SensorType;
import org.sense.util.Station;

/**
 * Open a terminal and type: 'mosquitto_sub -h 127.0.0.1 -t topic-edgent' to
 * receive values from the mqtt publisher
 * 
 * @author Felipe Oliveira Gutierrez
 *
 */
public class FraudTicketDetectionMqttTrainStationsApp {

	private final String TOPIC_STATION_01_PLAT_01_TICKETS = "topic-station-01-plat-01-tickets";
	private final String TOPIC_STATION_01_PLAT_02_TICKETS = "topic-station-01-plat-02-tickets";
	private String ipAddress = "127.0.0.1";
	private String port = "1883";

	private void disclaimer() {
		// @formatter:off
		System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t " + TOPIC_STATION_01_PLAT_01_TICKETS + "' ");
		System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t " + TOPIC_STATION_01_PLAT_02_TICKETS + "' ");
		System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t \'#\'");
		// @formatter:on
	}

	public FraudTicketDetectionMqttTrainStationsApp() {
		this("127.0.0.1", "1883");
	}

	public FraudTicketDetectionMqttTrainStationsApp(String ipAddress, String port) {
		disclaimer();
		this.ipAddress = ipAddress;
		this.port = port;

		// @formatter:off
		// Sensor at train station 01 ------sensorID, sensorType,              platformId, platformType,      stationId,       TICKET-ID_and_CITY
		Sensor sensor01Tickets = new Sensor(1, SensorType.TICKET, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		Sensor sensor02Tickets = new Sensor(2, SensorType.TICKET, new Platform(2, PlatformType.INTERNATIONAL, new Station(1)));
		// Sensor sensor03Tickets = new Sensor(3, SensorType.TICKET, new Platform(3, PlatformType.INTERNATIONAL, new Station(1)), this.randomTicketGenerator);
		// Sensor sensor04Tickets = new Sensor(4, SensorType.TICKET, new Platform(4, PlatformType.INTERNATIONAL, new Station(1)), this.randomTicketGenerator);
		// Sensor sensor05Tickets = new Sensor(5, SensorType.TICKET, new Platform(1, PlatformType.INTERNATIONAL, new Station(2)));//, this.randomTicketGenerator);
		// Sensor sensor06Tickets = new Sensor(6, SensorType.TICKET, new Platform(2, PlatformType.INTERNATIONAL, new Station(2)), this.randomTicketGenerator);
		// Sensor sensor07Tickets = new Sensor(7, SensorType.TICKET, new Platform(3, PlatformType.INTERNATIONAL, new Station(2)), this.randomTicketGenerator);
		// Sensor sensor08Tickets = new Sensor(8, SensorType.TICKET, new Platform(4, PlatformType.INTERNATIONAL, new Station(2)), this.randomTicketGenerator);
		// Sensor sensor09Tickets = new Sensor(9, SensorType.TICKET, new Platform(1, PlatformType.INTERNATIONAL, new Station(3)), this.randomTicketGenerator);
		// Sensor sensor10Tickets = new Sensor(10, SensorType.TICKET, new Platform(2, PlatformType.INTERNATIONAL, new Station(3)), this.randomTicketGenerator);
		// Sensor sensor11Tickets = new Sensor(11, SensorType.TICKET, new Platform(3, PlatformType.INTERNATIONAL, new Station(3)), this.randomTicketGenerator);
		// Sensor sensor12Tickets = new Sensor(12, SensorType.TICKET, new Platform(4, PlatformType.INTERNATIONAL, new Station(3)), this.randomTicketGenerator);

		DirectProvider dp = new DirectProvider();
		Topology topology = dp.newTopology();

		int qos = 0;
		boolean retain = false;
		MqttConfig config = new MqttConfig("tcp://" + this.ipAddress + ":" + this.port,
				"FraudTicketDetectionMqttTrainStationsApp");
		MqttStreams mqtt = new MqttStreams(topology, () -> config);

		// train station 01 - generating normal data
		TStream<String> sensor01Readings = topology.poll(sensor01Tickets, 100, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor02Readings = topology.poll(sensor02Tickets, 100, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		
		mqtt.publish(sensor01Readings, TOPIC_STATION_01_PLAT_01_TICKETS, qos, retain);
		mqtt.publish(sensor02Readings, TOPIC_STATION_01_PLAT_02_TICKETS, qos, retain);
		// @formatter:on

		dp.submit(topology);
	}

	public static class SensorIntMapper implements Function<Tuple4<SensorKey, Long, Integer, String>, String> {
		private static final long serialVersionUID = 7209777432743400335L;

		@Override
		public String apply(Tuple4<SensorKey, Long, Integer, String> value) {
			return value.f0.toString() + "|" + String.valueOf(value.f1) + "|" + String.valueOf(value.f2) + "|"
					+ String.valueOf(value.f3);
		}
	}
}
