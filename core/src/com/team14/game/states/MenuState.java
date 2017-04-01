package com.team14.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team14.game.BubbleAdventure;

/**
 * Created by Arianne on 3/26/17.
 */

public class MenuState extends State{
    private Texture background;
    private Texture playBtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("menubg.jpg");
        playBtn = new Texture("playbtn.png");
    }

    @Override
    public void handleInput() {
        //if user finger taps, mouse clicks etc
        if(Gdx.input.justTouched()){
            gsm.set(new StartState(gsm));
        }

    }

    @Override
    public void update(float dt) {
        handleInput(); //by putting in update, will always be checking to see if our user is doing anything
    }

    //SpriteBatch needs to open and close
    //Think of it like a container
    //Open box, put everything you want in it
    //CLose box, and it will render everything you need to in it
    @Override
    public void render(SpriteBatch sb) {
        sb.begin(); //opens
        sb.draw(background, 0, 0, BubbleAdventure.WIDTH, BubbleAdventure.HEIGHT);//draw(image to draw, x-pos, y-pos, width of screen, height)
        //0,0 = bottom left hand of screen
        sb.draw(playBtn, (BubbleAdventure.WIDTH / 2) - (playBtn.getWidth() / 2), BubbleAdventure.HEIGHT / 2); //centers button
        sb.end(); //close
    }

    //call this when transitioning states
    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
    }
}
