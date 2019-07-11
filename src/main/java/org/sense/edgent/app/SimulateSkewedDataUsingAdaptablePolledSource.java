package org.sense.edgent.app;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.edgent.connectors.mqtt.MqttConfig;
import org.apache.edgent.connectors.mqtt.MqttStreams;
import org.apache.edgent.execution.mbeans.PeriodMXBean;
import org.apache.edgent.execution.services.ControlService;
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

import com.google.gson.JsonObject;

/**
 * A recipe for a polled source stream with an adaptable poll period.
 * 
 * This is a source code from the original Apache Edgent web-site:
 * https://edgent.incubator.apache.org/recipes/recipe_adaptable_polling_source.html
 */
public class SimulateSkewedDataUsingAdaptablePolledSource {
	private final String TOPIC_STATION_01_PLAT_01_TICKETS = "topic-station-01-plat-01-tickets";
	private final String TOPIC_STATION_01_PLAT_02_TICKETS = "topic-station-01-plat-02-tickets";
	private String ipAddress = "127.0.0.1";
	private String port = "1883";
	private static final int SHORT_POLLING_MILLISECONDS = 10;
	private static final int LONG_POLLING_MILLISECONDS = 1000;
	private static final int INTERVAL_SECONDS = 120;

	private void disclaimer() {
		// @formatter:off
		System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t " + TOPIC_STATION_01_PLAT_01_TICKETS + "' ");
		System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t " + TOPIC_STATION_01_PLAT_02_TICKETS + "' ");
		System.out.println("Open a terminal and type: 'mosquitto_sub -h " + ipAddress + " -t \'#\'");
		// @formatter:on
	}

	public SimulateSkewedDataUsingAdaptablePolledSource() {
		this("127.0.0.1", "1883");
	}

	/**
	 * Poll a temperature sensor to periodically obtain temperature readings.
	 * Respond to a simulated command stream to change the poll period.
	 */
	public SimulateSkewedDataUsingAdaptablePolledSource(String ipAddress, String port) {
		disclaimer();
		this.ipAddress = ipAddress;
		this.port = port;
		int qos = 0;
		boolean retain = false;
		MqttConfig config = new MqttConfig("tcp://" + this.ipAddress + ":" + this.port,
				"SimulateSkewedDataUsingAdaptablePolledSource");

		// @formatter:off
		DirectProvider dp = new DirectProvider();
		Topology top = dp.newTopology("TemperatureSensor");
		MqttStreams mqtt = new MqttStreams(top, () -> config);

		// Generate a polled temperature sensor stream and set its alias
		// Sensor at train station 01 ------sensorID, sensorType,              platformId, platformType,      stationId,       TICKET-ID_and_CITY
		Sensor sensor01Tickets = new Sensor(1, SensorType.TICKET, new Platform(1, PlatformType.INTERNATIONAL, new Station(1)));
		Sensor sensor02Tickets = new Sensor(2, SensorType.TICKET, new Platform(2, PlatformType.INTERNATIONAL, new Station(1)));

		TStream<Tuple4<SensorKey, Long, Integer, String>> sensor01Readings = top
				.poll(sensor01Tickets, LONG_POLLING_MILLISECONDS, TimeUnit.MILLISECONDS).alias("sensor01Readings").tag("sensor01Readings");
		TStream<Tuple4<SensorKey, Long, Integer, String>> sensor02Readings = top
				.poll(sensor02Tickets, LONG_POLLING_MILLISECONDS, TimeUnit.MILLISECONDS).alias("sensor02Readings").tag("sensor02Readings");

		// Report the time each temperature reading arrives and the value
		// sensor01Readings.peek(tuple -> System.out.println(new Date() + " temp=" + tuple));
		// sensor02Readings.peek(tuple -> System.out.println(new Date() + " temp=" + tuple));
		mqtt.publish(sensor01Readings.map(new SensorIntMapper()), TOPIC_STATION_01_PLAT_01_TICKETS, qos, retain);
		mqtt.publish(sensor02Readings.map(new SensorIntMapper()), TOPIC_STATION_01_PLAT_02_TICKETS, qos, retain);

		// Generate a simulated "set poll period" command stream
		TStream<JsonObject> cmds = simulatedSetPollPeriodCmds(top);

		// Process the commands to change the poll period
		cmds.sink(json -> setPollPeriod(sensor01Readings, json.getAsJsonPrimitive("period").getAsLong(),
				TimeUnit.valueOf(json.getAsJsonPrimitive("unit").getAsString())));

		dp.submit(top);
		// @formatter:on
	}

	public static class SensorIntMapper implements Function<Tuple4<SensorKey, Long, Integer, String>, String> {
		private static final long serialVersionUID = -9089949423265465616L;

		@Override
		public String apply(Tuple4<SensorKey, Long, Integer, String> value) {
			return value.f0.toString() + "|" + String.valueOf(value.f1) + "|" + String.valueOf(value.f2) + "|"
					+ String.valueOf(value.f3);
		}
	}

	static <T> void setPollPeriod(TStream<T> pollStream, long period, TimeUnit unit) {
		// get the topology's runtime ControlService service
		ControlService cs = pollStream.topology().getRuntimeServiceSupplier().get().getService(ControlService.class);

		// using the the stream's alias, get its PeriodMXBean control
		PeriodMXBean control = cs.getControl(TStream.TYPE, pollStream.getAlias(), PeriodMXBean.class);

		// change the poll period using the control
		System.out.println("Setting period=" + period + " " + unit + " stream=" + pollStream);
		control.setPeriod(period, unit);
	}

	static TStream<JsonObject> simulatedSetPollPeriodCmds(Topology top) {
		AtomicInteger lastPeriod = new AtomicInteger(1);
		TStream<JsonObject> cmds = top.poll(() -> {
			// toggle between 1 and 10 sec period
			int newPeriod = lastPeriod.get() == SHORT_POLLING_MILLISECONDS ? LONG_POLLING_MILLISECONDS
					: SHORT_POLLING_MILLISECONDS;
			lastPeriod.set(newPeriod);
			JsonObject jo = new JsonObject();
			jo.addProperty("period", newPeriod);
			jo.addProperty("unit", TimeUnit.MILLISECONDS.toString());
			return jo;
		}, INTERVAL_SECONDS, TimeUnit.SECONDS).tag("cmds");
		return cmds;
	}

	public static void main(String[] args) throws Exception {
		new SimulateSkewedDataUsingAdaptablePolledSource();
	}
}
