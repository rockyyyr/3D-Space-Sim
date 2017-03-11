package com.space.universe.solarsystem;

import java.util.ArrayList;
import java.util.Random;

import com.space.util.OrbitTable;

/**
 * AsteroidBelt.
 */
public class AsteroidBelt {

	public static final String FILENAME = "asteroids/Asteroid";
	public static final int NUM_OF_ASTEROIDS = 125;

	private ArrayList<CosmicObject> belt;
	private float[][] table;
	private Random rand;

	public AsteroidBelt() {
		belt = new ArrayList<CosmicObject>();
		rand = new Random();

		table = OrbitTable.getTable();

		buildBelt();
	}

	private void buildBelt() {
		int index = 1;
		for (int i = 0; i < NUM_OF_ASTEROIDS; i++) {

			int pos = rand.nextInt(table.length - 1);
			float distance = rand.nextFloat() + 2;
			float scale = (float) rand.nextDouble() + rand.nextInt(2);

			CosmicObject asteroid = new CosmicObject(FILENAME + index, scale, distance, 0, 0, 0);

			asteroid.setPosition(table[pos][0] * distance, table[pos][1] * distance, table[pos][2] * distance);
			asteroid.setRotationVector(rand.nextInt(2), rand.nextInt(2), rand.nextInt(2), (float) rand.nextDouble() + rand.nextInt(5));

			belt.add(asteroid);

			if (index < 5)
				index++;
			else
				index = 1;
		}

		belt.add(new CosmicObject("asteroids/Ceres", 2f, 2.5f, 0, 0, 0));
	}

	public ArrayList<CosmicObject> getAsteroidBelt() {
		return belt;
	}

}
