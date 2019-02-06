package org.sense.util;

public class Platform {

	private Integer id;
	private Station station;

	public Platform(Integer id, Station station) {
		this.id = id;
		this.station = station;
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

	@Override
	public String toString() {
		return id + "|" + station;
	}

}
