package com.space.universe.solarsystem.planets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;

/**
 * Mars.
 */
public class Mars {

	private Environment environment;

	private Model model;
	private ModelInstance modelInstance;
	private AnimationController controller;
	private AssetManager assets;

	private Vector3 position;
	float x = 10, z = 0;
	private int obritLength = 200;

	private boolean loading;
	private boolean orbit;

	public Mars(Environment environment) {
		this.environment = environment;

		position = new Vector3(obritLength, 0, 0);

		buildModel();
		createController();
	}

	private void buildModel() {
		assets = new AssetManager();
		assets.load("data/Mars.g3dj", Model.class);
		loading = true;
	}

	private void doneLoading() {
		model = assets.get("data/Mars.g3dj", Model.class);
		modelInstance = new ModelInstance(model, position);
		loading = false;
	}

	private void createController() {
		controller = new AnimationController(modelInstance);
		// controller.setAnimation("Rotate", -1, new AnimationListener() {
		//
		// @Override
		// public void onEnd(AnimationDesc animation) {
		// }
		//
		// @Override
		// public void onLoop(AnimationDesc animation) {
		// }
		// });
	}

	public void render(ModelBatch batch) {
		if (loading && assets.update())
			doneLoading();

		if (x >= obritLength) {
			orbit = false;
		} else if (x <= -obritLength) {
			orbit = true;
		}

		if (!orbit) {
			x -= 1f;
			z -= 1f;
		} else if (orbit) {
			x += 1f;
			z += 1f;
		}

		if (modelInstance != null) {
			modelInstance.transform.rotate(0, 1, 0, -0.75f);
			// modelInstance.transform.setTranslation(x, 0, z);
			batch.render(modelInstance, environment);
		}
	}

	public ModelInstance getModel() {
		return modelInstance;
	}

}
