package com.space.universe.solarsystem;

/**
 * Asteroid.
 */
public class Asteroid extends OrbitingCosmicObject {

	public Asteroid(String name, float scale, float distanceFromHost, float orbitalPeriod, float tilt, float cameraDistanceFromPlanet) {
		super(name, scale, distanceFromHost, orbitalPeriod, tilt, cameraDistanceFromPlanet);
	}

	/**
	 * Sets the axis of rotation and rotation velocity
	 * 
	 * @param x
	 *            axis of rotation
	 * @param y
	 *            axis of rotation
	 * @param z
	 *            axis of rotation
	 * @param rotationVelocity
	 *            speed of rotation
	 */
	public void setRotationVector(float x, float y, float z, float rotationVelocity) {
		rotationVector.x = x;
		rotationVector.y = y;
		rotationVector.z = z;
		this.rotationVelocity = rotationVelocity;
	}

}