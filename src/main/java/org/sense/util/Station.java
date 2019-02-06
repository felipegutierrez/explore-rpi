package org.sense.util;

import java.util.Set;

public class Station {

	private Integer id;
	private Set<Platform> platforms;

	public Station(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<Platform> getPlatforms() {
		return platforms;
	}

	public void setPlatforms(Set<Platform> platforms) {
		this.platforms = platforms;
	}

	@Override
	public String toString() {
		return id + "|" + platforms;
	}

}
