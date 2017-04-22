package com.team14.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;


public class AndroidLauncher extends AndroidApplication {
<<<<<<< HEAD
	SharedPreferences pref;
=======

>>>>>>> 68f0fd0f5e16f9f62fa0c2c0c16b27012b3c9e92

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		pref = getSharedPreferences("com.team14.game", Context.MODE_PRIVATE);
		if(!(pref.contains("highscore")))
		{
			pref.edit().putInt("highscore", 0);
		}
		initialize(new BubbleAdventure(), config);
	}
}

