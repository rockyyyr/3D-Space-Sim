package com.space.universe.solarsystem;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.space.util.Assets;

/**
 * NightSky.
 */
public class NightSky extends CosmicObject {

	private static final String NAME = "MilkyWay";
	private static final float SCALE = 1000;
	private static final float ROTATION = 0.0075f;

	protected Model model;
	protected ModelInstance modelInstance;

	protected Vector3 position;

	public NightSky() {
		super(NAME, SCALE, 0, 0, 0, 0);
		position = new Vector3(0, 0, 0);
	}

	@Override
	public void render(ModelBatch batch, Environment environment) {
		modelInstance.transform.rotate(0, 1, 0, ROTATION);
		batch.render(modelInstance);
	}

	@Override
	public void buildModel(Assets assets) {
		model = assets.get(NAME + ".g3dj");
		modelInstance = new ModelInstance(model, position);
		modelInstance.transform.scl(SCALE);
	}

	@Override
	public Vector3 getPosition() {
		return position;
	}
}
