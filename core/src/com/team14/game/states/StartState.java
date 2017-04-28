package com.team14.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team14.game.BubbleAdventure;


/**
 * Created by Arianne on 3/31/17.
 * State between Menu and Play, freezes the bubble to prep user for tapping
 */

public class StartState extends State {

    private Texture startBubble, bg, tap;
    private BitmapFont description;
    private String descString;

    public StartState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, BubbleAdventure.WIDTH / 2, BubbleAdventure.HEIGHT / 2);
        bg = new Texture("bg1.jpg");
        tap = new Texture("Tap.png");
        descString = "BUBBA "+ " EATS VEGETABLES TO \nACCUMULATE POINTS. EATING \nJUNK FOOD WILL MAKE HIM \nFLUFFY AND " +
                "TOO MUCH FOOD \nWILL MAKE HIM POP. OH AND \nWATCH OUT FOR OBSTACLES!\n";
        description = new BitmapFont();
        description.getData().setScale(0.8f, 0.8f);//resize text by scaling it
        startBubble = new Texture("SmallBubble.png");

    }

    //If player taps screen, transition to play state
    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
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
        sb.draw(tap, 50, 200); //should probably not hard code will deal wid it l8r
        description.setColor(Color.CYAN);
        description.draw(sb, descString, 30, 130);
        sb.draw(startBubble, 50, 150);
        sb.end();
    }

    @Override
    public void dispose() {
        startBubble.dispose();
        bg.dispose();
        tap.dispose();
        description.dispose();
    }
}
