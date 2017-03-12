package com.space.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.space.Hud;
import com.space.UniverseRenderer;

/**
 * InputHandler.
 */
public class InputHandler extends FirstPersonCameraController {

	public static float VELOCITY = 150;
	public static final float DEGREES_PER_PIXEL = 0.1f;

	private PerspectiveCamera camera;
	private UniverseRenderer renderer;
	private Hud hud;

	private static int index = -1;
	private Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();

	public InputHandler(PerspectiveCamera camera, UniverseRenderer renderer, Hud hud) {
		super(camera);

		this.camera = camera;
		this.renderer = renderer;
		this.hud = hud;

		super.setVelocity(VELOCITY);
		super.setDegreesPerPixel(DEGREES_PER_PIXEL);
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyDown(int)
	 */
	@Override
	public boolean keyDown(int keycode) {
		super.keyDown(keycode);
		switch (keycode) {
		case Keys.NUM_1:
			renderer.setCameraAtPlanet(downIndex());
			break;
		case Keys.NUM_2:
			renderer.setCameraAtPlanet(upIndex());
			break;
		case Keys.R:
			renderer.setCameraAtSun();
			resetIndex();
			break;
		case Keys.H:
			hud.toggleHelpContext();
			break;
		case Keys.T:
			renderer.getUniverse().toggleOrbits();
			break;
		case Keys.J:
			renderer.getUniverse().togglePlanetOrbitPaths();
			break;
		case Keys.K:
			renderer.getUniverse().toggleMoonOrbitPaths();
			break;
		case Keys.G:
			hud.togglePlanetDataContext();
			break;
		case Keys.F:
			renderer.getUniverse().toggleSky();
			break;
		case Keys.Z:
			renderer.getUniverse().decelerateOrbits();
			break;
		case Keys.X:
			renderer.getUniverse().accelerateOrbits();
			break;
		case Keys.Y:
			renderer.releaseCamera();
			break;
		case Keys.U:
			renderer.toggleLookAtPlanet();
			break;
		case Keys.C:
			slowDownController();
			break;
		case Keys.V:
			speedUpController();
			break;
		case Keys.TAB:
			toggleFullScreen();
			break;
		case Keys.ESCAPE:
			shutDown();
			break;
		}
		return true;
	}

	private void speedUpController() {
		if (VELOCITY >= 50)
			VELOCITY += 50;
		else
			VELOCITY += 10;

		setSpeed(VELOCITY);
	}

	private void slowDownController() {
		if (VELOCITY > 50)
			VELOCITY -= 50;
		else if (VELOCITY > 0)
			VELOCITY -= 10;

		setSpeed(VELOCITY);
	}

	private void setSpeed(float speed) {
		hud.displaySpeedLabel(VELOCITY);
		super.setVelocity(VELOCITY);
	}

	private int upIndex() {
		renderer.lockCamera();
		return index < 8 ? ++index : index;
	}

	private int downIndex() {
		renderer.lockCamera();
		if (index == -1)
			index = 0;
		return index > 0 ? --index : index;
	}

	private void resetIndex() {
		index = 0;
	}

	private void toggleFullScreen() {
		if (Gdx.graphics.isFullscreen()) {
			Gdx.graphics.setWindowedMode(currentMode.width, currentMode.height);
		} else {
			Gdx.graphics.setFullscreenMode(currentMode);
		}
	}

	private void shutDown() {
		Gdx.app.exit();
	}

}
