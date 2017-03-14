package com.space.universe;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.space.Hud;
import com.space.universe.solarsystem.Asteroid;
import com.space.universe.solarsystem.AsteroidBelt;
import com.space.universe.solarsystem.CosmicObject;
import com.space.universe.solarsystem.Moon;
import com.space.universe.solarsystem.NightSky;
import com.space.universe.solarsystem.OrbitingCosmicObject;
import com.space.universe.solarsystem.Planet;
import com.space.universe.solarsystem.Sun;
import com.space.util.Assets;
import com.space.util.AttributeReader;
import com.space.util.OrbitPath;

/**
 * This class stores all entities in the simulation
 */
public class Universe {

	public static final float AU = 250;
	public static float ORBITAL_VELOCITY = 0.2f;
	public static final float UNIVERSAL_SCALE = 1.5f;

	private ArrayList<Planet> planets;
	private AsteroidBelt asteroidBelt;
	private CosmicObject sun;
	private NightSky sky;

	private boolean orbiting;
	private boolean planetOrbitPathsVisible;
	private boolean moonOrbitPathsVisible;
	private boolean skyVisible;
	private boolean passedZero;

	public Universe(Assets assets) {
		sun = new Sun();
		sky = new NightSky();
		asteroidBelt = new AsteroidBelt();

		orbiting = true;
		skyVisible = true;

		populatePlanetArray();
		buildModels(assets);
	}

	public void renderSky(ModelBatch batch, Environment environment) {
		if (skyVisible)
			sky.render(batch, environment);
	}

	public void renderSun(ModelBatch batch, Environment environment) {
		sun.render(batch, environment);
	}

	public void renderPlanetsAndMoons(ModelBatch batch, Environment environment) {
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
	}

	public void renderAsteroidBelt(ModelBatch batch, Environment environment) {
		for (Asteroid asteroid : asteroidBelt.getAsteroidBelt()) {
			if (orbiting)
				advanceOrbit(sun, asteroid);
			asteroid.render(batch, environment);
		}
	}

	public void renderOrbitPaths(Camera camera) {
		for (Planet planet : getPlanets()) {
			if (planetOrbitPathsVisible) {
				OrbitPath.drawOrbitalPath(sun, planet, camera, 0);
			}
			if (moonOrbitPathsVisible) {
				if (planet.hasMoon()) {
					for (Moon moon : planet.getMoons()) {
						OrbitPath.drawOrbitalPath(planet, moon, camera, planet.getTilt());
					}
				}
			}
		}
	}

	public ArrayList<Planet> getPlanets() {
		return planets;
	}

	public CosmicObject getSun() {
		return sun;
	}

	public void toggleOrbits() {
		orbiting = !orbiting;
	}

	public void togglePlanetOrbitPaths() {
		planetOrbitPathsVisible = !planetOrbitPathsVisible;
	}

	public void toggleMoonOrbitPaths() {
		moonOrbitPathsVisible = !moonOrbitPathsVisible;
	}

	public void toggleSky() {
		skyVisible = !skyVisible;
	}

	public void accelerateOrbits() {
		ORBITAL_VELOCITY += 2;
	}

	public void decelerateOrbits() {
		if (ORBITAL_VELOCITY - 2 > 0)
			ORBITAL_VELOCITY -= 2;
	}

	private void advanceOrbit(CosmicObject host, OrbitingCosmicObject orbital) {
		double angle = Math.atan2(orbital.getPosition().z - host.getPosition().z, orbital.getPosition().x - host.getPosition().x);

		angle += ORBITAL_VELOCITY / orbital.getDistance();

		if (orbital.getName().equals("Earth")) {
			checkForYearAdvancement(angle);
		}

		float X = (orbital.getDistance() * (float) Math.cos(angle)) + host.getPosition().x;
		float Z = (orbital.getDistance() * (float) Math.sin(angle)) + host.getPosition().z;

		float Y = orbital.getDistance() * (float) (Math.toRadians(orbital.getInclination() / 2) * Math.cos(angle));

		orbital.advanceOrbit(new Vector3(X, Y, Z));
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
	private void buildModels(Assets assets) {
		sun.buildModel(assets);
		sky.buildModel(assets);

		for (Planet planet : planets) {
			planet.buildModel(assets);

			if (planet.hasMoon()) {
				for (Moon moon : planet.getMoons()) {
					moon.buildModel(assets);
				}
			}
		}

		for (Asteroid asteroid : asteroidBelt.getAsteroidBelt()) {
			asteroid.buildModel(assets);
		}
	}

	public void dispose() {
		// sun.dispose();
		// sky.dispose();
		// for (Planet planet : planets)
		// planet.dispose();
	}

}
