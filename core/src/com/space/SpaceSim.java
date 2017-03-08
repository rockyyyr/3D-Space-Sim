package com.space;

import com.badlogic.gdx.Game;

public class SpaceSim extends Game {

	private Simulation simulation;

	@Override
	public void create() {
		simulation = new Simulation();
		setScreen(new LoadingScreen(SpaceSim.this, simulation));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		simulation.dispose();
	}
}
