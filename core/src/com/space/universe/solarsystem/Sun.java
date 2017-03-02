package com.space.universe.solarsystem;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

/**
 * Sun.
 */
public class Sun extends CelestialBody {

	private static final String NAME = "Sun";
	private static final float SCALE = 8;

	protected Model model;
	protected ModelInstance modelInstance;
	protected AssetManager assets;

	protected Vector3 position;
	protected boolean loading;

	public Sun() {
		super(NAME, SCALE, 0, 0, 0, 0);
		position = new Vector3(0, 0, 0);
		buildModel();
	}

	@Override
	public void render(ModelBatch batch, Environment environment) {
		if (loading && assets.update())
			doneLoading();

		if (modelInstance != null) {
			modelInstance.transform.rotate(0, 1, 0, 0.75f);
			batch.render(modelInstance);
		}
	}

	@Override
	public Vector3 getPosition() {
		return position;
	}

	@Override
	public float getScale() {
		return 333000;
	}

	protected void buildModel() {
		assets = new AssetManager();
		assets.load("data/" + NAME + ".g3dj", Model.class);
		loading = true;
	}

	@Override
	protected void doneLoading() {
		model = assets.get("data/" + NAME + ".g3dj", Model.class);
		modelInstance = new ModelInstance(model, position);
		modelInstance.transform.scl(8);
		loading = false;
	}

	@Override
	public void dispose() {
		model.dispose();
		assets.dispose();
	}

}
