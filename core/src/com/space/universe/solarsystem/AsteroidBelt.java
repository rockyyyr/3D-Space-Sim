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
		for (int i = 0; i < 400; i++) {

			int pos = rand.nextInt(table.length - 1);
			float distance = rand.nextFloat() + 2;
			float scale = (float) rand.nextDouble() + rand.nextInt(2);

			CelestialBody asteroid = new CelestialBody(FILENAME + index, scale, distance, 0, 0, 0);

			asteroid.setPosition(table[pos][0] * distance, table[pos][1] * distance, table[pos][2] * distance);
			asteroid.setRotationVector(rand.nextInt(2), rand.nextInt(2), rand.nextInt(2), (float) rand.nextDouble() + rand.nextInt(5));

			// belt.add(asteroid);

			if (index < 5)
				index++;
			else
				index = 1;
		}

		belt.add(new CelestialBody("asteroids/Ceres", 2f, 2.5f, 0, 0, 0));
	}

	public ArrayList<CelestialBody> getAsteroidBelt() {
		return belt;
	}

}
