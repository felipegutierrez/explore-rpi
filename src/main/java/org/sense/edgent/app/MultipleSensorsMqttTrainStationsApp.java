package org.sense.edgent.app;

import java.util.concurrent.TimeUnit;

import org.apache.edgent.connectors.mqtt.MqttConfig;
import org.apache.edgent.connectors.mqtt.MqttStreams;
import org.apache.edgent.function.Function;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import org.apache.flink.api.java.tuple.Tuple2;
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

	public MultipleSensorsMqttTrainStationsApp() {

		// @formatter:off
		// Sensor at train station 01
		TemperatureSensor sensor01 = new TemperatureSensor(1, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		TemperatureSensor sensor02 = new TemperatureSensor(2, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		TemperatureSensor sensor03 = new TemperatureSensor(3, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		TemperatureSensor sensor04 = new TemperatureSensor(4, new Platform(2, PlatformType.CITY, new Station(1)));
		TemperatureSensor sensor05 = new TemperatureSensor(5, new Platform(2, PlatformType.CITY, new Station(1)));
		TemperatureSensor sensor06 = new TemperatureSensor(6, new Platform(2, PlatformType.CITY, new Station(1)));
		LiftVibrationSensor sensor07 = new LiftVibrationSensor(7, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		LiftVibrationSensor sensor08 = new LiftVibrationSensor(8, new Platform(2, PlatformType.CITY, new Station(1)));
		LiftVibrationSensor sensor09 = new LiftVibrationSensor(9, new Platform(2, PlatformType.CITY, new Station(1)));
		CounterSensor sensor10 = new CounterSensor(10, SensorType.COUNTER_PEOPLE, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		CounterSensor sensor11 = new CounterSensor(11, SensorType.COUNTER_PEOPLE, new Platform(2, PlatformType.CITY, new Station(1)));
		CounterSensor sensor12 = new CounterSensor(12, SensorType.COUNTER_TRAINS, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		CounterSensor sensor13 = new CounterSensor(13, SensorType.COUNTER_TRAINS, new Platform(2, PlatformType.CITY, new Station(1)));
		CounterSensor sensor14 = new CounterSensor(14, SensorType.COUNTER_TICKETS, new Platform(null, PlatformType.UNDEFINED, new Station(1)));
		CounterSensor sensor15 = new CounterSensor(15, SensorType.COUNTER_PEOPLE, new Platform(null, PlatformType.UNDEFINED, new Station(1)));
		// Sensor at train station 02
		TemperatureSensor sensor16 = new TemperatureSensor(16, new Platform(1, PlatformType.CITY, new Station(2)));
		TemperatureSensor sensor17 = new TemperatureSensor(17, new Platform(1, PlatformType.CITY, new Station(2)));
		TemperatureSensor sensor18 = new TemperatureSensor(18, new Platform(2, PlatformType.REGIONAL, new Station(2)));
		TemperatureSensor sensor19 = new TemperatureSensor(19, new Platform(2, PlatformType.REGIONAL, new Station(2)));
		TemperatureSensor sensor20 = new TemperatureSensor(20, new Platform(3, PlatformType.INTERNATIONAL, new Station(2)));
		TemperatureSensor sensor21 = new TemperatureSensor(21, new Platform(3, PlatformType.INTERNATIONAL, new Station(2)));
		LiftVibrationSensor sensor22 = new LiftVibrationSensor(22, new Platform(1, PlatformType.CITY, new Station(2)));
		LiftVibrationSensor sensor23 = new LiftVibrationSensor(23, new Platform(2, PlatformType.REGIONAL, new Station(2)));
		LiftVibrationSensor sensor24 = new LiftVibrationSensor(24, new Platform(3, PlatformType.INTERNATIONAL, new Station(2)));
		CounterSensor sensor25 = new CounterSensor(25, SensorType.COUNTER_PEOPLE, new Platform(1, PlatformType.CITY, new Station(2)));
		CounterSensor sensor26 = new CounterSensor(26, SensorType.COUNTER_PEOPLE, new Platform(2, PlatformType.REGIONAL, new Station(2)));
		CounterSensor sensor27 = new CounterSensor(27, SensorType.COUNTER_PEOPLE, new Platform(3, PlatformType.INTERNATIONAL, new Station(2)));
		CounterSensor sensor28 = new CounterSensor(28, SensorType.COUNTER_TRAINS, new Platform(1, PlatformType.CITY, new Station(2)));
		CounterSensor sensor29 = new CounterSensor(29, SensorType.COUNTER_TRAINS, new Platform(2, PlatformType.REGIONAL, new Station(2)));
		CounterSensor sensor30 = new CounterSensor(30, SensorType.COUNTER_TRAINS, new Platform(3, PlatformType.INTERNATIONAL, new Station(2)));
		CounterSensor sensor31 = new CounterSensor(31, SensorType.COUNTER_TICKETS, new Platform(null, PlatformType.UNDEFINED, new Station(2)));
		CounterSensor sensor32 = new CounterSensor(32, SensorType.COUNTER_PEOPLE, new Platform(null, PlatformType.UNDEFINED, new Station(2)));
		// @formatter:on

		DirectProvider dp = new DirectProvider();
		Topology topology = dp.newTopology();

		int qos = 0;
		boolean retain = false;
		MqttConfig config = new MqttConfig("tcp://127.0.0.1:1883", "TempMultipleSensorMqttApp");
		MqttStreams mqtt = new MqttStreams(topology, () -> config);

		// @formatter:off
		// train station 01
		TStream<String> sensor01Readings = topology.poll(sensor01, 1000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor02Readings = topology.poll(sensor02, 1000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor03Readings = topology.poll(sensor03, 1000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor04Readings = topology.poll(sensor04, 1000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor05Readings = topology.poll(sensor05, 1000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor06Readings = topology.poll(sensor06, 1000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor07Readings = topology.poll(sensor07, 2000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor08Readings = topology.poll(sensor08, 2000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor09Readings = topology.poll(sensor09, 2000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor10Readings = topology.poll(sensor10, 3000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor11Readings = topology.poll(sensor11, 3000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor12Readings = topology.poll(sensor12, 5000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor13Readings = topology.poll(sensor13, 5000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor14Readings = topology.poll(sensor14, 1000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor15Readings = topology.poll(sensor15, 1000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		// train station 02
		TStream<String> sensor16Readings = topology.poll(sensor16, 1000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor17Readings = topology.poll(sensor17, 1000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor18Readings = topology.poll(sensor18, 1000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor19Readings = topology.poll(sensor19, 1000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor20Readings = topology.poll(sensor20, 1000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor21Readings = topology.poll(sensor21, 1000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor22Readings = topology.poll(sensor22, 2000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor23Readings = topology.poll(sensor23, 2000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor24Readings = topology.poll(sensor24, 2000, TimeUnit.MILLISECONDS).map(new SensorDoubleMapper());
		TStream<String> sensor25Readings = topology.poll(sensor25, 3000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor26Readings = topology.poll(sensor26, 3000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor27Readings = topology.poll(sensor27, 5000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor28Readings = topology.poll(sensor28, 5000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor29Readings = topology.poll(sensor29, 1000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor30Readings = topology.poll(sensor30, 1000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor31Readings = topology.poll(sensor31, 1000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		TStream<String> sensor32Readings = topology.poll(sensor32, 1000, TimeUnit.MILLISECONDS).map(new SensorIntMapper());
		// @formatter:on

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
		TStream<String> tempReadingsStation02 = sensor16Readings
				.union(sensor17Readings)
				.union(sensor18Readings)
				.union(sensor19Readings)
				.union(sensor20Readings)
				.union(sensor21Readings)
				.union(sensor22Readings)
				.union(sensor23Readings)
				.union(sensor24Readings)
				.union(sensor25Readings)
				.union(sensor26Readings)
				.union(sensor27Readings)
				.union(sensor28Readings)
				.union(sensor29Readings)
				.union(sensor30Readings)
				.union(sensor31Readings)
				.union(sensor32Readings);
		
		// tempReadingsStation01.print();
		// tempReadingsStation02.print();
		// TSink<String> sink01 = mqtt.publish(tempReadingsStation01, "topic-station-01", qos, retain);
		// TSink<String> sink02 = mqtt.publish(tempReadingsStation02, "topic-station-02", qos, retain);
		// @formatter:on

		mqtt.publish(tempReadingsStation01, "topic-station-01", qos, retain);
		mqtt.publish(tempReadingsStation02, "topic-station-02", qos, retain);

		dp.submit(topology);
	}

	public static class SensorDoubleMapper implements Function<Tuple2<SensorKey, Double>, String> {

		private static final long serialVersionUID = -183900884352862695L;

		@Override
		public String apply(Tuple2<SensorKey, Double> value) {
			return value.f0.toString() + "|" + String.valueOf(value.f1);
		}
	}

	public static class SensorIntMapper implements Function<Tuple2<SensorKey, Integer>, String> {

		private static final long serialVersionUID = 5478136460473668449L;

		@Override
		public String apply(Tuple2<SensorKey, Integer> value) {
			return value.f0.toString() + "|" + String.valueOf(value.f1);
		}
	}
}