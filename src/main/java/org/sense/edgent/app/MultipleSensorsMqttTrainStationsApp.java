package org.sense.edgent.app;

import java.util.concurrent.TimeUnit;

import org.apache.edgent.connectors.mqtt.MqttConfig;
import org.apache.edgent.connectors.mqtt.MqttStreams;
import org.apache.edgent.function.Function;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import org.apache.flink.api.java.tuple.Tuple3;
import org.sense.sensor.CounterSensor;
import org.sense.sensor.LiftVibrationSensor;
import org.sense.sensor.TemperatureSensor;
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
public class MultipleSensorsMqttTrainStationsApp {

	private String ipAddress = "127.0.0.1";
	private String port = "1883";

	public MultipleSensorsMqttTrainStationsApp() {
		this("127.0.0.1", "1883");
	}

	public MultipleSensorsMqttTrainStationsApp(String ipAddress, String port) {

		// @formatter:off
		// Sensor at train station 01
		TemperatureSensor sensor01Temp = new TemperatureSensor(1, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		TemperatureSensor sensor02Temp = new TemperatureSensor(2, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		TemperatureSensor sensor03Temp = new TemperatureSensor(3, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		TemperatureSensor sensor04Temp = new TemperatureSensor(4, new Platform(2, PlatformType.CITY, new Station(1)));
		TemperatureSensor sensor05Temp = new TemperatureSensor(5, new Platform(2, PlatformType.CITY, new Station(1)));
		TemperatureSensor sensor06Temp = new TemperatureSensor(6, new Platform(2, PlatformType.CITY, new Station(1)));
		LiftVibrationSensor sensor07Lift = new LiftVibrationSensor(7, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		LiftVibrationSensor sensor08Lift = new LiftVibrationSensor(8, new Platform(2, PlatformType.CITY, new Station(1)));
		LiftVibrationSensor sensor09Lift = new LiftVibrationSensor(9, new Platform(2, PlatformType.CITY, new Station(1)));
		CounterSensor sensor10People = new CounterSensor(10, SensorType.COUNTER_PEOPLE, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		CounterSensor sensor11People = new CounterSensor(11, SensorType.COUNTER_PEOPLE, new Platform(2, PlatformType.CITY, new Station(1)));
		CounterSensor sensor12Trains = new CounterSensor(12, SensorType.COUNTER_TRAINS, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		CounterSensor sensor13Trains = new CounterSensor(13, SensorType.COUNTER_TRAINS, new Platform(2, PlatformType.CITY, new Station(1)));
		CounterSensor sensor14Tickets = new CounterSensor(14, SensorType.COUNTER_TICKETS, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		CounterSensor sensor15Tickets = new CounterSensor(15, SensorType.COUNTER_TICKETS, new Platform(2, PlatformType.CITY, new Station(1)));
		// CounterSensor sensor16People = new CounterSensor(16, SensorType.COUNTER_PEOPLE, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		// CounterSensor sensor17People = new CounterSensor(17, SensorType.COUNTER_PEOPLE, new Platform(2, PlatformType.CITY, new Station(1)));
		// Sensor at train station 02
		TemperatureSensor sensor20Temp = new TemperatureSensor(20, new Platform(1, PlatformType.CITY, new Station(2)));
		TemperatureSensor sensor21Temp = new TemperatureSensor(21, new Platform(1, PlatformType.CITY, new Station(2)));
		TemperatureSensor sensor22Temp = new TemperatureSensor(22, new Platform(2, PlatformType.REGIONAL, new Station(2)));
		TemperatureSensor sensor23Temp = new TemperatureSensor(23, new Platform(2, PlatformType.REGIONAL, new Station(2)));
		TemperatureSensor sensor24Temp = new TemperatureSensor(24, new Platform(3, PlatformType.INTERNATIONAL, new Station(2)));
		TemperatureSensor sensor25Temp = new TemperatureSensor(25, new Platform(3, PlatformType.INTERNATIONAL, new Station(2)));
		LiftVibrationSensor sensor26Lift = new LiftVibrationSensor(26, new Platform(1, PlatformType.CITY, new Station(2)));
		LiftVibrationSensor sensor27Lift = new LiftVibrationSensor(27, new Platform(2, PlatformType.REGIONAL, new Station(2)));
		LiftVibrationSensor sensor28Lift = new LiftVibrationSensor(28, new Platform(3, PlatformType.INTERNATIONAL, new Station(2)));
		CounterSensor sensor29People = new CounterSensor(29, SensorType.COUNTER_PEOPLE, new Platform(1, PlatformType.CITY, new Station(2)));
		CounterSensor sensor30People = new CounterSensor(30, SensorType.COUNTER_PEOPLE, new Platform(2, PlatformType.REGIONAL, new Station(2)));
		CounterSensor sensor31People = new CounterSensor(31, SensorType.COUNTER_PEOPLE, new Platform(3, PlatformType.INTERNATIONAL, new Station(2)));
		CounterSensor sensor32Trains = new CounterSensor(32, SensorType.COUNTER_TRAINS, new Platform(1, PlatformType.CITY, new Station(2)));
		CounterSensor sensor33Trains = new CounterSensor(33, SensorType.COUNTER_TRAINS, new Platform(2, PlatformType.REGIONAL, new Station(2)));
		CounterSensor sensor34Trains = new CounterSensor(34, SensorType.COUNTER_TRAINS, new Platform(3, PlatformType.INTERNATIONAL, new Station(2)));
		CounterSensor sensor35Tickets = new CounterSensor(35, SensorType.COUNTER_TICKETS, new Platform(1, PlatformType.CITY, new Station(2)));
		CounterSensor sensor36Tickets = new CounterSensor(36, SensorType.COUNTER_TICKETS, new Platform(2, PlatformType.REGIONAL, new Station(2)));
		CounterSensor sensor37Tickets = new CounterSensor(37, SensorType.COUNTER_TICKETS, new Platform(3, PlatformType.INTERNATIONAL, new Station(2)));
		// @formatter:on

		DirectProvider dp = new DirectProvider();
		Topology topology = dp.newTopology();

		int qos = 0;
		boolean retain = false;
		MqttConfig config = new MqttConfig("tcp://" + this.ipAddress + ":" + this.port,
				"MultipleSensorsMqttTrainStationsApp");
		MqttStreams mqtt = new MqttStreams(topology, () -> config);

		// @formatter:off
		// train station 01
		TStream<String> sensor01ReadingsTemp = topology.poll(sensor01Temp, 20000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor02ReadingsTemp = topology.poll(sensor02Temp, 20000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor03ReadingsTemp = topology.poll(sensor03Temp, 20000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor04ReadingsTemp = topology.poll(sensor04Temp, 20000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor05ReadingsTemp = topology.poll(sensor05Temp, 20000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor06ReadingsTemp = topology.poll(sensor06Temp, 20000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor07ReadingsLift = topology.poll(sensor07Lift, 20000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor08ReadingsLift = topology.poll(sensor08Lift, 20000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor09ReadingsLift = topology.poll(sensor09Lift, 20000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor10ReadingsPeople = topology.poll(sensor10People, 3000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor11ReadingsPeople = topology.poll(sensor11People, 20000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor12ReadingsTrains = topology.poll(sensor12Trains, 20000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor13ReadingsTrains = topology.poll(sensor13Trains, 20000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor14ReadingsTickets = topology.poll(sensor14Tickets, 3000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor15ReadingsTickets = topology.poll(sensor15Tickets, 20000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		// TStream<String> sensor16ReadingsPeople = topology.poll(sensor16People, 1000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		// TStream<String> sensor17ReadingsPeople = topology.poll(sensor17People, 1000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		// train station 02
		TStream<String> sensor20ReadingsTemp = topology.poll(sensor20Temp, 20000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor21ReadingsTemp = topology.poll(sensor21Temp, 20000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor22ReadingsTemp = topology.poll(sensor22Temp, 20000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor23ReadingsTemp = topology.poll(sensor23Temp, 20000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor24ReadingsTemp = topology.poll(sensor24Temp, 20000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor25ReadingsTemp = topology.poll(sensor25Temp, 20000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor26ReadingsLift = topology.poll(sensor26Lift, 20000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor27ReadingsLift = topology.poll(sensor27Lift, 20000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor28ReadingsLift = topology.poll(sensor28Lift, 20000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor29ReadingsPeople = topology.poll(sensor29People, 3000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor30ReadingsPeople = topology.poll(sensor30People, 20000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor31ReadingsPeople = topology.poll(sensor31People, 20000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor32ReadingsTrains = topology.poll(sensor32Trains, 20000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor33ReadingsTrains = topology.poll(sensor33Trains, 20000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor34ReadingsTrains = topology.poll(sensor34Trains, 20000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor35ReadingsTickets = topology.poll(sensor35Tickets, 3000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor36ReadingsTickets = topology.poll(sensor36Tickets, 20000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor37ReadingsTickets = topology.poll(sensor37Tickets, 20000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		// @formatter:on

		// @formatter:off
		TStream<String> tempReadingsStation01 = sensor01ReadingsTemp
				.union(sensor02ReadingsTemp)
				.union(sensor03ReadingsTemp)
				.union(sensor04ReadingsTemp)
				.union(sensor05ReadingsTemp)
				.union(sensor06ReadingsTemp)
				.union(sensor07ReadingsLift)
				.union(sensor08ReadingsLift)
				.union(sensor09ReadingsLift)
				.union(sensor10ReadingsPeople)
				.union(sensor11ReadingsPeople)
				.union(sensor12ReadingsTrains)
				.union(sensor13ReadingsTrains)
				.union(sensor14ReadingsTickets)
				.union(sensor15ReadingsTickets)
				// .union(sensor16ReadingsPeople)
				// .union(sensor17ReadingsPeople)
				;
		TStream<String> tempReadingsStation02 = sensor20ReadingsTemp
				.union(sensor21ReadingsTemp)
				.union(sensor22ReadingsTemp)
				.union(sensor23ReadingsTemp)
				.union(sensor24ReadingsTemp)
				.union(sensor25ReadingsTemp)
				.union(sensor26ReadingsLift)
				.union(sensor27ReadingsLift)
				.union(sensor28ReadingsLift)
				.union(sensor29ReadingsPeople)
				.union(sensor30ReadingsPeople)
				.union(sensor31ReadingsPeople)
				.union(sensor32ReadingsTrains)
				.union(sensor33ReadingsTrains)
				.union(sensor34ReadingsTrains)
				.union(sensor35ReadingsTickets)
				.union(sensor36ReadingsTickets)
				.union(sensor37ReadingsTickets)
				;

		// tempReadingsStation01.print();
		// tempReadingsStation02.print();
		// TSink<String> sink01 = mqtt.publish(tempReadingsStation01, "topic-station-01", qos, retain);
		// TSink<String> sink02 = mqtt.publish(tempReadingsStation02, "topic-station-02", qos, retain);
		// @formatter:on

		mqtt.publish(tempReadingsStation01, "topic-station-01", qos, retain);
		mqtt.publish(tempReadingsStation02, "topic-station-02", qos, retain);

		dp.submit(topology);
	}

	public static class SensorDoubleMapper implements Function<Tuple3<SensorKey, Long, Double>, String> {

		private static final long serialVersionUID = -183900884352862695L;

		@Override
		public String apply(Tuple3<SensorKey, Long, Double> value) {
			return value.f0.toString() + "|" + String.valueOf(value.f1) + "|" + String.valueOf(value.f2);
		}
	}

	public static class SensorIntMapper implements Function<Tuple3<SensorKey, Long, Integer>, String> {

		private static final long serialVersionUID = 5478136460473668449L;

		@Override
		public String apply(Tuple3<SensorKey, Long, Integer> value) {
			return value.f0.toString() + "|" + String.valueOf(value.f1) + "|" + String.valueOf(value.f2);
		}
	}
}
