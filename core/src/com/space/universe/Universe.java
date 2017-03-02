package com.space.universe;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.space.universe.solarsystem.AsteroidBelt;
import com.space.universe.solarsystem.CelestialBody;
import com.space.universe.solarsystem.Moon;
import com.space.universe.solarsystem.NightSky;
import com.space.universe.solarsystem.Planet;
import com.space.universe.solarsystem.Sun;
import com.space.util.AttributeReader;

/**
 * Universe.
 */
public class Universe {

	public static boolean LOADED = false;

	private AssetManager assets;

	public static final String PRINT = "%-16s%-10.2f%5.2f";
	public static final float AU = 100;
	public static final float ORBIT_VELOCITY = 0.25f;

	private ArrayList<Planet> planets;
	private AsteroidBelt asteroidBelt;
	private Sun sun;
	private NightSky sky;

	public boolean orbiting;
	public boolean visibleSky;

	public Universe() {
		assets = new AssetManager();
		sun = new Sun();
		sky = new NightSky();
		asteroidBelt = new AsteroidBelt();

		orbiting = true;
		visibleSky = true;

		sun.buildModel(assets);

		populatePlanetArray();
		buildModels();

		LOADED = true;
		System.out.println("loaded");
	}

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

	private void advanceOrbit(CelestialBody host, CelestialBody orbital) {
		Vector3 hostPos = new Vector3(host.getPosition());
		Vector3 orbitalPos = new Vector3(orbital.getPosition());
		Vector3 posDiff = hostPos.sub(orbitalPos);

		double angle = Math.atan2(posDiff.z, posDiff.x);

		float forceX = ORBIT_VELOCITY * (float) Math.sin(angle);
		float forceZ = ORBIT_VELOCITY * (float) Math.cos(angle);

		orbital.advanceOrbit(new Vector3(-forceX, 0, forceZ));
	}

	private void populatePlanetArray() {
		try {
			planets = AttributeReader.fetchPlanets();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	public void dispose() {
		assets.dispose();
		sun.dispose();
		sky.dispose();
		for (Planet planet : planets)
			planet.dispose();
	}

}
