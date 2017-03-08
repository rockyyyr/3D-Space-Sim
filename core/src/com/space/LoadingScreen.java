package com.space;

import java.time.Duration;
import java.time.Instant;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.space.util.Font;

/**
 * LoadingScreen.
 */
public class LoadingScreen implements Screen {

	public static final String DEJAVU = "fonts/DEJAVUSANSMONO.TTF";
	public static final String[] LOADING = { "loading", "loading.", "loading..", "loading..." };
	public static final String TITLE = "space simulation";
	public static final double LOADING_DELAY = 400;

	private Game game;
	private Simulation screen;

	private Stage stage;

	private Label loading;
	private BitmapFont font30;
	private BitmapFont font50;

	private Instant start;
	private int index;

	public LoadingScreen(Game game, Simulation screen) {
		this.game = game;
		this.screen = screen;

		stage = new Stage();
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		start = Instant.now();

		font30 = Font.generateFont(DEJAVU, 30);
		font50 = Font.generateFont(DEJAVU, 50);

		LabelStyle style30 = new LabelStyle();
		LabelStyle style50 = new LabelStyle();

		style50.font = font50;
		style30.font = font30;

		loading = new Label(LOADING[index], style30);
		Label title = new Label(TITLE, style50);

		loading.setPosition((Gdx.graphics.getWidth() / 2) - 100, (Gdx.graphics.getHeight() / 2) - 50);
		title.setPosition((Gdx.graphics.getWidth() / 2) - 250, (Gdx.graphics.getHeight() / 2));

		stage.addActor(loading);
		stage.addActor(title);
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
		animateLoadingLabel();
		stage.draw();
		checkLoadingStatus(delta);
	}

	private void animateLoadingLabel() {
		if (Duration.between(start, Instant.now()).toMillis() > LOADING_DELAY) {
			loading.setText(LOADING[index]);
			index = index < 3 ? index += 1 : 0;
			start = Instant.now();
		}
	}

	/*
	 * Switches to the simulation screen when all assets are done loading
	 */
	private void checkLoadingStatus(float delta) {
		if (screen.getRenderer().getUniverse().getAssetManager().update()) {
			screen.render(delta);
			game.setScreen(screen);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause() {
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume() {
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide() {
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose() {
		font30.dispose();
		font50.dispose();
		stage.dispose();
	}

}
