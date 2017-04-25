package com.team14.game;

//import android.content.Context;
//import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import static java.sql.Types.NULL;


public class AndroidLauncher extends AndroidApplication {

	@Override
	protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();


		long id = getIntent().getExtras().getLong("userId");//gets the userId passed by the intent from LoginActivity - Anil
		//System.out.printf("%ld", id);
		/*Intent intent = getIntent();
		if(intent == null)
		{
			System.out.printf("This is null\n");
		}
		else
			System.out.printf("WTF\n");*/
		initialize(new BubbleAdventure(id), config);//passed argument to new instance of BubbleAdventure - Anil
	}
}

