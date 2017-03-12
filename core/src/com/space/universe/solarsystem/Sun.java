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
	private static final float ACTUAL_SCALE_FOR_HUD = 333000;

	protected Vector3 position;

	public Sun() {
		super(NAME, SCALE);
	}

	@Override
	public void render(ModelBatch batch, Environment environment) {
		modelInstance.transform.rotate(0, 1, 0, ROTATION);
		batch.render(modelInstance);
	}

	@Override
	public float getScale() {
		return ACTUAL_SCALE_FOR_HUD;
	}

	@Override
	public void buildModel(Assets assets) {
		super.buildModel(assets);
		applyScaling(SCALE);
	}

}
