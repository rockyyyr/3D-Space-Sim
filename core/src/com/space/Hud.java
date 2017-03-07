package com.space;

import java.text.DecimalFormat;
import java.time.Year;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.space.universe.Universe;
import com.space.universe.solarsystem.CelestialBody;
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
			+ "Q - jump to next planet" + NEWLINE
			+ "A - jump to previous planet" + NEWLINE
			+ "W - jump to sun" + NEWLINE
			+ "X - accelerate orbits" + NEWLINE
			+ "Z - decelerate orbits" + NEWLINE
			+ "T - toggle orbits" + NEWLINE
			+ "S - toggle night sky" + NEWLINE
			+ "D - toggle planet data" + NEWLINE
			+ "H - toggle help context" + NEWLINE
			+ NEWLINE
			+ "Mouse wheel - zoom in/out" + NEWLINE
			+ "Right click - rotate camera" + NEWLINE
			+ "Left click - move camera" + NEWLINE;

	private String celestialBodyData;

	private OrthographicCamera camera;
	private Stage stageHelp;
	private Stage stageData;
	private CelestialBody currentCelestialBody;

	private BitmapFont font14;
	private BitmapFont font20;
	private BitmapFont fontItalic64;

	private Label helpContext;
	private Label planetData;
	private Label planetNameData;
	private Label yearContext;
	private Label yearLabel;
	private LabelStyle style14;
	private LabelStyle style20;
	private LabelStyle styleItalic64;

	private float x, y;
	private boolean helpExpanded;
	private boolean dataExpanded;
	private static boolean ready = false;

	public Hud() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stageHelp = new Stage(new ScreenViewport());
		stageData = new Stage(new ScreenViewport());

		x = (-camera.viewportWidth / 2) + 15;
		y = (-camera.viewportHeight / 2) + 15;

		helpExpanded = false;
		dataExpanded = true;

		setupFonts();
		setupHelpLabel();
	}

	public void render() {
		if (helpExpanded)
			displayHelpContext();

		if (dataExpanded) {
			generatePlanetLabel();
			displayPlanetDataContext();
		}
	}

	private void setupFonts() {
		font14 = Font.generateFont(DEJAVU, 14);
		font20 = Font.generateFont(DEJAVU, 20);
		fontItalic64 = Font.generateFont(DEJAVU_ITALIC, 64);

		style14 = new LabelStyle();
		style14.font = font14;

		styleItalic64 = new LabelStyle();
		styleItalic64.font = fontItalic64;

		style20 = new LabelStyle();
		style20.font = font20;
	}

	private void setupHelpLabel() {
		helpContext = new Label(HELP, style14);
		helpContext.setPosition(20, 20);
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

	private void generatePlanetLabel() {
		if (!ready) {
			planetNameData = new Label(generatePlanetName(), styleItalic64);
			planetNameData.setPosition(20, camera.viewportHeight - 100);

			planetData = new Label(generatePlanetData(), style20);
			planetData.setPosition(20, camera.viewportHeight - 160);

			yearContext = new Label(String.valueOf(CURRENT_YEAR), styleItalic64);
			yearContext.setPosition(camera.viewportWidth - 200, camera.viewportHeight - 100);

			yearLabel = new Label("Year", style20);
			yearLabel.setPosition(camera.viewportWidth - 260, camera.viewportHeight - 60);

			ready = true;
		}

		planetNameData.setText(generatePlanetName());
		planetData.setText(generatePlanetData());
		yearContext.setText(String.valueOf(CURRENT_YEAR));

		stageData.addActor(planetNameData);
		stageData.addActor(planetData);
		stageData.addActor(yearContext);
		stageData.addActor(yearLabel);
	}

	public void toggleHelpContext() {
		helpExpanded = !helpExpanded;
	}

	public void togglePlanetDataContext() {
		dataExpanded = !dataExpanded;
	}

	public void setCurrentPlanet(CelestialBody body) {
		currentCelestialBody = body;
	}

	private void displayHelpContext() {
		stageHelp.draw();
	}

	private void displayPlanetDataContext() {
		stageData.draw();
	}

	public void dispose() {
		font14.dispose();
		fontItalic64.dispose();
		stageHelp.dispose();
	}
}
