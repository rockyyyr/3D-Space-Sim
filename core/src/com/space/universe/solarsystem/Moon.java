package com.space.universe.solarsystem;

import com.space.universe.Universe;

/**
 * Moon.
 */
public class Moon extends CelestialBody {

	public static final float ORBIT_VELOCITY = 1f;

	private float distanceFromHost;

	public Moon(String name, float scale, float distanceFromHost, float orbitalPeriod, float tilt, float lightDistanceFromPlanet, Planet host) {
		super(name, scale, (host.DISTANCE + (Universe.AU * distanceFromHost)) / Universe.AU, orbitalPeriod, tilt, lightDistanceFromPlanet);
		this.distanceFromHost = distanceFromHost;
	}

	@Override
	public float getDistance() {
		return distanceFromHost * Universe.AU;

	}

}
