package com.space.universe.solarsystem;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.space.universe.Universe;
import com.space.util.Assets;
import com.space.util.OrbitTable;

public class CosmicObject {

	public static final String EXTENSION = ".g3dj";

	protected final String NAME;
	protected final float SCALE;
	protected float DISTANCE;
	protected final float ORBITAL_PERIOD;
	protected final float TILT;

	protected Model model;
	protected ModelInstance modelInstance;

	// Light position debugging
	Model cameraModel;
	ModelBuilder mb;
	ModelInstance cameraDebugger;

	protected Vector3 planetPosition;
	private Vector3 cameraPosition;
	private Vector3 rotationVector;
	private float rotationVelocity;
	private float cameraDistance;
	private float cameraOrbitDifferential;
	private float[][] table;
	private Random random;

	protected boolean loading;

	public CosmicObject(String name, float scale, float distanceFromHost, float orbitalPeriod, float tilt, float cameraDistanceFromPlanet) {
		NAME = name;
		SCALE = scale;
		DISTANCE = Universe.AU * distanceFromHost;
		ORBITAL_PERIOD = orbitalPeriod;
		TILT = tilt;
		random = new Random();

		cameraDistance = DISTANCE - cameraDistanceFromPlanet;
		cameraOrbitDifferential = cameraDistance / DISTANCE;

		table = OrbitTable.getTable();
		int pos = generateRandomIntForOrbitTable();

		planetPosition = setOrbitalPosition(pos, distanceFromHost);
		cameraPosition = setOrbitalPosition(pos, cameraDistanceFromPlanet);

		rotationVector = new Vector3(0, 1, 0);
		rotationVelocity = 1f;

		createcameraDebugger();
	}

	public void render(ModelBatch batch, Environment environment) {
		modelInstance.transform.setTranslation(planetPosition);
		modelInstance.transform.rotate(rotationVector, rotationVelocity);
		batch.render(modelInstance, environment);

		// debugCameraPositions(batch);

	}

	public void buildModel(Assets assets) {
		model = assets.get(NAME + EXTENSION);
		modelInstance = new ModelInstance(model, planetPosition);
		applyPlanetaryScaling(SCALE * Universe.UNIVERSAL_SCALE);
		applyPlanetaryTilt(TILT);
	}

	public void advanceOrbit(Vector3 vector) {
		planetPosition.set(vector);
		advancecameraOrbit(vector);
	}

	private void advancecameraOrbit(Vector3 vector) {
		cameraPosition.set(vector.x * cameraOrbitDifferential, 0, vector.z * cameraOrbitDifferential);
	}

	private int generateRandomIntForOrbitTable() {
		return random.nextInt(table.length - 1);
	}

	private Vector3 setOrbitalPosition(int random, float distanceMultiplier) {
		float x = table[random][0] * distanceMultiplier;
		float y = table[random][1] * distanceMultiplier;
		float z = table[random][2] * distanceMultiplier;

		return new Vector3(x, y, z);
	}

	private void applyPlanetaryTilt(float tilt) {
		modelInstance.transform.rotate(1, 0, 1, tilt);
	}

	private void applyPlanetaryScaling(float scale) {
		modelInstance.transform.scl(scale);
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

	public Vector3 getCameraPosition() {
		return cameraPosition;
	}

	public float getDistance() {
		return DISTANCE;
	}

	public float getCameraDistance() {
		return cameraDistance;
	}

	/**
	 * @return planetPosition.x
	 */
	public float getX() {
		return planetPosition.x;
	}

	/**
	 * @return planetPosition.y
	 */
	public float getY() {
		return planetPosition.y;
	}

	/**
	 * @return planetPosition.z
	 */
	public float getZ() {
		return planetPosition.z;
	}

	public void setPosition(float x, float y, float z) {
		planetPosition.x = x;
		planetPosition.y = y;
		planetPosition.z = z;
	}

	public void setRotationVector(float x, float y, float z, float rotationVelocity) {
		rotationVector.x = x;
		rotationVector.y = y;
		rotationVector.z = z;
		this.rotationVelocity = rotationVelocity;
	}

	private void createcameraDebugger() {
		mb = new ModelBuilder();
		cameraModel = mb.createBox(1, 1, 1, new Material(ColorAttribute.createDiffuse(Color.GREEN)), Usage.Position | Usage.Normal);
		cameraDebugger = new ModelInstance(cameraModel);
	}

	private void debugCameraPositions(ModelBatch batch) {
		cameraDebugger.transform.setTranslation(cameraPosition);
		batch.render(cameraDebugger);
	}

	public void dispose() {
		model.dispose();
	}
}
