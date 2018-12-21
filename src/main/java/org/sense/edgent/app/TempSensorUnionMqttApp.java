package org.sense.edgent.app;

import java.util.concurrent.TimeUnit;

import org.apache.edgent.connectors.mqtt.MqttConfig;
import org.apache.edgent.connectors.mqtt.MqttStreams;
import org.apache.edgent.function.Function;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.types.Either;
import org.apache.flink.types.Either.Right;
import org.sense.sensor.TempEitherSensor;

/**
 * Open a terminal and type: 'mosquitto_sub -h 127.0.0.1 -t topic-edgent' to
 * receive values from the mqtt publisher
 * 
 * @author Felipe Oliveira Gutierrez
 *
 */
public class TempSensorUnionMqttApp {

	public TempSensorUnionMqttApp() {
		System.out.println("TempSensorUnionMqttApp Hello World!");
		TempEitherSensor sensor = new TempEitherSensor();
		DirectProvider dp = new DirectProvider();
		Topology topology = dp.newTopology();

		int qos = 0;
		boolean retain = false;
		MqttConfig config = new MqttConfig("tcp://127.0.0.1:1883", "TempSensorUnionMqttApp");
		MqttStreams mqtt = new MqttStreams(topology, () -> config);

		// data stream from sensor
		TStream<Either<String, Tuple2<Double, Double>>> tempReadings = topology.poll(sensor, 100,
				TimeUnit.MILLISECONDS);

		// data stream from parameters
		TStream<Either<String, Tuple2<Double, Double>>> parameters = mqtt.subscribe("topic-parameter", qos)
				.map(new Function<String, Either<String, Tuple2<Double, Double>>>() {

					@Override
					public Either<String, Tuple2<Double, Double>> apply(String value) {

						String[] array = value.split(",");
						double min = Double.parseDouble(array[0]);
						double max = Double.parseDouble(array[1]);
						Tuple2<Double, Double> t = new Tuple2<Double, Double>(min, max);

						return new Right<String, Tuple2<Double, Double>>(t);
					}
				});

		TStream<Either<String, Tuple2<Double, Double>>> filtered = tempReadings.union(parameters);

		// TSink<String> sink = mqtt.publish(filtered, "topic-edgent", qos, retain);

		dp.submit(topology);
	}
}
