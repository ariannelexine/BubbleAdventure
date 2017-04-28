package com.team14.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.team14.game.BubbleAdventure;

/**
 * Created by Arianne on 3/26/17.
 */

public class Bubble {
    private static final int GRAVITY = -10;
    private static final int MOVEMENT = 100; //speed of food/bubble in x direction.
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Sound tap;
    private Texture bubble;

    public boolean colliding;

    public int MAX_SIZE = 2; //bubbles maximum size is 2 ranging from 0 - 2

    private String[] BubbleStringArray = {"SmallBubble.png", "MediumBubble.png", "LargeBubble.png"};

    public Bubble(int x, int y){
        position = new Vector3(x, y, 0); //starting position of bubble
        velocity = new Vector3(0, 0, 0); //starting not moving
        tap = Gdx.audio.newSound(Gdx.files.internal("tap.ogg"));
        bubble = new Texture("SmallBubble.png");

        bounds = new Rectangle(x, y, bubble.getWidth(),bubble.getHeight());
        colliding = false;
    }

    //send delta time to bubble class and allow it to do the math to reset position in gameworld
    public void update(float dt){
        //if you don't float you fall
        //if the bubble isn't already on the ground, add gravity
        if(position.y > 0)
            velocity.add(0, GRAVITY, 0);

        velocity.scl(dt); //multiples everything by a delta time for position

        //If colliding is still false, comtinue to move the bubble in the x direction
        if(!colliding)
            position.add(MOVEMENT * dt,velocity.y, 0);
       //Else if the colliding has changed to true, stop the movement and replace the bubble with the popped image
        else
            bubble = new Texture("popped.png");


        //if the bubble is at the ground, add hops
        if(position.y < 0) {
            velocity.y = 3;
            position.add(MOVEMENT * dt, velocity.y , 0);
        }

        //Game screen ceiling, doesn't let bubble go past top of the screen
        //Hard coded as of right now
        if(position.y > BubbleAdventure.HEIGHT/2 - bubble.getHeight())// removed hard codded amount 350-Anil
            velocity.y = -1;

        velocity.scl(1/dt);
        bounds.setPosition(position.x, position.y);

    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return bubble;
    }

    public void jump(){
        velocity.y = 250;
        tap.play(1.0f);
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void grow(int x, int y, int size){
        bubble = new Texture(BubbleStringArray[size]);
        bounds = new Rectangle(x, y, bubble.getWidth(),bubble.getHeight());
    }

    public void shrink(int x, int y, int size){
        bubble = new Texture(BubbleStringArray[size]);
        bounds = new Rectangle(x, y, bubble.getWidth(),bubble.getHeight());
    }

    public void dispose()
    {
        bubble.dispose();
        tap.dispose();

    }
}
