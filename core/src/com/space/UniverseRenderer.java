package com.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.Vector3;
import com.space.universe.Universe;
import com.space.universe.solarsystem.CosmicObject;
import com.space.util.Assets;
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

	private PointLight light;

	private CosmicObject currentCosmicObject;
	private Vector3 cameraPosition;

	private boolean translate;
	private boolean lookAt;

	public UniverseRenderer(Hud hud, Assets assets) {
		environment = new Environment();
		universe = new Universe(assets);
		batch = new ModelBatch();
		this.hud = hud;

		cameraPosition = new Vector3();

		setupCamera();
		setupEnvironment();

		inputHandler = new InputHandler(camera, this, hud);
		Gdx.input.setInputProcessor(inputHandler);
	}

	public void render(float delta) {

		if (translate)
			camera.position.set(currentCosmicObject.getCameraPosition());

		if (lookAt)
			camera.lookAt(currentCosmicObject.getPosition());

		inputHandler.update(delta);
		camera.update();

		batch.begin(camera);
		universe.renderSky(batch, environment);
		batch.end();

		universe.renderOrbitPaths(camera);

		batch.begin(camera);
		universe.renderSun(batch, environment);
		universe.renderPlanetsAndMoons(batch, environment);
		universe.renderAsteroidBelt(batch, environment);
		batch.end();

	}

	/**
	 * Sets the main camera at the specified planet's camera position
	 * 
	 * @param index
	 *            the index of the planet array
	 */
	public void setCameraAtPlanet(int index) {
		currentCosmicObject = universe.getPlanets().get(index);
		hud.setCurrentPlanet(currentCosmicObject);
		camera.position.set(currentCosmicObject.getCameraPosition());
	}

	/**
	 * Sets the camera at the sun
	 */
	public void setCameraAtSun() {
		releaseCamera();
		currentCosmicObject = universe.getSun();
		hud.setCurrentPlanet(currentCosmicObject);
		camera.position.set(0, 0, 50);
	}

	/**
	 * Allows user to control camera if it is locked to a planet
	 */
	public void releaseCamera() {
		translate = false;
		hud.unlockCamera();
	}

	/**
	 * Locks the camera to a planet
	 */
	public void lockCamera() {
		translate = true;
		lookAt = true;
		hud.lockCamera();
		hud.lockLookAt();
	}

	public void toggleLookAtPlanet() {
		hud.toggleLookAt();
		lookAt = !lookAt;
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
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f));
	}

	public void dispose() {
		batch.dispose();
		universe.dispose();
	}

}
