package com.team14.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.team14.game.BubbleAdventure;
import com.team14.game.sprites.Bubble;
import com.team14.game.sprites.JunkFood;
import com.team14.game.sprites.Obstacle;
import com.team14.game.sprites.Vegetable;

/**
 * Created by Arianne on 3/26/17.
 */

public class PlayState extends State {
    private static final int VEGETABLE_SPACING = 150; //space between each vegetable
    private static final int VEGETABLE_COUNT = 4;
    private static final int JUNK_COUNT = 3;
    private static final int JUNK_SPACING = 275;

    private static final int OBSTACLE_COUNT = 1;

    private Bubble bubble;
    private Texture bg;

    private Array<Vegetable> vegetables;
    private Array<JunkFood> junkFoods;
    private Array<Obstacle> obstacles;

    public PlayState(GameStateManager gsm) {
        super(gsm);

        bubble = new Bubble(50, 150);
        cam.setToOrtho(false, BubbleAdventure.WIDTH / 2, BubbleAdventure.HEIGHT / 2); //sets view on screen to a certain part of Game World
        bg = new Texture("bg1.jpg");

        vegetables = new Array<Vegetable>();

        //runs once??????
        for(int i = 1; i <= VEGETABLE_COUNT; i++){
            vegetables.add(new Vegetable(i * (VEGETABLE_SPACING + Vegetable.VEGETABLE_WIDTH) + BubbleAdventure.WIDTH / 2));
        }

        junkFoods = new Array<JunkFood>();

        for(int i = 1; i <= JUNK_COUNT; i++){
            junkFoods.add(new JunkFood(i * (JUNK_SPACING + JunkFood.JF_WIDTH) + BubbleAdventure.WIDTH / 2));
        }

        obstacles = new Array<Obstacle>();

        // this part only instantiates the first obstacle, the rest are done in reposition
        for(int i = 1; i <= OBSTACLE_COUNT; i++){
            obstacles.add(new Obstacle(i * Obstacle.OBSTACLE_WIDTH));
        }


    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            bubble.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        bubble.update(dt);
        cam.position.x = bubble.getPosition().x + 80;

        for(Vegetable vegetable : vegetables){
            if(cam.position.x - (cam.viewportWidth / 2) > vegetable.getPosVegetable().x + vegetable.getVegetable().getWidth())
                vegetable.reposition(vegetable.getPosVegetable().x + ((Vegetable.VEGETABLE_WIDTH + VEGETABLE_SPACING) * VEGETABLE_COUNT));

            if(vegetable.collides(bubble.getBounds())) {
                bubble.shrink((int) bubble.getPosition().x,(int)bubble.getPosition().y);
            }

        }

        for(JunkFood junk : junkFoods){
            if(cam.position.x - (cam.viewportWidth / 2) > junk.getPosJunk().x + junk.getJunk().getWidth())
                junk.reposition(junk.getPosJunk().x + ((JunkFood.JF_WIDTH + JUNK_SPACING) * JUNK_COUNT));

            if(junk.collides(bubble.getBounds()))
                bubble.grow((int) bubble.getPosition().x,(int)bubble.getPosition().y);

        }


        for(Obstacle obstacle : obstacles){
            if(cam.position.x - (cam.viewportWidth / 2) > obstacle.getPosSign().x + obstacle.getSign().getWidth())
                obstacle.reposition(obstacle.getPosSign().x + ((Obstacle.OBSTACLE_WIDTH) * OBSTACLE_COUNT));

            // if(cam.position.x - (cam.viewportWidth / 2) > obstacle.getPosCart().x + obstacle.getCart().getWidth())
              // obstacle.reposition(obstacle.getPosCart().x + ((Obstacle.OBSTACLE_WIDTH) * OBSTACLE_COUNT));

            if(obstacle.collides(bubble.getBounds()))
                gsm.set(new GameOverState(gsm));

        }


        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined); //draws in relation to what we're viewing in game world, where in the game world we are
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0); //positions background picture to fit phone screen
        sb.draw(bubble.getTexture(), bubble.getPosition().x, bubble.getPosition().y);

        for(Vegetable vegetable : vegetables)
            sb.draw(vegetable.getVegetable(), vegetable.getPosVegetable().x, vegetable.getPosVegetable().y);


        for(JunkFood junk : junkFoods) {
            sb.draw(junk.getJunk(), junk.getPosJunk().x, junk.getPosJunk().y);
        }



        for(Obstacle obstacle : obstacles) {
            sb.draw(obstacle.getSign(),obstacle.getPosSign().x, obstacle.getPosSign().y);
           // sb.draw(obstacle.getCart(),obstacle.getPosCart().x, obstacle.getPosCart().y);
        }


        sb.end();
    }

    @Override
    public void dispose() {

    }
}
