package com.space.universe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.space.universe.solarsystem.planets.Planet;

/**
 * UniverseRenderer.
 */
public class UniverseRenderer {

	private btDynamicsWorld world;
	private btConstraintSolver constraintSolver;
	private btCollisionDispatcher dispatcher;
	private btDbvtBroadphase broadphase;
	private btDefaultCollisionConfiguration collisionConfig;

	private Environment environment;
	private Universe universe;
	private ModelBatch batch;

	private PerspectiveCamera camera;
	private CameraInputController cameraController;

	public UniverseRenderer() {
		environment = new Environment();
		universe = new Universe();
		batch = new ModelBatch();

		setupDynamicsWorld();
		setupCamera();
		setupEnvironment();
	}

	public void render(float delta) {
		camera.update();
		world.stepSimulation(delta, 5, 1f / 60f);

		batch.begin(camera);
		universe.render(batch, environment);
		batch.end();
	}

	private void setupDynamicsWorld() {
		collisionConfig = new btDefaultCollisionConfiguration();
		dispatcher = new btCollisionDispatcher(collisionConfig);
		broadphase = new btDbvtBroadphase();
		constraintSolver = new btSequentialImpulseConstraintSolver();

		world = new btDiscreteDynamicsWorld(dispatcher, broadphase, constraintSolver, collisionConfig);
		world.setGravity(new Vector3(0, 0, 0));

		addRigidBodiesToWorld();
	}

	private void addRigidBodiesToWorld() {
		for (Planet planet : universe.getPlanets())
			world.addRigidBody(planet.getBody());
	}

	private void setupCamera() {
		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cameraController = new CameraInputController(camera);
		Gdx.input.setInputProcessor(cameraController);

		camera.position.set(0, 0, 5);
		camera.lookAt(0, 0, 0);
		camera.near = 0.1f;
		camera.far = 300f;
	}

	private void setupEnvironment() {
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.1f, 0.1f, 0.1f, 0.1f));
		environment.add(new PointLight().setPosition(0, 0, 0).setColor(1f, 1f, 1f, 0.2f).setIntensity(7000));
	}

	public void dispose() {
		batch.dispose();
	}

}
