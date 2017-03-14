package com.space.universe.solarsystem;

/**
 * Asteroid.
 */
public class Asteroid extends OrbitingCosmicObject {

	public Asteroid(String name, float scale, float distanceFromHost) {
		super(name, scale, distanceFromHost, 0, 0);
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
