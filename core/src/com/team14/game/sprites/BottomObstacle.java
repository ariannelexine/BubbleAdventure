package com.team14.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.team14.game.BubbleAdventure;

import java.util.Random;

/**
 * Created by Arianne on 4/18/17.
 */

public class BottomObstacle {
    public static final int OBSTACLE_WIDTH = 130;
    private Texture bottomObstacle;
    private Vector2 posBottom;
    private Rectangle boundsBottom;
    private Random rand;

    private String[] botObstacleArray = {"cart.png", "wetfloor.png", "cart.png", "wetfloor.png"};

    public BottomObstacle(float x, int i) {
        rand = new Random();
        bottomObstacle = new Texture(botObstacleArray[i]);
        posBottom = new Vector2(x + 250, 0);
        //the + & - 20 gives a little leeway for the bubble to touch obstacle image when they're not rectangular in shape
        boundsBottom = new Rectangle(posBottom.x + 10, posBottom.y - 10, bottomObstacle.getWidth(), bottomObstacle.getHeight());

    }

    public Texture getBottomObstacle() {
        return bottomObstacle;
    }

    public Vector2 getPosBottom() {
        return posBottom;
    }

    public Rectangle getBoundsBottom() {return boundsBottom;}



    public boolean collides(Rectangle player) {
        return player.overlaps(boundsBottom);
    }


    public void reposition(float x) {
        rand = new Random();
        //Random number between 20 and 200
        int randomNum = rand.nextInt(200 + 1 - 20) + 20;
        posBottom.set(x + randomNum + 150, 0);
        boundsBottom.setPosition(posBottom.x + 10, posBottom.y - 10);

    }

    public void dispose() {
        bottomObstacle.dispose();
    }
}
