package com.space;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.bullet.Bullet;

public class SpaceSim extends Game {

	@Override
	public void create() {
		Bullet.init();
		setScreen(new Simulation());
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
	}
}
