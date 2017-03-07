package com.space.util;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.space.Hud;
import com.space.UniverseRenderer;

/**
 * InputHandler.
 */
public class InputHandler extends CameraInputController {

	private PerspectiveCamera camera;
	private UniverseRenderer renderer;
	private Hud hud;

	private static int index = -1;

	public InputHandler(PerspectiveCamera camera, UniverseRenderer renderer, Hud hud) {
		super(camera);
		this.camera = camera;
		this.renderer = renderer;
		this.hud = hud;
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyDown(int)
	 */
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.A:
			renderer.setCameraAtPlanet(downIndex());
			break;
		case Keys.Q:
			renderer.setCameraAtPlanet(upIndex());
			break;
		case Keys.W:
			renderer.setCameraAtSun();
			resetIndex();
			break;
		case Keys.H:
			hud.toggleHelpContext();
			break;
		case Keys.T:
			renderer.getUniverse().toggleOrbits();
			break;
		case Keys.D:
			hud.togglePlanetDataContext();
			break;
		case Keys.S:
			renderer.getUniverse().toggleSky();
			break;
		case Keys.UP:
			camera.translate(0, 5, 0);
			break;
		case Keys.DOWN:
			camera.translate(0, -5, 0);
			break;
		case Keys.LEFT:
			camera.translate(-5, 0, 0);
			break;
		case Keys.RIGHT:
			camera.translate(5, 0, 0);
			break;
		case Keys.Z:
			renderer.getUniverse().decelerateOrbits();
			break;
		case Keys.X:
			renderer.getUniverse().accelerateOrbits();
			break;
		}
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		renderer.translate = false;
		super.scrolled(amount);
		return true;
	}

	private int upIndex() {
		renderer.translate = true;
		return index < 8 ? ++index : index;
	}

	private int downIndex() {
		renderer.translate = true;
		if (index == -1)
			index = 0;
		return index > 0 ? --index : index;
	}

	private void resetIndex() {
		index = 0;
	}

}
