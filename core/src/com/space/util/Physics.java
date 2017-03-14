package com.space.util;

import com.badlogic.gdx.math.Vector3;
import com.space.universe.Universe;
import com.space.universe.solarsystem.CosmicObject;
import com.space.universe.solarsystem.OrbitingCosmicObject;

/**
 * Physics.
 */
public class Physics {

	public Physics() {
	}

	public static void advanceOrbit(CosmicObject host, OrbitingCosmicObject orbital) {
		// float a = orbital.getDistance() / 2;
		// float b = orbital.getDistance();
		//
		// double angle = Math.atan2(orbital.getPosition().z - host.getPosition().z, orbital.getPosition().x - host.getPosition().x);
		// double theta = Math.atan((b / a) * Math.tan(angle));
		//
		// theta += 0.0002f;
		//
		// float z = (a * (float) Math.cos(theta));
		// float x = (b * (float) Math.sin(theta));
		//
		// orbital.advanceOrbit(new Vector3(x, 0, z));

		float a = orbital.getDistance() / 2;
		float b = orbital.getDistance();

		double angle = Math.atan2(orbital.getPosition().z - host.getPosition().z, orbital.getPosition().x - host.getPosition().x);

		angle += Universe.ORBITAL_VELOCITY / orbital.getDistance();

		float x = (a * b) / (float) Math.sqrt((b * b) + ((a * a) * ((float) Math.tan(angle) * (float) Math.tan(angle))));
		float z = (a * b / (float) Math.sqrt((a * a) + ((b * b) / ((float) Math.tan(angle) * (float) Math.tan(angle)))));

		orbital.advanceOrbit(new Vector3(x, 0, z));

	}

}
