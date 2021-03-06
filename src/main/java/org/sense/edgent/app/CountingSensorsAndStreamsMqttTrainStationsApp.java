package org.sense.edgent.app;

import java.util.concurrent.TimeUnit;

import org.apache.edgent.connectors.mqtt.MqttConfig;
import org.apache.edgent.connectors.mqtt.MqttStreams;
import org.apache.edgent.function.Function;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.sense.sensor.CounterSensor;
import org.sense.util.Platform;
import org.sense.util.PlatformType;
import org.sense.util.RandomScheduler;
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
public class CountingSensorsAndStreamsMqttTrainStationsApp {

	private String ipAddress = "127.0.0.1";
	private String port = "1883";
	private RandomScheduler randomSchedulerGenerator01;
	private RandomScheduler randomSchedulerGenerator02;

	public CountingSensorsAndStreamsMqttTrainStationsApp() {
		this("127.0.0.1", "1883");
		this.randomSchedulerGenerator01 = new RandomScheduler(20000);
		this.randomSchedulerGenerator02 = new RandomScheduler(20000);
	}

	public CountingSensorsAndStreamsMqttTrainStationsApp(String ipAddress, String port) {
		this.ipAddress = ipAddress;
		this.port = port;
		this.randomSchedulerGenerator01 = new RandomScheduler(20000);
		this.randomSchedulerGenerator02 = new RandomScheduler(20000);

		// @formatter:off
		// Sensor at train station 01
		// TemperatureSensor sensor01 = new TemperatureSensor(1, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		// TemperatureSensor sensor02 = new TemperatureSensor(2, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		// TemperatureSensor sensor03 = new TemperatureSensor(3, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		// TemperatureSensor sensor04 = new TemperatureSensor(4, new Platform(2, PlatformType.CITY, new Station(1)));
		// TemperatureSensor sensor05 = new TemperatureSensor(5, new Platform(2, PlatformType.CITY, new Station(1)));
		// TemperatureSensor sensor06 = new TemperatureSensor(6, new Platform(2, PlatformType.CITY, new Station(1)));
		// LiftVibrationSensor sensor07 = new LiftVibrationSensor(7, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		// LiftVibrationSensor sensor08 = new LiftVibrationSensor(8, new Platform(2, PlatformType.CITY, new Station(1)));
		// LiftVibrationSensor sensor09 = new LiftVibrationSensor(9, new Platform(2, PlatformType.CITY, new Station(1)));
		CounterSensor sensor10People = new CounterSensor(10, SensorType.COUNTER_PEOPLE, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)), this.randomSchedulerGenerator01);
		CounterSensor sensor11People = new CounterSensor(11, SensorType.COUNTER_PEOPLE, new Platform(2, PlatformType.CITY, new Station(1)), this.randomSchedulerGenerator01);
		// CounterSensor sensor12Trains = new CounterSensor(12, SensorType.COUNTER_TRAINS, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		// CounterSensor sensor13Trains = new CounterSensor(13, SensorType.COUNTER_TRAINS, new Platform(2, PlatformType.CITY, new Station(1)));
		CounterSensor sensor14Tickets = new CounterSensor(14, SensorType.COUNTER_TICKETS, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)), this.randomSchedulerGenerator01);
		CounterSensor sensor15Tickets = new CounterSensor(15, SensorType.COUNTER_TICKETS, new Platform(2, PlatformType.CITY, new Station(1)), this.randomSchedulerGenerator01);
		// CounterSensor sensor16 = new CounterSensor(16, SensorType.COUNTER_PEOPLE, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		// CounterSensor sensor17 = new CounterSensor(17, SensorType.COUNTER_PEOPLE, new Platform(2, PlatformType.CITY, new Station(1)));
		// Sensor at train station 02
		// TemperatureSensor sensor20 = new TemperatureSensor(20, new Platform(1, PlatformType.CITY, new Station(2)));
		// TemperatureSensor sensor21 = new TemperatureSensor(21, new Platform(1, PlatformType.CITY, new Station(2)));
		// TemperatureSensor sensor22 = new TemperatureSensor(22, new Platform(2, PlatformType.REGIONAL, new Station(2)));
		// TemperatureSensor sensor23 = new TemperatureSensor(23, new Platform(2, PlatformType.REGIONAL, new Station(2)));
		// TemperatureSensor sensor24 = new TemperatureSensor(24, new Platform(3, PlatformType.INTERNATIONAL, new Station(2)));
		// TemperatureSensor sensor25 = new TemperatureSensor(25, new Platform(3, PlatformType.INTERNATIONAL, new Station(2)));
		// LiftVibrationSensor sensor26 = new LiftVibrationSensor(26, new Platform(1, PlatformType.CITY, new Station(2)));
		// LiftVibrationSensor sensor27 = new LiftVibrationSensor(27, new Platform(2, PlatformType.REGIONAL, new Station(2)));
		// LiftVibrationSensor sensor28 = new LiftVibrationSensor(28, new Platform(3, PlatformType.INTERNATIONAL, new Station(2)));
		CounterSensor sensor29People = new CounterSensor(29, SensorType.COUNTER_PEOPLE, new Platform(1, PlatformType.CITY, new Station(2)), this.randomSchedulerGenerator02);
		CounterSensor sensor30People = new CounterSensor(30, SensorType.COUNTER_PEOPLE, new Platform(2, PlatformType.REGIONAL, new Station(2)), this.randomSchedulerGenerator02);
		CounterSensor sensor31People = new CounterSensor(31, SensorType.COUNTER_PEOPLE, new Platform(3, PlatformType.INTERNATIONAL, new Station(2)), this.randomSchedulerGenerator02);
		// CounterSensor sensor32Trains = new CounterSensor(32, SensorType.COUNTER_TRAINS, new Platform(1, PlatformType.CITY, new Station(2)));
		// CounterSensor sensor33Trains = new CounterSensor(33, SensorType.COUNTER_TRAINS, new Platform(2, PlatformType.REGIONAL, new Station(2)));
		// CounterSensor sensor34Trains = new CounterSensor(34, SensorType.COUNTER_TRAINS, new Platform(3, PlatformType.INTERNATIONAL, new Station(2)));
		CounterSensor sensor35Tickets = new CounterSensor(35, SensorType.COUNTER_TICKETS, new Platform(1, PlatformType.CITY, new Station(2)), this.randomSchedulerGenerator02);
		CounterSensor sensor36Tickets = new CounterSensor(36, SensorType.COUNTER_TICKETS, new Platform(2, PlatformType.REGIONAL, new Station(2)), this.randomSchedulerGenerator02);
		CounterSensor sensor37Tickets = new CounterSensor(37, SensorType.COUNTER_TICKETS, new Platform(3, PlatformType.INTERNATIONAL, new Station(2)), this.randomSchedulerGenerator02);
		// @formatter:on

		DirectProvider dp = new DirectProvider();
		Topology topology = dp.newTopology();

		int qos = 0;
		boolean retain = false;
		MqttConfig config = new MqttConfig("tcp://" + this.ipAddress + ":" + this.port,
				"CountingSensorsAndStreamsMqttTrainStationsApp");
		MqttStreams mqtt = new MqttStreams(topology, () -> config);

		// @formatter:off
		// train station 01 - generating normal data
		// TStream<String> sensor01Readings = topology.poll(sensor01, 1000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		// TStream<String> sensor02Readings = topology.poll(sensor02, 1000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		// TStream<String> sensor03Readings = topology.poll(sensor03, 1000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		// TStream<String> sensor04Readings = topology.poll(sensor04, 1000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		// TStream<String> sensor05Readings = topology.poll(sensor05, 1000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		// TStream<String> sensor06Readings = topology.poll(sensor06, 1000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		// TStream<String> sensor07Readings = topology.poll(sensor07, 2000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		// TStream<String> sensor08Readings = topology.poll(sensor08, 2000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		// TStream<String> sensor09Readings = topology.poll(sensor09, 2000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor10Readings = topology.poll(sensor10People, 100, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor11Readings = topology.poll(sensor11People, 100, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		// TStream<String> sensor12ReadingsTrains = topology.poll(sensor12Trains, 10000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		// TStream<String> sensor13ReadingsTrains = topology.poll(sensor13Trains, 10000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor14ReadingsTickets = topology.poll(sensor14Tickets, 100, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor15ReadingsTickets = topology.poll(sensor15Tickets, 100, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		// TStream<String> sensor16Readings = topology.poll(sensor16, 1000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		// TStream<String> sensor17Readings = topology.poll(sensor17, 1000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		// train station 02 - generating data very skewed
		// TStream<String> sensor20Readings = topology.poll(sensor20, 1000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		// TStream<String> sensor21Readings = topology.poll(sensor21, 1000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		// TStream<String> sensor22Readings = topology.poll(sensor22, 2000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		// TStream<String> sensor23Readings = topology.poll(sensor23, 2000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		// TStream<String> sensor24Readings = topology.poll(sensor24, 2000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		// TStream<String> sensor25Readings = topology.poll(sensor25, 3000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		// TStream<String> sensor26Readings = topology.poll(sensor26, 3000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		// TStream<String> sensor27Readings = topology.poll(sensor27, 5000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		// TStream<String> sensor28Readings = topology.poll(sensor28, 5000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor29Readings = topology.poll(sensor29People, 100, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor30Readings = topology.poll(sensor30People, 100, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor31Readings = topology.poll(sensor31People, 100, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		// TStream<String> sensor32ReadingsTrains = topology.poll(sensor32Trains, 20000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		// TStream<String> sensor33ReadingsTrains = topology.poll(sensor33Trains, 20000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		// TStream<String> sensor34ReadingsTrains = topology.poll(sensor34Trains, 20000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor35ReadingsTickets = topology.poll(sensor35Tickets, 100, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor36ReadingsTickets = topology.poll(sensor36Tickets, 100, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor37ReadingsTickets = topology.poll(sensor37Tickets, 100, TimeUnit.MILLISECONDS).map(new SensorIntMapper());

		// TStream<String> tempReadingsStation01Temp = sensor01Readings.union(sensor02Readings).union(sensor03Readings)
		// 		.union(sensor04Readings).union(sensor05Readings).union(sensor06Readings);
		// TStream<String> tempReadingsStation01Lift = sensor07Readings.union(sensor08Readings).union(sensor09Readings);
		TStream<String> tempReadingsStation01People = sensor10Readings.union(sensor11Readings);
		// TStream<String> tempReadingsStation01Trains = sensor12ReadingsTrains.union(sensor13ReadingsTrains);
		TStream<String> tempReadingsStation01Tickets = sensor14ReadingsTickets.union(sensor15ReadingsTickets);
		
		// TStream<String> tempReadingsStation02Temp = sensor20Readings.union(sensor21Readings).union(sensor22Readings)
		// 		.union(sensor23Readings).union(sensor24Readings).union(sensor25Readings);
		// TStream<String> tempReadingsStation02Lift = sensor26Readings.union(sensor27Readings).union(sensor28Readings);
		TStream<String> tempReadingsStation02People = sensor29Readings.union(sensor30Readings).union(sensor31Readings);
		// TStream<String> tempReadingsStation02Trains = sensor32ReadingsTrains.union(sensor33ReadingsTrains).union(sensor34ReadingsTrains);
		TStream<String> tempReadingsStation02Tickets = sensor35ReadingsTickets.union(sensor36ReadingsTickets).union(sensor37ReadingsTickets);

		// mqtt.publish(tempReadingsStation01Temp, "topic-station-01-temp", qos, retain);
		// mqtt.publish(tempReadingsStation01Lift, "topic-station-01-lift", qos, retain);
		mqtt.publish(tempReadingsStation01People, "topic-station-01-people", qos, retain);
		// mqtt.publish(tempReadingsStation01Trains, "topic-station-01-trains", qos, retain);
		mqtt.publish(tempReadingsStation01Tickets, "topic-station-01-tickets", qos, retain);

		// mqtt.publish(tempReadingsStation02Temp, "topic-station-02-temp", qos, retain);
		// mqtt.publish(tempReadingsStation02Lift, "topic-station-02-lift", qos, retain);
		mqtt.publish(tempReadingsStation02People, "topic-station-02-people", qos, retain);
		// mqtt.publish(tempReadingsStation02Trains, "topic-station-02-trains", qos, retain);
		mqtt.publish(tempReadingsStation02Tickets, "topic-station-02-tickets", qos, retain);
		// @formatter:on

		dp.submit(topology);
	}

	public static class SensorDoubleMapper implements Function<Tuple3<SensorKey, Long, Double>, String> {

		private static final long serialVersionUID = -183900884352862695L;

		@Override
		public String apply(Tuple3<SensorKey, Long, Double> value) {
			return value.f0.toString() + "|" + String.valueOf(value.f1) + "|" + String.valueOf(value.f2);
		}
	}

	public static class SensorIntMapper implements Function<Tuple4<SensorKey, Long, Integer, String>, String> {

		private static final long serialVersionUID = 5478136460473668449L;

		@Override
		public String apply(Tuple4<SensorKey, Long, Integer, String> value) {
			return value.f0.toString() + "|" + String.valueOf(value.f1) + "|" + String.valueOf(value.f2) + "|"
					+ String.valueOf(value.f3);
		}
	}
}
