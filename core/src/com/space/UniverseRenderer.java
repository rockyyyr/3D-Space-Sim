package com.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.space.universe.Universe;
import com.space.universe.solarsystem.CelestialBody;
import com.space.util.InputHandler;

/**
 * UniverseRenderer.
 */
public class UniverseRenderer {

	private Environment environment;
	private Universe universe;
	private ModelBatch batch;
	private Hud hud;

	private CelestialBody currentCelestialBody;

	private PerspectiveCamera camera;
	private InputHandler inputHandler;
	private CameraInputController cameraController;

	private PointLight light;

	private Vector3 cameraPosition;
	public boolean translate;
	public boolean lookAt;

	public UniverseRenderer(Hud hud) {
		environment = new Environment();
		universe = new Universe();
		batch = new ModelBatch();
		this.hud = hud;

		cameraPosition = new Vector3();

		setupCamera();
		setupEnvironment();
	}

	public void render() {
		hud.setCurrentPlanet(currentCelestialBody);

		if (translate)
			camera.position.set(cameraPosition);

		if (lookAt) {
			camera.lookAt(currentCelestialBody.getPosition());
			lookAt = false;
		}
		camera.update();

		batch.begin(camera);
		universe.render(batch, environment);
		batch.end();
	}

	private void setupCamera() {
		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		inputHandler = new InputHandler(camera, this, hud);
		Gdx.input.setInputProcessor(inputHandler);

		camera.near = 0.1f;
		camera.far = 30000000f;

		setCameraAtSun();
	}

	public void setCameraAtPlanet(int index) {
		lookAt = true;
		currentCelestialBody = universe.getPlanets().get(index);
		cameraPosition = currentCelestialBody.getLightPosition();
	}

	public void setCameraAtSun() {
		currentCelestialBody = universe.getSun();
		cameraPosition.set(0, 0, -100);
	}

	public Universe getUniverse() {
		return universe;
	}

	private void setupEnvironment() {
		light = new PointLight();
		light.intensity = 20000000;
		light.setColor(1, 1, 1, 1);
		light.setPosition(0, 0, 0);
		environment.add(light);
	}

	public void dispose() {
		batch.dispose();
		universe.dispose();
	}

}
