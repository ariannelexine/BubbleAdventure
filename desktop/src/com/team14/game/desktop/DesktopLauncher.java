package com.team14.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.team14.game.BubbleAdventure;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = BubbleAdventure.WIDTH;
		config.height = BubbleAdventure.HEIGHT;
		config.title = BubbleAdventure.TITLE;
		//new LwjglApplication(new BubbleAdventure(), config);
	}
}
