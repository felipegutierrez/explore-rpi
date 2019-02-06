package org.sense.util;

public class SensorKey {

	private Integer id;
	private SensorType sensorType;
	private Platform platform;

	public SensorKey(Integer id, SensorType sensorType, Platform platform) {
		this.id = id;
		this.sensorType = sensorType;
		this.platform = platform;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SensorType getSensorType() {
		return sensorType;
	}

	public void setSensorType(SensorType sensorType) {
		this.sensorType = sensorType;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	@Override
	public String toString() {
		return "SensorKey [id=" + id + ", sensorType=" + sensorType + ", platform=" + platform + "]";
	}

}
