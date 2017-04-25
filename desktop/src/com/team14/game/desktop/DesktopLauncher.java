package com.team14.game.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.team14.game.BubbleAdventure;

import static com.badlogic.gdx.Application.ApplicationType.Android;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = BubbleAdventure.WIDTH;
		config.height = BubbleAdventure.HEIGHT;
		config.title = BubbleAdventure.TITLE;
		//long id = getIntent().getExtras().getlong("userId");
		long id = 1;
		new LwjglApplication(new BubbleAdventure(id), config);
	}
}
