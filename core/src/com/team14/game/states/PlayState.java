package com.team14.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.team14.game.BubbleAdventure;
import com.team14.game.sprites.BottomObstacle;
import com.team14.game.sprites.Bubble;
import com.team14.game.sprites.JunkFood;
import com.team14.game.sprites.TopObstacle;
import com.team14.game.sprites.Vegetable;

/**
 * Created by Arianne on 3/26/17.
 *
 *  TO DO figure out spacing issue with obstacles to keep them from overlapping or being too close to eachother
 *  Make food disappear after collision
 *  Dispose
 */

public class PlayState extends State {
    private static final int VEGETABLE_SPACING = 250; //space between each vegetable
    private static final int VEGETABLE_COUNT = 4; //one for every image
    private static final int JUNK_COUNT = 5;
    private static final int JUNK_SPACING = 150;

    private static final int OBSTACLE_SPACING = 300;
    private static final int OBSTACLE_COUNT = 2;
    //keeps track of whether or not the last state of the character was large or not. Used to determine whether score increments
    private boolean wasBig = false;

    //Determines if the bubble has collided with an obstacle for MOVEMENT and popped image
    private boolean gameover;
    private Texture gameoverImg;


    private Bubble bubble;
    private Texture bg;

    private Music music;
    private Sound over;

    private Array<Vegetable> vegetables;
    private Array<JunkFood> junkFoods;
    private Array<TopObstacle> topObstacles;
    private Array<BottomObstacle> bottomObstacles;

    private String scoreString;
    private BitmapFont scoreFont;

    /*Constructor*/
    public PlayState(GameStateManager gsm) {
        super(gsm);

        bubble = new Bubble(50, 150);
        cam.setToOrtho(false, BubbleAdventure.WIDTH / 2, BubbleAdventure.HEIGHT / 2); //sets view on screen to a certain part of Game World
        bg = new Texture("bg1.jpg");
        gameoverImg = new Texture("GameOver.png");

        music = Gdx.audio.newMusic(Gdx.files.internal("game_2.wav"));
        over = Gdx.audio.newSound(Gdx.files.internal("game_over.wav"));
        music.setLooping(true);
        music.setVolume(1.0f);
        music.play();

        //Instantiates a vegetable for every vegetable image and reuses them in update()
        //incrementing through each i allows each vegetable in the array to be instantiated
        vegetables = new Array<Vegetable>();
        for(int i = 1; i <= VEGETABLE_COUNT; i++) {
            vegetables.add(new Vegetable(i * (VEGETABLE_SPACING + Vegetable.VEGETABLE_WIDTH) + BubbleAdventure.WIDTH / 2, i - 1));
        }

        junkFoods = new Array<JunkFood>();
        for(int i = 1; i <= JUNK_COUNT; i++) {
            junkFoods.add(new JunkFood(i * (JUNK_SPACING + JunkFood.JF_WIDTH) + BubbleAdventure.WIDTH / 2, i - 1));
        }


        /* TO DO figure out spacing issue with obstacles to keep them from overlapping or being too close to eachother*/
        topObstacles= new Array<TopObstacle>();
        for(int i = 1; i <= OBSTACLE_COUNT; i++) {
            topObstacles.add(new TopObstacle(i * (OBSTACLE_SPACING + TopObstacle.OBSTACLE_WIDTH)));
        }

        bottomObstacles= new Array<BottomObstacle>();
        for(int i = 1; i <= OBSTACLE_COUNT; i++) {
            bottomObstacles.add(new BottomObstacle(i * (OBSTACLE_SPACING + BottomObstacle.OBSTACLE_WIDTH), i - 1));
        }

        //construct score string and font
        scoreString = "Score: 0";
        scoreFont = new BitmapFont();

        gameover = false;
    }

    @Override
    protected void handleInput() {

        //If user touches the screen
        if(Gdx.input.justTouched()) {

            //If the bubble has collided with the obstacle, gameover will be set to true
            //so when user touches screen, reset to the start state again and reset score
            if(gameover) {
                BubbleAdventure.resetScore();//reset score to 0 before returning
                gsm.set(new StartState(gsm));
            }

            //if gameover is still false, bubble jumps
            else
                bubble.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput(); //constantly checks input to see if user has done anything
        bubble.update(dt);
        cam.position.x = bubble.getPosition().x + 80; //updates camera position based on where character is

        for(Vegetable vegetable : vegetables) {
            if(cam.position.x - (cam.viewportWidth / 2) > vegetable.getPosVegetable().x + vegetable.getVegetable().getWidth()) {
                vegetable.reposition(vegetable.getPosVegetable().x + ((Vegetable.VEGETABLE_WIDTH + VEGETABLE_SPACING) * VEGETABLE_COUNT));
                vegetable.resetLock();//resets the lock on the object once it is out of view.
            }

            //if the rectangular bounds of vegetable overlap with the bubbles rectangular bounds
            if(vegetable.collides(bubble.getBounds())) {
                //On collision, reposition the vegetable out of the camera view towards the left of the bubble
                vegetable.reposition(cam.position.x - cam.viewportWidth);
                //if character wasn't big previously gains points
                if(!wasBig){
                    BubbleAdventure.increment();
                    scoreString = "Score: " + BubbleAdventure.getScore();//update string with now score value
                }
                bubble.shrink((int) bubble.getPosition().x,(int)bubble.getPosition().y);
                wasBig = false;//last state updated for character
            }
        }

        for(JunkFood junk : junkFoods) {
            if(cam.position.x - (cam.viewportWidth / 2) > junk.getPosJunk().x + junk.getJunk().getWidth()) {
                junk.reposition(junk.getPosJunk().x + ((JunkFood.JF_WIDTH + JUNK_SPACING) * JUNK_COUNT));
            }

            if(junk.collides(bubble.getBounds())) {
                bubble.grow((int) bubble.getPosition().x, (int) bubble.getPosition().y);
                wasBig = true;//state of the character has changed. will not be able to score until small again
                junk.reposition(cam.position.x - cam.viewportWidth);
            }
        }

        /* TO DO randomize obstacles so that they're not always in the same pattern */
        for(TopObstacle topObstacle : topObstacles) {
            if(cam.position.x - (cam.viewportWidth / 2) > topObstacle.getPosTop().x + topObstacle.getTopObstacle().getWidth())
                topObstacle.reposition(topObstacle.getPosTop().x + ((TopObstacle.OBSTACLE_WIDTH + OBSTACLE_SPACING) * OBSTACLE_COUNT));

            if(topObstacle.collides(bubble.getBounds())) {
                bubble.colliding = true;
                gameover = true;
            }

        }


        for(BottomObstacle bottomObstacle : bottomObstacles) {
            if(cam.position.x - (cam.viewportWidth / 2) > bottomObstacle.getPosBottom().x + bottomObstacle.getBottomObstacle().getWidth())
                bottomObstacle.reposition(bottomObstacle.getPosBottom().x + ((BottomObstacle.OBSTACLE_WIDTH + OBSTACLE_SPACING) * OBSTACLE_COUNT));

            if(bottomObstacle.collides(bubble.getBounds())) {
                bubble.colliding = true;
                gameover = true;

            }
        }
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined); //draws in relation to what we're viewing in game world, where in the game world we are
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0); //positions background picture to fit phone screen
        sb.draw(bubble.getTexture(), bubble.getPosition().x, bubble.getPosition().y);


        /*Rendering score output*/
        scoreFont.setColor(Color.GOLD);//can also input rgb values
        scoreFont.draw(sb, scoreString, cam.position.x - cam.viewportWidth/2, cam.viewportHeight);
        scoreFont.setUseIntegerPositions(false);//fixes shaking of score display

        for(Vegetable vegetable : vegetables) {
            sb.draw(vegetable.getVegetable(), vegetable.getPosVegetable().x, vegetable.getPosVegetable().y);
        }
        for(JunkFood junk : junkFoods) {
            sb.draw(junk.getJunk(), junk.getPosJunk().x, junk.getPosJunk().y);
        }
        for(TopObstacle topObstacle : topObstacles) {
            sb.draw(topObstacle.getTopObstacle(), topObstacle.getPosTop().x, topObstacle.getPosTop().y);
        }
        for(BottomObstacle bottomObstacle : bottomObstacles) {
            sb.draw(bottomObstacle.getBottomObstacle(), bottomObstacle.getPosBottom().x, bottomObstacle.getPosBottom().y);
        }

        if(gameover)
        {
            sb.draw(gameoverImg, cam.position.x - gameoverImg.getWidth() / 2, cam.position.y);
            over.play(1.0f);

        }


        sb.end();
    }

    @Override
    public void dispose() {
        /* TO DO dispose everything */
        bg.dispose();
        bubble.dispose();
        music.dispose();
        over.dispose();

    }
}
