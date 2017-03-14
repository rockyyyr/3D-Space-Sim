package com.space;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.space.util.Assets;

public class SpaceSim extends Game {

	private Assets assets;
	private FPSLogger logger;

	@Override
	public void create() {
		assets = new Assets();
		assets.loadModels();

		setScreen(new LoadingScreen(SpaceSim.this, assets));
		logger = new FPSLogger();

	}

	@Override
	public void render() {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT |
				(Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
		super.render();
		logger.log();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
