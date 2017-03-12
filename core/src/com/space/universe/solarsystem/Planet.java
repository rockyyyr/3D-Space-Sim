package com.space.universe.solarsystem;

import java.util.ArrayList;

import com.space.util.AttributeReader;
import com.space.util.OrbitPath;

/**
 * Planet.
 */
public class Planet extends OrbitingCosmicObject {

	private boolean hasMoon;
	private ArrayList<Moon> moons;
	private OrbitPath orbitPath;

	public Planet(String name, float scale, float distanceFromSun, float orbitalPeriod, float tilt, float lightDistanceFromPlanet, boolean hasMoon) {
		super(name, scale, distanceFromSun, orbitalPeriod, tilt, lightDistanceFromPlanet);

		this.hasMoon = hasMoon;
		moons = new ArrayList<Moon>();

		loadMoonsIfPresent();
	}

	public ArrayList<Moon> getMoons() {
		return moons;
	}

	public boolean hasMoon() {
		return hasMoon;
	}

	private void loadMoonsIfPresent() {
		if (hasMoon) {
			try {
				moons = AttributeReader.fetchMoon(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
