package com.team14.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Arianne on 3/27/17.
 */

public class Vegetable {
    public static final  int VEGETABLE_WIDTH = 25;
    private static final int FLUCTUATION = 450;
    private Texture vegetable;
    private Vector2 posVegetable;
    private Random rand;

    public Vegetable(float x) {
        vegetable = new Texture("tomato.png");
        rand = new Random();

        posVegetable = new Vector2(x, rand.nextInt(FLUCTUATION));
    }


    public Texture getVegetable() {
        return vegetable;
    }

    public Vector2 getPosVegetable() {
        return posVegetable;
    }

    public void reposition(float x){
        posVegetable.set(x, rand.nextInt(FLUCTUATION));
    }
}
