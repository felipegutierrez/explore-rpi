package org.sense.edgent.app;

import java.util.concurrent.TimeUnit;

import org.apache.edgent.connectors.mqtt.MqttConfig;
import org.apache.edgent.connectors.mqtt.MqttStreams;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TSink;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import org.sense.sensor.TempSensorId;

/**
 * Open a terminal and type: 'mosquitto_sub -h 127.0.0.1 -t topic-edgent' to
 * receive values from the mqtt publisher
 * 
 * @author Felipe Oliveira Gutierrez
 *
 */
public class TempMultipleSensorMqttApp {

	public TempMultipleSensorMqttApp() {
		System.out.println("TempMultipleSensorMqttApp Hello World!");
		TempSensorId sensor01 = new TempSensorId(1);
		TempSensorId sensor02 = new TempSensorId(2);
		TempSensorId sensor03 = new TempSensorId(3);
		TempSensorId sensor04 = new TempSensorId(4);
		TempSensorId sensor05 = new TempSensorId(5);
		TempSensorId sensor06 = new TempSensorId(6);
		TempSensorId sensor07 = new TempSensorId(7);
		TempSensorId sensor08 = new TempSensorId(8);
		TempSensorId sensor09 = new TempSensorId(9);
		DirectProvider dp = new DirectProvider();
		Topology topology = dp.newTopology();

		int qos = 0;
		boolean retain = false;
		MqttConfig config = new MqttConfig("tcp://127.0.0.1:1883", "TempMultipleSensorMqttApp");
		MqttStreams mqtt = new MqttStreams(topology, () -> config);

		// receive values from 5 different sources
		TStream<String> temp01Readings = topology.poll(sensor01, 750, TimeUnit.MILLISECONDS).map(p -> {
			return String.valueOf(p.f0) + "|" + String.valueOf(p.f1);
		});
		TStream<String> temp02Readings = topology.poll(sensor02, 1000, TimeUnit.MILLISECONDS).map(p -> {
			return String.valueOf(p.f0) + "|" + String.valueOf(p.f1);
		});
		TStream<String> temp03Readings = topology.poll(sensor03, 1000, TimeUnit.MILLISECONDS).map(p -> {
			return String.valueOf(p.f0) + "|" + String.valueOf(p.f1);
		});
		TStream<String> temp04Readings = topology.poll(sensor04, 2900, TimeUnit.MILLISECONDS).map(p -> {
			return String.valueOf(p.f0) + "|" + String.valueOf(p.f1);
		});
		TStream<String> temp05Readings = topology.poll(sensor05, 3000, TimeUnit.MILLISECONDS).map(p -> {
			return String.valueOf(p.f0) + "|" + String.valueOf(p.f1);
		});
		TStream<String> temp06Readings = topology.poll(sensor06, 3500, TimeUnit.MILLISECONDS).map(p -> {
			return String.valueOf(p.f0) + "|" + String.valueOf(p.f1);
		});
		TStream<String> temp07Readings = topology.poll(sensor07, 5500, TimeUnit.MILLISECONDS).map(p -> {
			return String.valueOf(p.f0) + "|" + String.valueOf(p.f1);
		});
		TStream<String> temp08Readings = topology.poll(sensor08, 6000, TimeUnit.MILLISECONDS).map(p -> {
			return String.valueOf(p.f0) + "|" + String.valueOf(p.f1);
		});
		TStream<String> temp09Readings = topology.poll(sensor09, 7000, TimeUnit.MILLISECONDS).map(p -> {
			return String.valueOf(p.f0) + "|" + String.valueOf(p.f1);
		});

		TStream<String> tempReadingsGroup01 = temp01Readings.union(temp02Readings).union(temp03Readings);
		TStream<String> tempReadingsGroup02 = temp04Readings.union(temp05Readings).union(temp06Readings);
		TStream<String> tempReadingsGroup03 = temp07Readings.union(temp08Readings).union(temp09Readings);

		TSink<String> sink = mqtt.publish(tempReadingsGroup01, "topic-edgent-01", qos, retain);
		mqtt.publish(tempReadingsGroup02, "topic-edgent-02", qos, retain);
		mqtt.publish(tempReadingsGroup03, "topic-edgent-03", qos, retain);

		dp.submit(topology);
	}
}
