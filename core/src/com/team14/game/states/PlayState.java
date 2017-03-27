package com.team14.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.team14.game.BubbleAdventure;
import com.team14.game.sprites.Bubble;
import com.team14.game.sprites.Obstacle;
import com.team14.game.sprites.Vegetable;

/**
 * Created by Arianne on 3/26/17.
 */

public class PlayState extends State {
    private static final int VEGETABLE_SPACING = 125; //space between each vegetable
    private static final int VEGETABLE_COUNT = 4;

    private Bubble bubble;
    private Texture bg;
    private Obstacle tube;

    private Array<Vegetable> vegetables;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bubble = new Bubble(50, 300);
        cam.setToOrtho(false, BubbleAdventure.WIDTH / 2, BubbleAdventure.HEIGHT / 2); //sets view on screen to a certain part of Game World
        bg = new Texture("bg.jpg");
     //   tube = new Obstacle(100);

        vegetables = new Array<Vegetable>();

        for(int i = 0; i < VEGETABLE_COUNT; i++){
            vegetables.add(new Vegetable(i * (VEGETABLE_SPACING + Vegetable.VEGETABLE_WIDTH)));
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
        }

        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined); //draws in relation to what we're viewing in game world, where in the game world we are
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0); //positions background picture to fit phone screen
        sb.draw(bubble.getTexture(), bubble.getPosition().x, bubble.getPosition().y);
       // sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
       // sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        for(Vegetable vegetable : vegetables)
            sb.draw(vegetable.getVegetable(), vegetable.getPosVegetable().x, vegetable.getPosVegetable().y);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
