package com.space.universe.solarsystem;

import java.util.Random;

import com.space.universe.Universe;
import com.space.util.OrbitTable;

/**
 * Moon.
 */
public class Moon extends OrbitingCosmicObject {

	private float[][] table;
	private float distanceFromHost;

	public Moon(String name, float scale, float distanceFromHost, Planet host) {
		super(name, scale, (host.DISTANCE + (Universe.AU * distanceFromHost)) / Universe.AU, host.getTilt(), 0);
		this.distanceFromHost = distanceFromHost;

		table = OrbitTable.getTable();

		setRandomPosition();
		setInclination(host.getTilt());
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
