package com.space.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.space.SpaceSim;

public class SpaceSim3DDesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// config.width = 1280;
		// config.height = 720;
		config.width = 1800;
		config.height = 1400;
		new LwjglApplication(new SpaceSim(), config);
	}
}
