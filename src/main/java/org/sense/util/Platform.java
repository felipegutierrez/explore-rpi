package org.sense.util;

public class Platform {

	private Integer id;
	private Station station;
	private PlatformType platformType;

	public Platform(Integer id, PlatformType platformType, Station station) {
		if (platformType == null) {
			System.out.println("Please set the platform type.");
		}
		this.id = id;
		this.station = station;
		this.platformType = platformType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public PlatformType getPlatformType() {
		return platformType;
	}

	public void setPlatformType(PlatformType platformType) {
		this.platformType = platformType;
	}

	@Override
	public String toString() {
		return id + "|" + platformType.getValue() + "|" + station;
	}
}
