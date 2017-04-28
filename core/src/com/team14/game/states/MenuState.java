package com.team14.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team14.game.BubbleAdventure;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Rectangle;
import com.team14.game.sprites.Bubble;
import com.team14.game.states.PlayState;

/**
 * Created by Arianne on 3/26/17.
 */

public class MenuState extends State{
    private Texture background;
    private Texture playBtn;
    private Texture musicOffBtn;
    private Texture musicOnBtn;
    //private Texture soundBtn;
    private float playBtnXPos;
    private float playBtnYPos;
    //all gameplay music
    private float musicOffBtnXPos;
    private float musicOffBtnYPos;
    private float musicOnBtnXPos;
    private float musicOnBtnYPos;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, BubbleAdventure.WIDTH / 2, BubbleAdventure.HEIGHT / 2);
        background = new Texture("bg.jpg");
        playBtn = new Texture("playbtn.png");
        playBtnXPos = playBtn.getWidth() / 2;
        playBtnYPos = cam.position.y;
        //music off button
        musicOffBtn = new Texture("musicOffBtn.png");
        musicOffBtnXPos = musicOffBtn.getWidth()/3 + (2*musicOffBtn.getWidth());
        musicOffBtnYPos = cam.position.y / 2;
        musicOnBtn = new Texture("musicOnBtn.png");
        musicOnBtnXPos = musicOnBtn.getWidth()/5 + musicOnBtn.getWidth();
        musicOnBtnYPos = cam.position.y / 2;
    }

    @Override
    public void handleInput() {
        //if user finger taps, mouse clicks etc
        if(Gdx.input.justTouched()){
            //gsm.set(new StartState(gsm));
            Vector3 tmp=new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
            cam.unproject(tmp);//can't unproject vector 2 so vector3 used
            Rectangle textureBoundsPlayBtn=new Rectangle(playBtnXPos,playBtnYPos, playBtn.getWidth(), playBtn.getHeight());
            Rectangle textureBoundsMusicOffBtn=new Rectangle(musicOffBtnXPos,musicOffBtnYPos, musicOffBtn.getWidth(), musicOffBtn.getHeight());
            Rectangle textureBoundsMusicOnBtn=new Rectangle(musicOnBtnXPos,musicOnBtnYPos, musicOnBtn.getWidth(), musicOnBtn.getHeight());
            // texture btn_xpos is the x position of the texture
            // texture btn_ypos is the y position of the texture
            // btn_width is the width of the texture (you can get it with texture.getWidth() or textureRegion.getRegionWidth() if you have a texture region
            // btn_height is the height of the texture (you can get it with texture.getHeight() or textureRegion.getRegionhHeight() if you have a texture region
            if(textureBoundsPlayBtn.contains(tmp.x,tmp.y))
            {
                gsm.set(new StartState(gsm));
            }
            //toggles off for music by adjusting the volume
            if(textureBoundsMusicOffBtn.contains(tmp.x,tmp.y))
            {
                //turn music off 0f
                Bubble.bubbleVolume = 0f;
                PlayState.musicControl = 0f;
                gsm.set(new StartState(gsm));

            }
            //toggles on for music by adjusting the volume
            if(textureBoundsMusicOnBtn.contains(tmp.x,tmp.y))
            {
                //turn music off 0f
                Bubble.bubbleVolume = 1f;
                PlayState.musicControl = 1f;
                gsm.set(new StartState(gsm));

            }
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
        sb.draw(playBtn, cam.position.x = playBtnXPos, playBtnYPos); //centers button
        sb.draw(musicOffBtn, cam.position.x = musicOffBtnXPos, musicOffBtnYPos);
        sb.draw(musicOnBtn, cam.position.x = musicOnBtnXPos, musicOnBtnYPos);
        sb.end(); //close
    }

    //call this when transitioning states
    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
    }
}
