package com.space.universe.solarsystem;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.space.util.Assets;

/**
 * NightSky.
 */
public class NightSky extends CosmicObject {

	private static final String NAME = "MilkyWay";
	private static final float SCALE = 1000;
	private static final float ROTATION = 0.0075f;

	public NightSky() {
		super(NAME, SCALE);
	}

	@Override
	public void render(ModelBatch batch, Environment environment) {
		modelInstance.transform.rotate(0, 1, 0, ROTATION);
		batch.render(modelInstance);
	}

	@Override
	public void buildModel(Assets assets) {
		super.buildModel(assets);
		applyScaling(SCALE);
	}
}
