package com.space.universe.solarsystem;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.space.util.Assets;

/**
 * Sun.
 */
public class Sun extends CosmicObject {

	private static final String NAME = "Sun";
	private static final float SCALE = 18;
	private static final float ROTATION = -0.75f;
	private static final float SCALE_FOR_HUD = 333000;

	private Vector3 cameraPosition;

	public Sun() {
		super(NAME, SCALE);

		cameraPosition = new Vector3(0, 0, 50);
	}

	@Override
	public void render(ModelBatch batch, Environment environment) {
		modelInstance.transform.rotate(0, 1, 0, ROTATION);
		batch.render(modelInstance);
	}

	@Override
	public float getScale() {
		return SCALE_FOR_HUD;
	}

	@Override
	public void buildModel(Assets assets) {
		super.buildModel(assets);
		applyScaling(SCALE);
	}

	@Override
	public Vector3 getCameraPosition() {
		return cameraPosition;
	}

}
