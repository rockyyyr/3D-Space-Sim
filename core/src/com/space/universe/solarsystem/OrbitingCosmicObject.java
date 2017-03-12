package com.space.universe.solarsystem;

import java.util.Random;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.space.universe.Universe;
import com.space.util.Assets;
import com.space.util.OrbitTable;

/**
 * OrbitingCosmicObject.
 */
public class OrbitingCosmicObject extends CosmicObject {

	public final float TILT;

	private Random random;

	private float[][] table;
	private float cameraDistance;
	private float cameraOrbitDifferential;

	/**
	 * @param name
	 * @param scale
	 * @param distanceFromHost
	 * @param orbitalPeriod
	 * @param tilt
	 * @param cameraDistanceFromPlanet
	 */
	public OrbitingCosmicObject(String name, float scale, float distanceFromHost, float orbitalPeriod, float tilt, float cameraDistanceFromPlanet) {
		super(name, scale);

		TILT = tilt;
		DISTANCE = distanceFromHost * Universe.AU;

		random = new Random();

		cameraDistance = DISTANCE - cameraDistanceFromPlanet;
		cameraOrbitDifferential = cameraDistance / DISTANCE;

		table = OrbitTable.getTable();
		int pos = generateRandomIntForOrbitTable();

		position = setOrbitalPosition(pos, distanceFromHost);
		cameraPosition = setOrbitalPosition(pos, cameraDistanceFromPlanet);
	}

	@Override
	public void render(ModelBatch batch, Environment environment) {
		super.render(batch, environment);
		modelInstance.transform.setTranslation(position);
	}

	@Override
	public void buildModel(Assets assets) {
		super.buildModel(assets);
		applyScaling(SCALE * Universe.UNIVERSAL_SCALE);
		applyTilt(TILT);
	}

	public void setPosition(float x, float y, float z) {
		position.x = x;
		position.y = y;
		position.z = z;
	}

	@Override
	public float getDistance() {
		return DISTANCE;
	}

	/**
	 * @param vector
	 *            this objects next location on its orbital path
	 */
	public void advanceOrbit(Vector3 vector) {
		position.set(vector);
		advancecameraOrbit(vector);
	}

	private void advancecameraOrbit(Vector3 vector) {
		cameraPosition.set(vector.x * cameraOrbitDifferential, 0, vector.z * cameraOrbitDifferential);
	}

	private Vector3 setOrbitalPosition(int random, float distanceMultiplier) {
		float x = table[random][0] * distanceMultiplier;
		float y = table[random][1] * distanceMultiplier;
		float z = table[random][2] * distanceMultiplier;

		return new Vector3(x, y, z);
	}

	private int generateRandomIntForOrbitTable() {
		return random.nextInt(table.length - 1);
	}

	private void applyTilt(float tilt) {
		modelInstance.transform.rotate(1, 0, 1, tilt);
	}

}
