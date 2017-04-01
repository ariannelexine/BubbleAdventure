package com.team14.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * Created by Arianne on 3/27/17.
 */

public class JunkFood {
    public static final  int JF_WIDTH = 30;
    private static final int FLUCTUATION = 450;
    private Texture junk;
    private Vector2 posJunk;
    private Rectangle boundsJunk;
    private Random rand;

    public JunkFood(float x) {
        junk = new Texture("cupcake.png");

        rand = new Random();

        posJunk = new Vector2(x, rand.nextInt(FLUCTUATION));

        boundsJunk = new Rectangle(posJunk.x, posJunk.y, junk.getWidth(), junk.getHeight());
    }


    public Texture getJunk() {
        return junk;
    }

    public Vector2 getPosJunk() {
        return posJunk;
    }

    public void reposition(float x){
        posJunk.set(x, rand.nextInt(FLUCTUATION));
        boundsJunk.setPosition(posJunk.x, posJunk.y);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsJunk);
    }
}