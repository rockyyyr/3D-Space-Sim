package com.space;

import java.text.DecimalFormat;

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

	public static final String NEWLINE = System.lineSeparator();
	private static final String HELP = ""
			+ "Q - jump to next planet" + NEWLINE
			+ "A - jump to previous planet" + NEWLINE
			+ "W - jump to sun" + NEWLINE
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

	private BitmapFont font;
	private BitmapFont font30;
	private BitmapFont fontItalic;

	private Label helpContext;
	private Label planetData;
	private Label planetNameData;
	private LabelStyle style;
	private LabelStyle style30;
	private LabelStyle styleItalic;

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

		helpExpanded = true;
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

	private void setupFonts() {
		font = Font.generateFont("DEJAVUSANSMONO.TTF", 14);
		fontItalic = Font.generateFont("DEJAVUSANSMONO-OBLIQUE.TTF", 64);
		font30 = Font.generateFont("DEJAVUSANSMONO.TTF", 20);

		style = new LabelStyle();
		style.font = font;

		styleItalic = new LabelStyle();
		styleItalic.font = fontItalic;

		style30 = new LabelStyle();
		style30.font = font30;
	}

	private void setupHelpLabel() {
		helpContext = new Label(HELP, style);
		helpContext.setPosition(20, 20);
		stageHelp.addActor(helpContext);
	}

	private String generatePlanetData() {
		DecimalFormat df = new DecimalFormat("#.##");

		String distance = df.format(currentCelestialBody.getDistance() / Universe.AU);
		String scale = df.format(currentCelestialBody.getScale());

		return String.format("AU: %s%nDiameter: %s", distance, scale);
	}

	private String generatePlanetName() {
		return currentCelestialBody.getName();
	}

	private void generatePlanetLabel() {
		if (!ready) {
			planetNameData = new Label(generatePlanetName(), styleItalic);
			planetData = new Label(generatePlanetData(), style30);
			ready = true;
		}

		planetNameData.setText(generatePlanetName());
		planetNameData.setPosition(20, camera.viewportHeight - 100);

		planetData.setText(generatePlanetData());
		planetData.setPosition(20, (camera.viewportHeight) - 160);

		stageData.addActor(planetNameData);
		stageData.addActor(planetData);
	}

	public void dispose() {
		font.dispose();
		fontItalic.dispose();
		stageHelp.dispose();
	}
}
