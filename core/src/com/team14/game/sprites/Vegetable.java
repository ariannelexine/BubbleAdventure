package com.team14.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * Created by Arianne on 3/27/17.
 */

public class Vegetable {
    public static final  int VEGETABLE_WIDTH = 25;
    private static final int FLUCTUATION = 350;

    private Texture vegetable;
    private Vector2 posVegetable;
    private Rectangle boundsVeg;
    private Random rand;
    private boolean locked;//variable to lock out collision detection after the first has been recorded.
    int count;
    boolean done;

    private String[] vegetableStringArray = {"tomato.png", "carrot.png", "watermelon.png", "grapes.png"};

    //Passes in a float for instantiating the position of the vegetable and an integer i to get the
    //image in the vegetable images array
    public Vegetable(float x, int i) {
        vegetable = new Texture(vegetableStringArray[i]);

        rand = new Random();
        posVegetable = new Vector2(x, rand.nextInt(FLUCTUATION));

        //the + & - 5 gives a little leeway for the bubble to touch food image when they're not rectangular in shape
        boundsVeg = new Rectangle(posVegetable.x + 5, posVegetable.y - 5, vegetable.getWidth(), vegetable.getHeight());

        locked = false;
    }


    public Texture getVegetable() {
        return vegetable;
    }

    public Vector2 getPosVegetable() {
        return posVegetable;
    }

    public Rectangle getBoundsVeg(){return boundsVeg;}

    public void reposition(float x, Rectangle top, Rectangle bot, Rectangle junk){

        done = false;
        count = 0;
        posVegetable.set(x, rand.nextInt(FLUCTUATION));
        boundsVeg.setPosition(posVegetable.x + 5, posVegetable.y - 5);

        while(!done){
            if(boundsVeg.contains(top))
            {
                vegetable.dispose();
                posVegetable.set(x, posVegetable.y - top.getHeight() - vegetable.getHeight());
                boundsVeg.setPosition(posVegetable.x + 5, posVegetable.y - 5);
                count++;
            }

            if(boundsVeg.contains(bot))
            {
                vegetable.dispose();
                posVegetable.set(x, posVegetable.y + bot.getHeight() + vegetable.getHeight());
                boundsVeg.setPosition(posVegetable.x, posVegetable.y);
                done = false;
                count++;
            }

            if(boundsVeg.contains(junk))
            {
                vegetable.dispose();
                posVegetable.set(x + junk.getWidth()*2, posVegetable.y);
                boundsVeg.setPosition(posVegetable.x, posVegetable.y);
                done = false;
                count++;
            }

            if(count == 0)
                done = true;

            //resets count for next loop if needed
            count = 0;

        }

    }

    public boolean collides(Rectangle player){
        if(!locked && player.overlaps(boundsVeg)) {
            locked = true;
            return player.overlaps(boundsVeg);
        }
        return false;
    }

    public void resetLock(){locked = false;}
}
