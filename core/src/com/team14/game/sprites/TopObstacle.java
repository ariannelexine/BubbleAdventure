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

    public TopObstacle(float x) {
        topObstacle = new Texture("sign.png");
        random = new Random();
        int randomNum = random.nextInt(((BubbleAdventure.WIDTH / 2) + 1 - topObstacle.getWidth()) + topObstacle.getWidth());
        posTop = new Vector2(x + randomNum, 325);
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


    public void reposition(float x, Rectangle last) {
        random = new Random();
        int randomNum = random.nextInt( ((BubbleAdventure.WIDTH * 2) + 1 - topObstacle.getWidth()) + topObstacle.getWidth());
        posTop.set(x + randomNum, 325);
        boundsTop.setPosition(posTop.x, posTop.y);

        //If the reposition overlaps the last known reposition boundry adjust be moving the obstacle by its width
        while(boundsTop.overlaps(last))
        {
            //System.out.printf("overlap\n");//testing purposes
            posTop.set(x + randomNum + topObstacle.getWidth(), 325);
            boundsTop.setPosition(posTop.x, posTop.y);
            x+=randomNum;
        }

    }

    public void dispose() {
        topObstacle.dispose();
    }
}

