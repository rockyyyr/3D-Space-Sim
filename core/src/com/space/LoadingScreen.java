package com.space;

import java.time.Duration;
import java.time.Instant;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	public static final double LOADING_DELAY = 400;

	private Game game;
	private Simulation screen;

	private Stage stage;
	private Label label;
	private LabelStyle style;
	private BitmapFont font;

	private Instant start;
	private int index;

	private Texture texture;
	private Sprite sprite;
	private SpriteBatch batch;

	private LoadingAnimation animation;

	public LoadingScreen(Game game, Simulation screen) {
		this.game = game;
		this.screen = screen;

		animation = new LoadingAnimation();

		font = Font.generateFont(DEJAVU, 30);
		stage = new Stage();
		start = Instant.now();

	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		BitmapFont font50 = Font.generateFont(DEJAVU, 50);
		LabelStyle style50 = new LabelStyle();
		style50.font = font50;

		style = new LabelStyle();
		style.font = font;

		label = new Label(LOADING[index], style);
		Label title = new Label("space simulation", style50);
		label.setPosition((Gdx.graphics.getWidth() / 2) - 100, (Gdx.graphics.getHeight() / 2) - 50);
		title.setPosition((Gdx.graphics.getWidth() / 2) - 250, (Gdx.graphics.getHeight() / 2));

		stage.addActor(label);
		stage.addActor(title);

	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		if (Duration.between(start, Instant.now()).toMillis() > LOADING_DELAY) {
			if (index < 3) {
				index++;
			} else {
				index = 0;
			}
			label.setText(LOADING[index]);
			start = Instant.now();
		}
		stage.draw();

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
	}

	public class LoadingAnimation {

		public void animate() {
			Gdx.app.postRunnable(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < 3; i++) {
						label.setText(LOADING + dots(i + 1));
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						if (i == 2)
							i = 0;
					}
				}
			});
		}
	}

	private String dots(int numOfDots) {
		String dot = ".";
		String result = "";
		for (int i = 0; i < numOfDots; i++) {
			result += dot;
		}
		return result;
	}

}
