package com.space.universe.solarsystem.planets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.space.universe.Universe;

/**
 * Planet.
 */
public class Planet {

	private final String NAME;
	private final float DIAMETER;
	private final float MASS;

	protected Model model;
	protected ModelInstance modelInstance;
	protected AssetManager assets;

	protected btRigidBody body;
	protected btSphereShape sphere;

	protected float startingPositionX;
	protected Vector3 position;
	protected boolean loading;

	public Planet(String name, float diameter, float distanceFromSun, float mass) {
		NAME = name;
		DIAMETER = diameter;
		MASS = mass;
		startingPositionX = Universe.AU * distanceFromSun;

		position = new Vector3(startingPositionX, 0, 0);

		sphere = new btSphereShape(50);
		body = new btRigidBody(MASS, null, sphere);
		body.activate();
		body.setAngularVelocity(new Vector3(0, 1, 0));

		buildModel();
	}

	public void render(ModelBatch batch, Environment environment) {
		if (loading && assets.update())
			doneLoading();

		if (modelInstance != null) {
			body.getWorldTransform(modelInstance.transform);
			batch.render(modelInstance, environment);
		}
	}

	// public void advanceOrbit(Vector3 position) {
	// this.position = position;
	// }

	public Vector3 getPosition() {
		return position;
	}

	public btRigidBody getBody() {
		return body;
	}

	protected void buildModel() {
		assets = new AssetManager();
		assets.load("data/" + NAME + ".g3dj", Model.class);
		loading = true;
	}

	protected void doneLoading() {
		model = assets.get("data/" + NAME + ".g3dj", Model.class);
		modelInstance = new ModelInstance(model, position);
		loading = false;
	}

	public void dispose() {
		model.dispose();
		assets.dispose();
	}

}
