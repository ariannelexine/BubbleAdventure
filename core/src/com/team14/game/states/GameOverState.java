package com.team14.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.team14.game.BubbleAdventure;

/**
 * Created by parsh on 4/23/2017.
 */

public class GameOverState extends State {

    private boolean new_high_score;
    private Texture startBubble, bg, main_btn;
    private final float btn_xpos, btn_ypos;//, btn_width, btn_height;
    protected GameOverState(GameStateManager gsm) {
        super(gsm);
        new_high_score = BubbleAdventure.checkHighScore(BubbleAdventure.getScore());
        BubbleAdventure.resetScore();//reset score to 0 before returning
        cam.setToOrtho(false, BubbleAdventure.WIDTH / 2, BubbleAdventure.HEIGHT / 2);
        bg = new Texture("bg1.jpg");
        main_btn = new Texture("main_menu_bttn.png");
        startBubble = new Texture("SmallBubble.png");
        btn_xpos = cam.viewportWidth/2 - main_btn.getWidth()/4;
        btn_ypos = cam.viewportHeight/2;
        //used for resizing purposes. Size too big for screen otherwise
        //btn_width = cam.viewportWidth/2;
        //btn_height = cam.viewportHeight/4;
    }

    @Override
    protected void handleInput() {
        /* This should only go to the main menu if only the area specified is touched*/
        if(Gdx.input.justTouched())
        {
                Vector3 tmp=new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
                cam.unproject(tmp);//can't unproject vector 2 so vector3 used
                Rectangle textureBounds=new Rectangle(btn_xpos,btn_ypos, main_btn.getWidth(), main_btn.getHeight());
                // texture btn_xpos is the x position of the texture
                // texture btn_ypos is the y position of the texture
                // btn_width is the width of the texture (you can get it with texture.getWidth() or textureRegion.getRegionWidth() if you have a texture region
                // btn_height is the height of the texture (you can get it with texture.getHeight() or textureRegion.getRegionhHeight() if you have a texture region
                if(textureBounds.contains(tmp.x,tmp.y))
                {
                    gsm.pop();// you are touching your texture so goes back to main menu screen
                }
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
        sb.draw(main_btn, btn_xpos, btn_ypos);
        sb.draw(startBubble, cam.viewportWidth/2, 0);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
