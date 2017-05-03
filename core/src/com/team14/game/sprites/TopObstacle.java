package com.team14.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.team14.game.BubbleAdventure;
import com.team14.game.states.PlayState;

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

    private String[] topObstacleArray = {"Sign1.png", "Sign2.png", "Sign1.png", "Sign2.png"};

    public TopObstacle(float x, int i) {
        topObstacle = new Texture(topObstacleArray[i]);
        posTop = new Vector2(x, (BubbleAdventure.HEIGHT / 2) - topObstacle.getHeight());
        boundsTop = new Rectangle(posTop.x, posTop.y, topObstacle.getWidth(), topObstacle.getHeight());

    }

    public Texture getTopObstacle() {
        return topObstacle;
    }

    public Vector2 getPosTop() {
        return posTop;
    }

    public Rectangle getBoundsTop(){return boundsTop;}



    public boolean collides(Rectangle player) {
        return player.overlaps(boundsTop);
    }


    public void reposition(float x) {
        random = new Random();
        int randomNum = random.nextInt(200 + 1 - 20) + 20;
        posTop.set(x + randomNum + topObstacle.getWidth(), (BubbleAdventure.HEIGHT / 2) - topObstacle.getHeight());
        boundsTop.setPosition(posTop.x, posTop.y);

    }

    public void dispose() {
        topObstacle.dispose();
    }
}

