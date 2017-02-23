package com.space.universe;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.space.universe.solarsystem.Sun;
import com.space.universe.solarsystem.planets.Earth;
import com.space.universe.solarsystem.planets.Planet;

/**
 * Universe.
 */
public class Universe {

	public static final float AU = 100;

	private Planet[] planets;
	private Sun sun;

	public Universe() {
		sun = new Sun();
		planets = new Planet[] {
				// sun,
				new Earth()
		};
	}

	public void render(ModelBatch batch, Environment environment) {
		sun.render(batch, environment);

		for (Planet planet : planets) {
			planet.render(batch, environment);
		}
	}

	private void advanceOrbit(Planet planet) {

	}

	public Planet[] getPlanets() {
		return planets;
	}

}
