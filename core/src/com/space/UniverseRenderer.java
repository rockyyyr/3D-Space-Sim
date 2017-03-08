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
 * This class handles rendering of entities and contains the main camera and environment
 */
public class UniverseRenderer {

	private Environment environment;
	private Universe universe;
	private ModelBatch batch;
	private Hud hud;

	private PerspectiveCamera camera;
	private InputHandler inputHandler;
	private CameraInputController cameraController;

	private PointLight light;

	private CelestialBody currentCelestialBody;
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

		inputHandler = new InputHandler(camera, this, hud);
		Gdx.input.setInputProcessor(inputHandler);
	}

	public void render() {
		if (translate) {
			camera.position.set(currentCelestialBody.getCameraPosition());
			camera.lookAt(currentCelestialBody.getPosition());
		}

		camera.update();
		batch.begin(camera);
		universe.render(batch, environment);
		batch.end();
	}

	/**
	 * Sets the main camera at the specified planet's camera position
	 * 
	 * @param index
	 *            the index of the planet array
	 */
	public void setCameraAtPlanet(int index) {
		currentCelestialBody = universe.getPlanets().get(index);
		hud.setCurrentPlanet(currentCelestialBody);
		cameraPosition = currentCelestialBody.getCameraPosition();
	}

	/**
	 * Sets the camera at the sun
	 */
	public void setCameraAtSun() {
		currentCelestialBody = universe.getSun();
		hud.setCurrentPlanet(currentCelestialBody);
		camera.position.set(0, 0, 100);
	}

	/**
	 * @return the universe which contains all entities
	 */
	public Universe getUniverse() {
		return universe;
	}

	private void setupCamera() {
		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		camera.near = 0.1f;
		camera.far = 30000000f;

		setCameraAtSun();
	}

	private void setupEnvironment() {
		light = new PointLight();
		light.intensity = 60000000;
		light.setColor(1, 1, 1, 1);
		light.setPosition(0, 0, 0);
		environment.add(light);
	}

	public void dispose() {
		batch.dispose();
		universe.dispose();
	}

}
