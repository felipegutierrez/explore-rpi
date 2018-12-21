package org.sense.edgent.app;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.edgent.analytics.sensors.Range;
import org.apache.edgent.analytics.sensors.Ranges;
import org.apache.edgent.connectors.mqtt.MqttConfig;
import org.apache.edgent.connectors.mqtt.MqttStreams;
import org.apache.edgent.function.Function;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import org.sense.sensor.SimulatedTemperatureSensor;

/**
 * Example based on
 * https://edgent.apache.org/recipes/recipe_adaptable_filter_range.html
 * 
 * Range examples: <code>[70.0..120.0]", "[80.0..130.0]", "[90.0..140.0]</code>
 * 
 * Publish using the command line: <code>
 * 'mosquitto_pub -h 127.0.0.1 -t topic-parameter -m "[70.0..120.0]"'
 * </code>
 * 
 * @author Felipe Oliveira Gutierrez
 *
 */
public class AdaptableFilterRangeApp {
	/**
	 * Optimal temperatures (in Fahrenheit)
	 */
	static Range<Double> DEFAULT_TEMP_RANGE = Ranges.valueOfDouble("[77.0..91.0]");
	static AtomicReference<Range<Double>> optimalTempRangeRef = new AtomicReference<>(DEFAULT_TEMP_RANGE);

	/**
	 * Polls a simulated temperature sensor to periodically obtain temperature
	 * readings (in Fahrenheit). Use a simple filter to determine when the
	 * temperature is out of the optimal range.
	 */
	public AdaptableFilterRangeApp() {
		DirectProvider dp = new DirectProvider();

		Topology top = dp.newTopology("AdaptableFilterRangeApp");

		// data stream from parameters
		int qos = 0;
		boolean retain = false;
		MqttConfig config = new MqttConfig("tcp://127.0.0.1:1883", "AdaptableFilterRangeApp");
		MqttStreams mqtt = new MqttStreams(top, () -> config);

		// Generate a stream of temperature sensor readings
		SimulatedTemperatureSensor tempSensor = new SimulatedTemperatureSensor();
		TStream<Double> temp = top.poll(tempSensor, 1, TimeUnit.SECONDS);

		// Simple filter: Perform analytics on sensor readings to detect when
		// the temperature is out of the optimal range and generate warnings
		TStream<Double> simpleFiltered = temp.filter(tuple -> optimalTempRangeRef.get().contains(tuple));
		// simpleFiltered.sink(tuple -> System.out.println("Temperature is out of range!
		// " + "It is " + tuple + "\u00b0F!"));

		mqtt.publish(simpleFiltered.map(tuple -> String.valueOf(tuple)), "topic-edgent", qos, retain);

		TStream<Range<Double>> setRangeCmds = mqtt.subscribe("topic-parameter", qos)
				.map(new Function<String, Range<Double>>() {

					@Override
					public Range<Double> apply(String value) {
						return Ranges.valueOfDouble(value);
					}
				});
		setRangeCmds.sink(tuple -> setOptimalTempRange(tuple));

		dp.submit(top);
	}

	static void setOptimalTempRange(Range<Double> range) {
		System.out.println("Using optimal temperature range: " + range);
		optimalTempRangeRef.set(range);
	}
}
