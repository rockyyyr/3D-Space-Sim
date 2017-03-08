package com.space.universe.solarsystem;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

/**
 * NightSky.
 */
public class NightSky {

	private static final String NAME = "MilkyWay";
	private static final float SCALE = 1000;

	protected Model model;
	protected ModelInstance modelInstance;
	protected AssetManager assets;

	protected Vector3 position;
	protected boolean loading;

	public NightSky() {
		position = new Vector3(0, 0, 0);

	}

	public void render(ModelBatch batch, Environment environment) {
		if (loading && assets.update())
			doneLoading();

		if (modelInstance != null) {
			modelInstance.transform.rotate(0, 1, 0, 0.0075f);
			batch.render(modelInstance);
		}
	}

	public Vector3 getPosition() {
		return position;
	}

	public void buildModel(AssetManager assets) {
		this.assets = assets;
		assets.load("entities/" + NAME + ".g3dj", Model.class);
		loading = true;
	}

	protected void doneLoading() {
		model = assets.get("entities/" + NAME + ".g3dj", Model.class);
		modelInstance = new ModelInstance(model, position);
		modelInstance.transform.scl(SCALE);
		loading = false;
	}

	public void dispose() {
		// model.dispose();
	}

}
