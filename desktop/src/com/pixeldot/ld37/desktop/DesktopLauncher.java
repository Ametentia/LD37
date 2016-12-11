package com.pixeldot.ld37.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pixeldot.ld37.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = Game.WIDTH;
		config.height = Game.HEIGHT;
		config.fullscreen=true;

		config.forceExit = false;

		new LwjglApplication(new Game(), config);
	}
}
