package com.space.universe.solarsystem;

import java.io.IOException;
import java.util.Random;

import com.space.universe.Universe;
import com.space.util.AttributeReader;

/**
 * Moon.
 */
public class Moon extends CelestialBody {

	public static final float ORBIT_VELOCITY = 1f;

	private float[][] table;

	private float distanceFromHost;

	public Moon(String name, float scale, float distanceFromHost, float orbitalPeriod, float tilt, float lightDistanceFromPlanet, Planet host) {
		super(name, scale, (host.DISTANCE + (Universe.AU * distanceFromHost)) / Universe.AU, orbitalPeriod, tilt, lightDistanceFromPlanet);
		this.distanceFromHost = distanceFromHost;

		try {
			table = AttributeReader.getOrbitTable();
		} catch (IOException e) {
			e.printStackTrace();
		}

		setRandomPosition();
	}

	@Override
	public float getDistance() {
		return distanceFromHost * Universe.AU;
	}

	public void setRandomPosition() {
		Random rand = new Random();
		int pos = rand.nextInt(table.length - 1);

		float x = table[pos][0] * DISTANCE;
		float y = table[pos][1] * DISTANCE;
		float z = table[pos][2] * DISTANCE;

		setPosition(x, y, z);
	}

}
