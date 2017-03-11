package com.space;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.Year;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.space.universe.Universe;
import com.space.universe.solarsystem.CosmicObject;
import com.space.util.Font;

/**
 * Hud.
 */
public class Hud {

	public static int CURRENT_YEAR = Year.now().getValue();
	public static final String NEWLINE = System.lineSeparator();

	public static final String DEJAVU_ITALIC = "fonts/DEJAVUSANSMONO-OBLIQUE.TTF";
	public static final String DEJAVU = "fonts/DEJAVUSANSMONO.TTF";

	public static final String PLANET_DATA_TEXT = "Distance: %s AU%nDiameter: %s x Earth";

	private static final String HELP = ""
			+ "W, A, S, D, Q, E - move" + NEWLINE
			+ "Mouse - change direction" + NEWLINE
			+ NEWLINE
			+ "2 - jump to next planet" + NEWLINE
			+ "1 - jump to previous planet" + NEWLINE
			+ "R - jump to sun" + NEWLINE
			+ "X - accelerate orbits" + NEWLINE
			+ "Z - decelerate orbits" + NEWLINE
			+ "C - slow down controls" + NEWLINE
			+ "V - speed up controls" + NEWLINE
			+ "U - toggle follow planet" + NEWLINE
			+ "Y - release camera" + NEWLINE
			+ "T - toggle orbits" + NEWLINE
			+ "J - toggle planet orbit paths" + NEWLINE
			+ "K - toggle moon orbit paths" + NEWLINE
			+ "F - toggle night sky" + NEWLINE
			+ "G - toggle planet data" + NEWLINE
			+ "H - toggle help context" + NEWLINE
			+ NEWLINE
			+ "Tab - toggle fullscreen" + NEWLINE
			+ "Esc - exit";

	private OrthographicCamera camera;
	private CosmicObject currentCelestialBody;
	private String celestialBodyData;

	private Stage stageHelp;
	private Stage stageData;
	private Stage stageSpeed;

	private BitmapFont font14;
	private BitmapFont font20;
	private BitmapFont fontItalic20;
	private BitmapFont fontItalicGrey20;
	private BitmapFont fontItalic64;

	private Label helpContext;
	private Label planetData;
	private Label planetNameData;
	private Label yearContext;
	private Label yearLabel;
	private Label speedLabel;
	private Label lookAtLabel;
	private Label cameraLock;

	private LabelStyle style14;
	private LabelStyle style20;
	private LabelStyle styleItalic20;
	private LabelStyle styleItalicGrey20;
	private LabelStyle styleItalic64;

	private float x, y;

	private boolean helpExpanded;
	private boolean dataExpanded;
	private boolean speedExpanded;
	private boolean lookAtOn;
	private boolean cameraLocked;
	private boolean ready;

	private Instant start;

	public Hud() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		ScreenViewport viewport = new ScreenViewport();
		viewport.setCamera(camera);

		stageHelp = new Stage(viewport);
		stageData = new Stage(viewport);
		stageSpeed = new Stage(viewport);

		x = (-camera.viewportWidth / 2) + 15;
		y = (-camera.viewportHeight / 2) + 15;

		helpExpanded = true;
		dataExpanded = true;

		setupFonts();
		setupHelpLabel();
	}

	public void render() {
		if (helpExpanded)
			displayHelpContext();

		if (dataExpanded) {
			generateLabels();
			displayPlanetDataContext();
		}
		if (speedExpanded) {
			displaySpeedContext();
			if (Duration.between(start, Instant.now()).toMillis() > 1500) {
				speedExpanded = false;
			}
		}

		if (lookAtOn)
			lookAtLabel.setStyle(styleItalic20);
		else
			lookAtLabel.setStyle(styleItalicGrey20);

		if (cameraLocked)
			cameraLock.setStyle(styleItalic20);
		else
			cameraLock.setStyle(styleItalicGrey20);
	}

	public void toggleCameraLock() {
		cameraLocked = !cameraLocked;
	}

	public void toggleLookAt() {
		lookAtOn = !lookAtOn;
	}

	public void lockLookAt() {
		lookAtOn = true;
	}

	public void unlockLookAt() {
		lookAtOn = false;
	}

	public void lockCamera() {
		cameraLocked = true;
	}

	public void unlockCamera() {
		cameraLocked = false;
	}

	public void displaySpeedLabel(float speed) {
		speedLabel.setText("speed: " + convertSpeedToLightYears(speed) + "c");
		speedExpanded = true;
		start = Instant.now();
	}

	private float convertSpeedToLightYears(float speed) {
		return speed / 50;
	}

	private void setupFonts() {
		font14 = Font.generateFont(DEJAVU, 14);
		font20 = Font.generateFont(DEJAVU, 20);
		fontItalic20 = Font.generateFont(DEJAVU_ITALIC, 20);
		fontItalicGrey20 = Font.generateFont(DEJAVU_ITALIC, 20, Color.DARK_GRAY);
		fontItalic64 = Font.generateFont(DEJAVU_ITALIC, 64);

		style14 = new LabelStyle();
		style14.font = font14;

		styleItalic64 = new LabelStyle();
		styleItalic64.font = fontItalic64;

		style20 = new LabelStyle();
		style20.font = font20;

		styleItalic20 = new LabelStyle();
		styleItalic20.font = fontItalic20;
		styleItalicGrey20 = new LabelStyle();
		styleItalicGrey20.font = fontItalicGrey20;
	}

	private void setupHelpLabel() {
		helpContext = new Label(HELP, style14);
		helpContext.setPosition(20, 50);
		stageHelp.addActor(helpContext);
	}

	private String generatePlanetData() {
		DecimalFormat df = new DecimalFormat("#.##");

		String distance = df.format(currentCelestialBody.getDistance() / Universe.AU);
		String scale = df.format(currentCelestialBody.getScale());

		return String.format(PLANET_DATA_TEXT, distance, scale);
	}

	private String generatePlanetName() {
		return currentCelestialBody.getName();
	}

	private void generateLabels() {
		if (!ready) {
			planetNameData = new Label(generatePlanetName(), styleItalic64);
			planetNameData.setPosition(20, camera.viewportHeight - 100);

			planetData = new Label(generatePlanetData(), style20);
			planetData.setPosition(20, camera.viewportHeight - 160);

			yearContext = new Label(String.valueOf(CURRENT_YEAR), styleItalic64);
			yearContext.setPosition(camera.viewportWidth - 200, camera.viewportHeight - 100);

			yearLabel = new Label("Year", style20);
			yearLabel.setPosition(camera.viewportWidth - 260, camera.viewportHeight - 60);

			speedLabel = new Label("", styleItalic20);
			speedLabel.setPosition(20, (camera.viewportHeight / 2) - 60);

			lookAtLabel = new Label("camera follow", styleItalicGrey20);
			lookAtLabel.setPosition(20, camera.viewportHeight / 2);

			cameraLock = new Label("camera locked", styleItalicGrey20);
			cameraLock.setPosition(20, (camera.viewportHeight / 2) - 30);

			ready = true;
		}

		planetNameData.setText(generatePlanetName());
		planetData.setText(generatePlanetData());
		yearContext.setText(String.valueOf(CURRENT_YEAR));

		stageData.addActor(planetNameData);
		stageData.addActor(planetData);
		stageData.addActor(yearContext);
		stageData.addActor(yearLabel);
		stageData.addActor(lookAtLabel);
		stageData.addActor(cameraLock);

		stageSpeed.addActor(speedLabel);
	}

	public void toggleHelpContext() {
		helpExpanded = !helpExpanded;
	}

	public void togglePlanetDataContext() {
		dataExpanded = !dataExpanded;
	}

	public void setCurrentPlanet(CosmicObject body) {
		currentCelestialBody = body;
	}

	private void displayHelpContext() {
		stageHelp.draw();
	}

	private void displayPlanetDataContext() {
		stageData.draw();
	}

	private void displaySpeedContext() {
		stageSpeed.draw();
	}

	public void dispose() {
		font14.dispose();
		fontItalic64.dispose();
		stageHelp.dispose();
	}
}
