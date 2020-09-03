package org.sense.edgent.app;

import java.util.concurrent.TimeUnit;

import org.apache.edgent.connectors.mqtt.MqttConfig;
import org.apache.edgent.connectors.mqtt.MqttStreams;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import org.apache.flink.api.java.tuple.Tuple4;
import org.sense.edgent.app.SimulateSkewedDataUsingAdaptablePolledSource.SensorIntMapper;
import org.sense.sensor.Sensor;
import org.sense.util.Platform;
import org.sense.util.PlatformType;
import org.sense.util.SensorKey;
import org.sense.util.SensorType;
import org.sense.util.Station;

import com.google.gson.JsonObject;

public class ValenciaItemPublisher {
	private final String VALENCIA_ITEM_TRAFFIC_JAM = "valencia-item-traffic-jam";
	private String ipAddress = "127.0.0.1";
	private String port = "1883";

	public ValenciaItemPublisher() {
		this("127.0.0.1", "1883");
	}

	public ValenciaItemPublisher(String ipAddress, String port) {
		disclaimer();
		this.ipAddress = ipAddress;
		this.port = port;
		int qos = 0;
		boolean retain = false;
		MqttConfig config = new MqttConfig("tcp://" + this.ipAddress + ":" + this.port, "ValenciaItemPublisher");

		// @formatter:off
		DirectProvider dp = new DirectProvider();
		Topology top = dp.newTopology("TemperatureSensor");
		MqttStreams mqtt = new MqttStreams(top, () -> config);

		// Generate a polled temperature sensor stream and set its alias
		// Sensor at train station 01 ------sensorID, sensorType,              platformId, platformType,      stationId,       TICKET-ID_and_CITY
		Sensor valenciaItemTrafficSensors = new Sensor(1, SensorType.TICKET, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		

		TStream<Tuple4<SensorKey, Long, Integer, String>> valenciaItemTrafficReadings = top
				.poll(valenciaItemTrafficSensors, 1000, TimeUnit.MILLISECONDS).alias("valenciaItemTraffic").tag("valenciaItemTraffic");

		// Report the time each temperature reading arrives and the value
		mqtt.publish(valenciaItemTrafficReadings.map(new SensorIntMapper()), VALENCIA_ITEM_TRAFFIC_JAM, qos, retain);

		// Generate a simulated "set poll period" command stream
		// TStream<JsonObject> cmds = simulatedSetPollPeriodCmds(top);

		// Process the commands to change the poll period
		// cmds.sink(json -> setPollPeriod(valenciaItemTrafficReadings, json.getAsJsonPrimitive("period").getAsLong(),
		// TimeUnit.valueOf(json.getAsJsonPrimitive("unit").getAsString())));

		dp.submit(top);
		// @formatter:on
	}

	private void disclaimer() {
		// @formatter:off
		System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t " + VALENCIA_ITEM_TRAFFIC_JAM + "' ");
		System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t \'#\'");
		// @formatter:on
	}
}
