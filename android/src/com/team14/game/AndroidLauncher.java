package com.team14.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;


public class AndroidLauncher extends AndroidApplication {

	@Override
	protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		SharedPreferences pref = getSharedPreferences("com.team14.game", Context.MODE_PRIVATE);
		if(!(pref.contains("highscore")))
		{
			pref.edit().putInt("highscore", 0);
		}
		initialize(new BubbleAdventure(), config);
	}
}

