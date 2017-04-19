package com.team14.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Arianne on 4/18/17.
 */

public class TopObstacle {
    public static final int OBSTACLE_WIDTH = 130;
    private Texture topObstacle;
    private Vector2 posTop;
    private Rectangle boundsTop;
    private Random random;

    public TopObstacle(float x) {
        topObstacle = new Texture("sign.png");
        posTop = new Vector2(x, 325);
        boundsTop = new Rectangle(posTop.x, posTop.y, topObstacle.getWidth(), topObstacle.getHeight());

    }

    public Texture getTopObstacle() {
        return topObstacle;
    }

    public Vector2 getPosTop() {
        return posTop;
    }



    public boolean collides(Rectangle player) {
        return player.overlaps(boundsTop);
    }


    public void reposition(float x) {
        random = new Random();
        int randomNum = random.nextInt(500 + 1 - 125) + 125;
        posTop.set(x + randomNum, 325);
        boundsTop.setPosition(posTop.x, posTop.y);

    }

    public void dispose() {
        topObstacle.dispose();
    }
}

