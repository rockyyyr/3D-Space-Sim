package com.space.universe.solarsystem;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.space.universe.Universe;

/**
 * CelestialBody.
 */
public class CelestialBody {

	protected final String NAME;
	protected final float SCALE;
	protected float DISTANCE;
	protected final float ORBITAL_PERIOD;
	protected final float TILT;

	private PointLight light;

	private Model model;
	private ModelInstance modelInstance;
	private AssetManager assets;

	// Light position debugging
	Model lightModel;
	ModelBuilder mb;
	ModelInstance lightDebugger;

	protected Vector3 planetPosition;
	private Vector3 lightPosition;
	private Vector3 rotationVector;
	private float lightDistance;
	private float lightOrbitDifferential;

	protected boolean loading;

	public CelestialBody(String name, float scale, float distanceFromHost, float orbitalPeriod, float tilt, float lightDistanceFromPlanet) {
		NAME = name;
		SCALE = scale;
		DISTANCE = Universe.AU * distanceFromHost;
		ORBITAL_PERIOD = orbitalPeriod;
		TILT = tilt;

		lightDistance = DISTANCE - lightDistanceFromPlanet;
		lightOrbitDifferential = lightDistance / DISTANCE;

		planetPosition = new Vector3(DISTANCE, 0, 0);
		lightPosition = new Vector3(lightDistance, 0, 0);
		rotationVector = new Vector3(0, 1, 0);

		createLightDebugger();

		setupLight();
	}

	public void render(ModelBatch batch, Environment environment) {
		if (loading && assets.update())
			doneLoading();

		if (modelInstance != null) {
			light.setPosition(lightPosition);
			modelInstance.transform.setTranslation(planetPosition);
			modelInstance.transform.rotate(rotationVector, 1f);
			batch.render(modelInstance, environment);
		}

		// if (NAME.equals("Earth"))
		// System.out.println(planetPosition.x + "| " + planetPosition.y + "| " + planetPosition.z);

		// debugLights(batch);
	}

	public void buildModel(AssetManager assets) {
		this.assets = assets;
		assets.load("data/" + NAME + ".g3dj", Model.class);
		loading = true;
	}

	public void advanceOrbit(Vector3 vector) {
		planetPosition.add(vector);
		advanceLightOrbit(vector);
	}

	private void advanceLightOrbit(Vector3 vector) {
		lightPosition.add(vector.x * lightOrbitDifferential, 0, vector.z * lightOrbitDifferential);
	}

	protected void doneLoading() {
		model = assets.get("data/" + NAME + ".g3dj", Model.class);
		modelInstance = new ModelInstance(model, planetPosition);
		applyPlanetaryScaling(SCALE);
		applyPlanetaryTilt(TILT);
		loading = false;
	}

	private void applyPlanetaryTilt(float tilt) {
		modelInstance.transform.rotate(1, 0, 1, tilt);
	}

	private void applyPlanetaryScaling(float scale) {
		modelInstance.transform.scl(scale);
	}

	private void setupLight() {
		light = new PointLight();
		light.intensity = 50f;
		light.setColor(1, 1, 1, 0.1f);
	}

	public String getName() {
		return NAME;
	}

	public float getScale() {
		return SCALE;
	}

	public Vector3 getPosition() {
		return planetPosition;
	}

	public Vector3 getLightPosition() {
		return lightPosition;
	}

	public float getDistance() {
		return DISTANCE;
	}

	public float getLightDistance() {
		return lightDistance;
	}

	public PointLight getLight() {
		return light;
	}

	public float getX() {
		return planetPosition.x;
	}

	public float getY() {
		return planetPosition.y;
	}

	public float getZ() {
		return planetPosition.z;
	}

	public void setPosition(float x, float y, float z) {
		planetPosition.x = x;
		planetPosition.y = y;
		planetPosition.z = z;

	}

	public void setPosition(Vector3 vector) {

	}

	public void setRotationVector(float x, float y, float z) {
		// System.out.println(x + ", " + y + ", " + z);
		rotationVector.x = x;
		rotationVector.y = y;
		rotationVector.z = z;
	}

	private void createLightDebugger() {
		mb = new ModelBuilder();
		lightModel = mb.createBox(1, 1, 1, new Material(ColorAttribute.createDiffuse(Color.GREEN)), Usage.Position | Usage.Normal);
		lightDebugger = new ModelInstance(lightModel);
	}

	private void debugLights(ModelBatch batch) {
		lightDebugger.transform.setTranslation(lightPosition);
		batch.render(lightDebugger);
	}

	public void dispose() {
		model.dispose();
		assets.dispose();
	}
}
