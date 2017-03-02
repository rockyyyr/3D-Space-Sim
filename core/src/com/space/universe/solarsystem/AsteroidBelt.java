package com.space.universe.solarsystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.space.util.AttributeReader;

/**
 * AsteroidBelt.
 */
public class AsteroidBelt {

	public static final String FILENAME = "asteroids/Asteroid";

	private ArrayList<CelestialBody> belt;
	private float[][] table;
	private Random rand;

	public AsteroidBelt() {
		belt = new ArrayList<CelestialBody>();
		rand = new Random();

		try {
			table = AttributeReader.getOrbitTable();
		} catch (IOException e) {
			e.printStackTrace();
		}

		buildBelt();
	}

	private void buildBelt() {
		int index = 1;
		for (int i = 0; i < 100; i++) {
			int pos = rand.nextInt(table.length - 1);
			float dis = rand.nextFloat() + 2;
			CelestialBody asteroid = new CelestialBody(FILENAME + index, (float) rand.nextDouble() + rand.nextInt(2), 0, 0, 0, 0);
			asteroid.setPosition(table[pos][0] * dis, table[pos][1] * dis, table[pos][2] * dis);
			asteroid.setRotationVector(rand.nextInt(2), rand.nextInt(2), rand.nextInt(2));
			belt.add(asteroid);

			if (index < 5)
				index++;
			else
				index = 1;
		}
	}

	public ArrayList<CelestialBody> getAsteroidBelt() {
		return belt;
	}

}
