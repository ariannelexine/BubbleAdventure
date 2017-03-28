package com.team14.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Arianne on 3/26/17.
 */

public class Bubble {
    private static final int GRAVITY = -10;
    private static final int MOVEMENT = 100;
    private Vector3 position;
    private Vector3 velocity;

    private Texture bubble;

    public Bubble(int x, int y){
        position = new Vector3(x, y, 0); //starting position of bubble
        velocity = new Vector3(0, 0, 0); //starting not moving

        bubble = new Texture("bubble.png");
    }

    //send delta time to bubble class and allow it to do the math to reset position in gameworld
    public void update(float dt){
        //if you don't float you fall
        //if the bubble isn't already on the ground, add gravity
        if(position.y > 0)
            velocity.add(0, GRAVITY, 0);

        velocity.scl(dt); //multiples everything by a delta time for position
        position.add(MOVEMENT * dt,velocity.y, 0);


        //if the bubble is at the ground, add hops
        if(position.y < 0) {
           velocity.y = 3;
           //position.add(MOVEMENT * dt, velocity.y , 0);
        }
        velocity.scl(1/dt);

    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return bubble;
    }

    public void jump(){
        velocity.y = 250;
    }
}
