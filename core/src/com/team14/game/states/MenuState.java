package com.team14.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    private Texture musicOnBtn;
    private BitmapFont title;
    private String titleString;

    private float playBtnXPos;
    private float playBtnYPos;

    //all gameplay music
    private float musicOnBtnXPos;
    private float musicOnBtnYPos;
    private boolean musicOn;

    //For displaying High Score
    private String scoreString;
    private BitmapFont scoreFont;

    Sound blop;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, BubbleAdventure.WIDTH / 2, BubbleAdventure.HEIGHT / 2);
        background = new Texture("bg.jpg");
        title = new BitmapFont(Gdx.files.internal("title.fnt"));
        titleString = "   Bubble\n" +
                      "Adventure";
        title.getData().setScale(.9f, 1.5f);

        playBtn = new Texture("playbtn.png");
        playBtnXPos = cam.position.x - (playBtn.getWidth() / 2);
        playBtnYPos = cam.position.y - (playBtn.getHeight());

        musicOnBtn = new Texture("musicOn.png");
        musicOnBtnXPos = cam.position.x + (cam.viewportWidth/2) - musicOnBtn.getWidth() - 10;
        musicOnBtnYPos = cam.position.y - cam.position.y + 10;
        musicOn = true;

        scoreString = "High Score: " + BubbleAdventure.getPrefScore();
        scoreFont = new BitmapFont(Gdx.files.internal("score.fnt"));
        scoreFont.getData().setScale(.6f);
        blop = Gdx.audio.newSound(Gdx.files.internal("Blop.wav"));
    }

    @Override
    public void handleInput() {
        //if user finger taps, mouse clicks etc
        if(Gdx.input.justTouched()){
            //gsm.set(new StartState(gsm));
            Vector3 tmp=new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
            cam.unproject(tmp);//can't unproject vector 2 so vector3 used
            Rectangle textureBoundsPlayBtn=new Rectangle(playBtnXPos,playBtnYPos, playBtn.getWidth(), playBtn.getHeight());
           // Rectangle textureBoundsMusicOffBtn=new Rectangle(musicOffBtnXPos,musicOffBtnYPos, musicOffBtn.getWidth(), musicOffBtn.getHeight());
            Rectangle textureBoundsMusicOnBtn=new Rectangle(musicOnBtnXPos,musicOnBtnYPos, musicOnBtn.getWidth(), musicOnBtn.getHeight());
            // texture btn_xpos is the x position of the texture
            // texture btn_ypos is the y position of the texture
            // btn_width is the width of the texture (you can get it with texture.getWidth() or textureRegion.getRegionWidth() if you have a texture region
            // btn_height is the height of the texture (you can get it with texture.getHeight() or textureRegion.getRegionhHeight() if you have a texture region
            if(textureBoundsPlayBtn.contains(tmp.x,tmp.y))
            {
                blop.play(PlayState.musicControl);
                gsm.set(new StartState(gsm));
            }

            //toggles on for music by adjusting the volume
            if(textureBoundsMusicOnBtn.contains(tmp.x,tmp.y))
            {
                if(musicOn == true){
                    musicOnBtn = new Texture("musicOff.png");
                    Bubble.bubbleVolume = 0f;
                    PlayState.musicControl = 0f;
                    musicOn = false;
                }
                else{
                    //turn music on 1f - full volume
                    musicOnBtn = new Texture("musicOn.png");
                    Bubble.bubbleVolume = 1f;
                    PlayState.musicControl = 1f;
                    blop.play(PlayState.musicControl);
                    musicOn = true;
                }

            }
        }

    }

    @Override
    public void update(float dt) {
        //Below only needed here if we don't pop from the stack and just push a new state on top.
        //scoreString = "High Score: " + BubbleAdventure.getPrefScore();
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

        title.draw(sb, titleString,10, 340);
        sb.draw(playBtn, playBtnXPos, playBtnYPos);
        sb.draw(musicOnBtn, musicOnBtnXPos, musicOnBtnYPos);
        scoreFont.draw(sb, scoreString, cam.position.x / 3, BubbleAdventure.HEIGHT/2 - 5);
        scoreFont.setUseIntegerPositions(false);//fixes shaking of score display

        sb.end(); //close
    }

    //call this when transitioning states
    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        musicOnBtn.dispose();
        blop.dispose();
    }
}
