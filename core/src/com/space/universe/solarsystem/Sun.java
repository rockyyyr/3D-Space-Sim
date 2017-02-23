package com.space.universe.solarsystem;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.space.universe.solarsystem.planets.Planet;

/**
 * Sun.
 */
public class Sun extends Planet {

	private static final String NAME = "Sun";
	private static final float DIAMETER = 1;
	private static final float DISTANCE = 0;
	private static float MASS = 1;

	public Sun() {
		super(NAME, DIAMETER, DISTANCE, MASS);
	}

	@Override
	public void render(ModelBatch batch, Environment environment) {
		if (loading && assets.update())
			doneLoading();

		if (modelInstance != null) {
			body.getWorldTransform(modelInstance.transform);
			batch.render(modelInstance);
		}
	}

	// @Override
	// public void advanceOrbit(Vector3 position) {
	// }

}
