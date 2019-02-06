package org.sense.edgent.app;

import java.util.concurrent.TimeUnit;

import org.apache.edgent.connectors.mqtt.MqttConfig;
import org.apache.edgent.connectors.mqtt.MqttStreams;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TSink;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import org.sense.sensor.CounterSensor;
import org.sense.sensor.LiftVibrationSensor;
import org.sense.sensor.TemperatureSensor;
import org.sense.util.Platform;
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

	public MultipleSensorsMqttTrainStationsApp() {

		// Sensor at train station 01
		TemperatureSensor sensor01 = new TemperatureSensor(1, new Platform(1, new Station(1)));
		TemperatureSensor sensor02 = new TemperatureSensor(2, new Platform(1, new Station(1)));
		TemperatureSensor sensor03 = new TemperatureSensor(3, new Platform(1, new Station(1)));
		TemperatureSensor sensor04 = new TemperatureSensor(4, new Platform(2, new Station(1)));
		TemperatureSensor sensor05 = new TemperatureSensor(5, new Platform(2, new Station(1)));
		TemperatureSensor sensor06 = new TemperatureSensor(6, new Platform(2, new Station(1)));
		LiftVibrationSensor sensor07 = new LiftVibrationSensor(7, new Platform(1, new Station(1)));
		LiftVibrationSensor sensor08 = new LiftVibrationSensor(8, new Platform(2, new Station(1)));
		LiftVibrationSensor sensor09 = new LiftVibrationSensor(9, new Platform(2, new Station(1)));
		CounterSensor sensor10 = new CounterSensor(10, SensorType.COUNTER_PEOPLE, new Platform(1, new Station(1)));
		CounterSensor sensor11 = new CounterSensor(11, SensorType.COUNTER_PEOPLE, new Platform(2, new Station(1)));
		CounterSensor sensor12 = new CounterSensor(12, SensorType.COUNTER_TRAINS, new Platform(1, new Station(1)));
		CounterSensor sensor13 = new CounterSensor(13, SensorType.COUNTER_TRAINS, new Platform(2, new Station(1)));
		CounterSensor sensor14 = new CounterSensor(14, SensorType.COUNTER_TICKETS, new Platform(null, new Station(1)));
		CounterSensor sensor15 = new CounterSensor(15, SensorType.COUNTER_PEOPLE, new Platform(null, new Station(1)));

		DirectProvider dp = new DirectProvider();
		Topology topology = dp.newTopology();

		int qos = 0;
		boolean retain = false;
		MqttConfig config = new MqttConfig("tcp://127.0.0.1:1883", "TempMultipleSensorMqttApp");
		MqttStreams mqtt = new MqttStreams(topology, () -> config);

		// receive values from 5 different sources
		TStream<String> sensor01Readings = topology.poll(sensor01, 1000, TimeUnit.MILLISECONDS).map(p -> {
			return p.f0.toString() + "|" + String.valueOf(p.f1);
		});
		TStream<String> sensor02Readings = topology.poll(sensor02, 1000, TimeUnit.MILLISECONDS).map(p -> {
			return p.f0.toString() + "|" + String.valueOf(p.f1);
		});
		TStream<String> sensor03Readings = topology.poll(sensor03, 1000, TimeUnit.MILLISECONDS).map(p -> {
			return p.f0.toString() + "|" + String.valueOf(p.f1);
		});
		TStream<String> sensor04Readings = topology.poll(sensor04, 1000, TimeUnit.MILLISECONDS).map(p -> {
			return p.f0.toString() + "|" + String.valueOf(p.f1);
		});
		TStream<String> sensor05Readings = topology.poll(sensor05, 1000, TimeUnit.MILLISECONDS).map(p -> {
			return p.f0.toString() + "|" + String.valueOf(p.f1);
		});
		TStream<String> sensor06Readings = topology.poll(sensor06, 1000, TimeUnit.MILLISECONDS).map(p -> {
			return p.f0.toString() + "|" + String.valueOf(p.f1);
		});
		TStream<String> sensor07Readings = topology.poll(sensor07, 2000, TimeUnit.MILLISECONDS).map(p -> {
			return p.f0.toString() + "|" + String.valueOf(p.f1);
		});
		TStream<String> sensor08Readings = topology.poll(sensor08, 2000, TimeUnit.MILLISECONDS).map(p -> {
			return p.f0.toString() + "|" + String.valueOf(p.f1);
		});
		TStream<String> sensor09Readings = topology.poll(sensor09, 2000, TimeUnit.MILLISECONDS).map(p -> {
			return p.f0.toString() + "|" + String.valueOf(p.f1);
		});
		TStream<String> sensor10Readings = topology.poll(sensor10, 3000, TimeUnit.MILLISECONDS).map(p -> {
			return p.f0.toString() + "|" + String.valueOf(p.f1);
		});
		TStream<String> sensor11Readings = topology.poll(sensor11, 3000, TimeUnit.MILLISECONDS).map(p -> {
			return p.f0.toString() + "|" + String.valueOf(p.f1);
		});
		TStream<String> sensor12Readings = topology.poll(sensor12, 5000, TimeUnit.MILLISECONDS).map(p -> {
			return p.f0.toString() + "|" + String.valueOf(p.f1);
		});
		TStream<String> sensor13Readings = topology.poll(sensor13, 5000, TimeUnit.MILLISECONDS).map(p -> {
			return p.f0.toString() + "|" + String.valueOf(p.f1);
		});
		TStream<String> sensor14Readings = topology.poll(sensor14, 1000, TimeUnit.MILLISECONDS).map(p -> {
			return p.f0.toString() + "|" + String.valueOf(p.f1);
		});
		TStream<String> sensor15Readings = topology.poll(sensor15, 1000, TimeUnit.MILLISECONDS).map(p -> {
			return p.f0.toString() + "|" + String.valueOf(p.f1);
		});

		// @formatter:off
		TStream<String> tempReadingsStation01 = sensor01Readings
				.union(sensor02Readings)
				.union(sensor03Readings)
				.union(sensor04Readings)
				.union(sensor05Readings)
				.union(sensor06Readings)
				.union(sensor07Readings)
				.union(sensor08Readings)
				.union(sensor09Readings)
				.union(sensor10Readings)
				.union(sensor11Readings)
				.union(sensor12Readings)
				.union(sensor13Readings)
				.union(sensor14Readings)
				.union(sensor15Readings);
		// @formatter:on

		TSink<String> sink = mqtt.publish(tempReadingsStation01, "topic-station-01", qos, retain);

		dp.submit(topology);
	}
}
