package com.team14.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Arianne on 3/29/17.
 */

public class Obstacle {
    public static final int OBSTACLE_WIDTH = 130;
    private Texture sign, cart;
    private Vector2 posSign, posCart;
    private Rectangle boundsSign, boundsCart;
    private Random random;

    public Obstacle(float x) {
        sign = new Texture("sign.png");
      //  cart = new Texture("cart.png");

        posSign = new Vector2(x, 325);
       // posCart = new Vector2(x + 250, 0);
        boundsSign = new Rectangle(posSign.x, posSign.y, sign.getWidth(), sign.getHeight());
       // boundsCart = new Rectangle(posCart.x, posCart.y, cart.getWidth(), cart.getHeight());
    }

    public Texture getSign() {
        return sign;
    }

    public Vector2 getPosSign() {
        return posSign;
    }



    public boolean collides(Rectangle player) {
        return player.overlaps(boundsSign);
    }

    public Texture getCart() {
        return cart;
    }

    public Vector2 getPosCart() {
        return posCart;
    }

    public void reposition(float x) {
        random = new Random();
        int randomNum = random.nextInt(500 + 1 - 125) + 125;
        posSign.set(x + randomNum, 325);
      //  System.out.println(x + randomNum);
      //  posCart.set(x, 0);
        boundsSign.setPosition(posSign.x, posSign.y);
        // boundsCart.setPosition(posCart.x, posCart.y);
    }
}
