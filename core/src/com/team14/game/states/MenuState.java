package com.team14.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team14.game.BubbleAdventure;

/**
 * Created by Arianne on 3/26/17.
 */

public class MenuState extends State{
    private Texture background;
    private Texture playBtn;
    private String scoreString;
    private BitmapFont scoreFont;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, BubbleAdventure.WIDTH / 2, BubbleAdventure.HEIGHT / 2);
        background = new Texture("bg.jpg");
        playBtn = new Texture("playbtn.png");
        scoreString = "Score: ";// + pref.getInteger("highscore", 0);
        scoreFont = new BitmapFont();

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
        sb.setProjectionMatrix(cam.combined);
        sb.begin(); //opens
        sb.draw(background, 0, 0);//draw(image to draw, x-pos, y-pos, width of screen, height)
        //0,0 = bottom left hand of screen
        sb.draw(playBtn, cam.position.x = playBtn.getWidth() / 2, cam.position.y); //centers button
        scoreFont.setColor(Color.GOLD);//can also input rgb values
        scoreFont.draw(sb, scoreString, cam.position.x - cam.viewportWidth/2, cam.viewportHeight);
        scoreFont.setUseIntegerPositions(false);//fixes shaking of score display
        sb.end(); //close
    }

    //call this when transitioning states
    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();

    }


}
