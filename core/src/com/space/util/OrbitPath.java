package com.space.util;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.space.universe.solarsystem.CosmicObject;

/**
 * OrbitPath.
 */
public class OrbitPath {

	private static ShapeRenderer renderer = new ShapeRenderer();

	/**
	 * Draw a blue line representing a Cosmic Objects path of orbit
	 * 
	 * @param host
	 * @param orbital
	 * @param camera
	 */
	public static void drawOrbitalPath(CosmicObject host, CosmicObject orbital, Camera camera) {
		Matrix4 mat = camera.combined.cpy();
		mat.rotate(1, 0, 0, 90);
		renderer.setProjectionMatrix(mat);

		renderer.setColor(Color.BLUE);
		renderer.begin(ShapeType.Line);
		renderer.circle(host.getX(), host.getZ(), orbital.getDistance(), 500);
		renderer.end();
	}

}
