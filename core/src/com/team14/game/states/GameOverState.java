package com.team14.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team14.game.BubbleAdventure;

/**
 * Created by Arianne on 3/31/17.
 */

public class GameOverState extends State{

    private Texture bg;
    private Texture gameOver;


    public GameOverState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, BubbleAdventure.WIDTH / 2, BubbleAdventure.HEIGHT / 2);
        bg = new Texture("bg1.jpg");
        gameOver = new Texture("GameOver.png");

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new StartState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, 0,0);
        sb.draw(gameOver, 25, 200); //should probably not hard code will deal wid it l8r
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
