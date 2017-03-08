package com.space.universe;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.space.Hud;
import com.space.universe.solarsystem.AsteroidBelt;
import com.space.universe.solarsystem.CelestialBody;
import com.space.universe.solarsystem.Moon;
import com.space.universe.solarsystem.NightSky;
import com.space.universe.solarsystem.Planet;
import com.space.universe.solarsystem.Sun;
import com.space.util.AttributeReader;

/**
 * This class stores all entities in the simulation
 */
public class Universe {

	private AssetManager assets;

	public static final float AU = 250;
	public static float ORBITAL_VELOCITY = 0.2f;

	private ArrayList<Planet> planets;
	private AsteroidBelt asteroidBelt;
	private Sun sun;
	private NightSky sky;

	public boolean orbiting;
	public boolean visibleSky;
	public boolean passedZero;

	public Universe() {
		assets = new AssetManager();
		sun = new Sun();
		sky = new NightSky();
		asteroidBelt = new AsteroidBelt();

		orbiting = true;
		visibleSky = true;

		sun.buildModel(assets);
		sky.buildModel(assets);

		populatePlanetArray();
		buildModels();
	}

	public AssetManager getAssetManager() {
		return assets;
	}

	/**
	 * Renders entities and advances entity orbits
	 * 
	 * @param batch
	 *            ModelBatch used for rendering
	 * @param environment
	 */
	public void render(ModelBatch batch, Environment environment) {
		sun.render(batch, environment);

		if (visibleSky)
			sky.render(batch, environment);

		for (Planet planet : planets) {
			if (orbiting)
				advanceOrbit(sun, planet);
			planet.render(batch, environment);

			if (planet.hasMoon()) {
				for (Moon moon : planet.getMoons()) {
					if (orbiting) {
						advanceOrbit(planet, moon);
						advanceOrbit(planet, moon);
						advanceOrbit(planet, moon);
						advanceOrbit(planet, moon);
					}
					moon.render(batch, environment);
				}
			}
		}

		for (CelestialBody asteroid : asteroidBelt.getAsteroidBelt()) {
			if (orbiting)
				advanceOrbit(sun, asteroid);
			asteroid.render(batch, environment);
		}
	}

	public ArrayList<Planet> getPlanets() {
		return planets;
	}

	public CelestialBody getSun() {
		return sun;
	}

	public void toggleOrbits() {
		orbiting = !orbiting;
	}

	public void toggleSky() {
		visibleSky = !visibleSky;
	}

	public void accelerateOrbits() {
		ORBITAL_VELOCITY += 2;
	}

	public void decelerateOrbits() {
		if (ORBITAL_VELOCITY - 2 > 0)
			ORBITAL_VELOCITY -= 2;
	}

	private void advanceOrbit(CelestialBody host, CelestialBody orbital) {
		double angle = Math.atan2(orbital.getPosition().z - host.getPosition().z, orbital.getPosition().x - host.getPosition().x);

		angle += ORBITAL_VELOCITY / orbital.getDistance();

		if (orbital.getName().equals("Earth")) {
			checkForYearAdvancement(angle);
		}

		float forceX = ((orbital.getDistance() * (float) Math.cos(angle)) + host.getPosition().x);
		float forceZ = ((orbital.getDistance() * (float) Math.sin(angle)) + host.getPosition().z);

		orbital.advanceOrbit(new Vector3(forceX, 0, forceZ));
	}

	/*
	 * If angle passes zero, advance the year by one
	 */
	private void checkForYearAdvancement(double angle) {
		if (!passedZero && angle > 0) {
			Hud.CURRENT_YEAR++;
			passedZero = true;
		}

		if (passedZero && angle < 0)
			passedZero = false;
	}

	private void populatePlanetArray() {
		try {
			planets = AttributeReader.fetchPlanets();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Load assets for all bodies
	 */
	private void buildModels() {
		for (Planet planet : planets) {
			planet.buildModel(assets);

			if (planet.hasMoon()) {
				for (Moon moon : planet.getMoons()) {
					moon.buildModel(assets);
				}
			}
		}

		for (CelestialBody asteroid : asteroidBelt.getAsteroidBelt()) {
			asteroid.buildModel(assets);
		}
	}

	public void dispose() {
		assets.dispose();
		// sun.dispose();
		// sky.dispose();
		// for (Planet planet : planets)
		// planet.dispose();
	}

}
