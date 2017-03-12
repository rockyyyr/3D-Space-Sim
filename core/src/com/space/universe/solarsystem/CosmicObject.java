package com.space.universe.solarsystem;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.space.util.Assets;

public class CosmicObject {

	public static final String EXTENSION = ".g3dj";

	protected final String NAME;
	protected final float SCALE;
	protected float DISTANCE;
	protected float ROTATION = 1f;

	protected Model model;
	protected ModelInstance modelInstance;

	protected Vector3 position;
	protected Vector3 cameraPosition;
	protected Vector3 rotationVector;
	protected float rotationVelocity;

	public CosmicObject(String name, float scale) {
		NAME = name;
		SCALE = scale;

		position = new Vector3();

		rotationVector = new Vector3(0, 1, 0);
		rotationVelocity = ROTATION;
	}

	public void render(ModelBatch batch, Environment environment) {
		modelInstance.transform.rotate(rotationVector, rotationVelocity);
		batch.render(modelInstance, environment);
	}

	public void buildModel(Assets assets) {
		model = assets.get(NAME + EXTENSION);
		modelInstance = new ModelInstance(model, position);
	}

	protected void applyScaling(float scale) {
		modelInstance.transform.scl(scale);
	}

	public String getName() {
		return NAME;
	}

	public float getScale() {
		return SCALE;
	}

	public Vector3 getPosition() {
		return position;
	}

	public Vector3 getCameraPosition() {
		return cameraPosition;
	}

	public float getDistance() {
		return DISTANCE;
	}

	/**
	 * @return position.x
	 */
	public float getX() {
		return position.x;
	}

	/**
	 * @return position.y
	 */
	public float getY() {
		return position.y;
	}

	/**
	 * @return position.z
	 */
	public float getZ() {
		return position.z;
	}

	public void dispose() {
		model.dispose();
	}
}
