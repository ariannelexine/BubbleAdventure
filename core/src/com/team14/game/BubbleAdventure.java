package com.team14.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team14.game.states.GameStateManager;
import com.team14.game.states.MenuState;

public class BubbleAdventure extends ApplicationAdapter {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	public static final String TITLE = "Bubble Adventure";

	private GameStateManager gsm;
	private SpriteBatch batch; //only need one bc heavy files, just pass around each state
	Texture img;

	//score variable
	private static int scoreNum;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new MenuState(gsm));
		scoreNum = 0;

	}


	//render method runs continuously
	//need to make GameStateManager update first then render game states
	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //wipes the screen clean and spritebatch draws everything fresh
		gsm.update(Gdx.graphics.getDeltaTime()); //tells us difference between render times
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}

	public static void increment()
	{
		scoreNum += 100;
		System.out.println(scoreNum);
	}

	public static int getScore(){return scoreNum;}

	public static void resetScore(){scoreNum = 0;}

}
