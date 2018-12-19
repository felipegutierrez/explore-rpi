package org.sense.edgent.app;

import java.util.concurrent.TimeUnit;

import org.apache.edgent.connectors.mqtt.MqttConfig;
import org.apache.edgent.connectors.mqtt.MqttStreams;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TSink;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import org.sense.sensor.TempStringSensor;

/**
 * Open a terminal and type: 'mosquitto_sub -h 127.0.0.1 -t topic-edgent' to
 * receive values from the mqtt publisher
 * 
 * @author Felipe Oliveira Gutierrez
 *
 */
public class TempSensorMqttApp {

	public TempSensorMqttApp() {
		System.out.println("TempSensorMqttApp Hello World!");
		TempStringSensor sensor = new TempStringSensor();
		DirectProvider dp = new DirectProvider();
		Topology topology = dp.newTopology();

		int qos = 0;
		boolean retain = false;
		MqttConfig config = new MqttConfig("tcp://127.0.0.1:1883", "TempSensorMqttApp");
		MqttStreams mqtt = new MqttStreams(topology, () -> config);

		TStream<String> tempReadings = topology.poll(sensor, 100, TimeUnit.MILLISECONDS);

		TSink<String> sink = mqtt.publish(tempReadings, "topic-edgent", qos, retain);

		dp.submit(topology);
	}
}
