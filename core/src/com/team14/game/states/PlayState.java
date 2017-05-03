package com.team14.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
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
    private static final int VEGETABLE_SPACING = 150; //space between each vegetable
    private static final int VEGETABLE_COUNT = 4; //one for every image
    private static final int JUNK_COUNT = 5;
    private static final int JUNK_SPACING = 200;

    private static final int OBSTACLE_SPACING = 200;
    private static final int OBSTACLE_COUNT = 4;
    //keeps track of whether or not the last state of the character was large or not. Used to determine whether score increments
    private boolean wasBig = false;
    private double delay = 0.5;//delay timer for sound in seconds.

    private int size; //Bubbles current size

    //Determines if the bubble has collided with an obstacle for MOVEMENT and popped image
    private boolean gameover;
    private Texture gameoverImg;


    private Bubble bubble;
    private Texture bg;

    private Music music;
    private Sound over;
    private Sound blop;
    private Sound healthPickup;
    private Sound junkPickup;
    public static float musicControl = 1f;

    private Array<Vegetable> vegetables;
    private Array<JunkFood> junkFoods;
    private Array<TopObstacle> topObstacles;
    private Array<BottomObstacle> bottomObstacles;
    /*These will be used to check and see if any of our game objects are overlapping*/
    public Vector2 last_posTop;
    public Vector2 last_posBot;
    public Vector2 last_posJunk;
    public Vector2 last_posVeg;
    public Rectangle last_boundsTop;
    public Rectangle last_boundsBot;
    public Rectangle last_boundsJunk;
    public Rectangle last_boundsVeg;

    /*Used for score display*/
    private String scoreString;
    private BitmapFont scoreFont;
    int count;
    /*Constructor*/
    public PlayState(GameStateManager gsm) {
        super(gsm);

        int count = 0;
        bubble = new Bubble(50, 150);
        size = 0;
        cam.setToOrtho(false, BubbleAdventure.WIDTH / 2, BubbleAdventure.HEIGHT / 2); //sets view on screen to a certain part of Game World
        bg = new Texture("bg.jpg");
        gameoverImg = new Texture("GameOver.png");

        music = Gdx.audio.newMusic(Gdx.files.internal("game_2.wav"));
        over = Gdx.audio.newSound(Gdx.files.internal("game_over.wav"));
        blop = Gdx.audio.newSound(Gdx.files.internal("Blop.wav"));
        junkPickup = Gdx.audio.newSound(Gdx.files.internal("Powerup14.wav"));
        healthPickup = Gdx.audio.newSound(Gdx.files.internal("Pickup_Coin4.wav"));
        music.setLooping(true);
        music.setVolume(musicControl);
        music.play();

        last_posTop = new Vector2(0,0);
        last_posBot = new Vector2(0,0);
        last_posJunk = new Vector2(0,0);
        last_posVeg = new Vector2(0,0);

        last_boundsTop = new Rectangle(0,0,0,0);
        last_boundsBot = new Rectangle(0,0,0,0);
        last_boundsJunk = new Rectangle(0,0,0,0);
        last_boundsVeg = new Rectangle(0,0,0,0);

        //Instantiates a vegetable for every vegetable image and reuses them in update()
        //incrementing through each i allows each vegetable in the array to be instantiated
        vegetables = new Array<Vegetable>();
        for(int i = 1; i <= VEGETABLE_COUNT; i++) {
            vegetables.add(new Vegetable(i * (VEGETABLE_SPACING + Vegetable.VEGETABLE_WIDTH) + BubbleAdventure.WIDTH / 2, i - 1));
            //looks at top of the stack to set the last positions and boundries
            //last_posVeg.set(vegetables.peek().getPosVegetable());
            //last_boundsVeg.set(vegetables.peek().getBoundsVeg());
        }

        junkFoods = new Array<JunkFood>();
        for(int i = 1; i <= JUNK_COUNT; i++) {
            junkFoods.add(new JunkFood(i * (JUNK_SPACING + JunkFood.JF_WIDTH) + BubbleAdventure.WIDTH / 2, i - 1));
            //last_posJunk.set(junkFoods.peek().getPosJunk());
            //last_boundsJunk.set(junkFoods.peek().getBoundsJunk());
        }


        /* TO DO figure out spacing issue with obstacles to keep them from overlapping or being too close to eachother*/
        topObstacles= new Array<TopObstacle>();
        for(int i = 1; i <= OBSTACLE_COUNT; i++) {
            topObstacles.add(new TopObstacle(last_posTop.x + (OBSTACLE_SPACING + last_boundsTop.getWidth()), i - 1));
            last_posTop.set(topObstacles.peek().getPosTop());
            last_boundsTop.set(topObstacles.peek().getBoundsTop());
        }

        bottomObstacles= new Array<BottomObstacle>();
        for(int i = 1; i <= OBSTACLE_COUNT; i++) {
            bottomObstacles.add(new BottomObstacle(last_posBot.x + (OBSTACLE_SPACING + last_boundsBot.getWidth()), i - 1));
            last_posBot.set(bottomObstacles.peek().getPosBottom());
            last_boundsBot.set(bottomObstacles.peek().getBoundsBottom());
        }

        //construct score string and font
        scoreString = "Score: 0";
        scoreFont = new BitmapFont(Gdx.files.internal("score.fnt"));
        scoreFont.getData().setScale(.8f);

        gameover = false;
    }

    @Override
    protected void handleInput() {

        //If user touches the screen
        if(Gdx.input.justTouched()) {

            //If the bubble has collided with the obstacle, gameover will be set to true
            //so when user touches screen, reset to the MenuState again and reset score
            if(gameover) {
                boolean tempMusicOn = MenuState.musicOn;
                String tempMusicBtn = MenuState.musicBtn;
                gsm.set(new MenuState(gsm));
                MenuState.musicOn= tempMusicOn;
                MenuState.musicBtn= tempMusicBtn;
            }

            //if game over is still false, bubble jumps
            else
                bubble.jump();
        }
    }

    @Override
    public void update(float dt) {
        //plays game over sound only once upon death. All items here only need to be updated once the game is over.
        if (gameover && (count == 0)){
            music.stop();
            blop.play(musicControl);
            /*delays the game over music so it doesn't play on top of popping souund(blop.wave).
             *DO NOT CHANGE THE delay VALUE. Casting from double to float might lead to issues with other values.*/
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    over.play(musicControl);
                }
            }, (float)delay);

            //over.dispose(); can dispose here but left it in our dispose method. Either way, shouldn't matter.
            //check and see if a new high score was reached. If so it will be stored in preferences
            BubbleAdventure.checkHighScore(BubbleAdventure.getScore());
            BubbleAdventure.resetScore();//reset score to 0 before returning
            bubble.reset();//resets movement speed to initial value
            count++;

        }
        handleInput(); //constantly checks input to see if user has done anything
        bubble.update(dt);
        cam.position.x = bubble.getPosition().x + 80; //updates camera position based on where character is

        /* TO DO randomize obstacles so that they're not always in the same pattern
         * I moved the top and bottom obstacle reposition checks here as those should be updated before we reposition the food.
         * This is because of how our reposition checks work for food. Obstacle repositions will not take into account where food is, but
         * food will take into account where the last food position and last obstacle positions are.*/
        for(TopObstacle topObstacle : topObstacles) {
            if(cam.position.x - (cam.viewportWidth / 2) > topObstacle.getPosTop().x + topObstacle.getTopObstacle().getWidth()) {
                topObstacle.reposition(last_posTop.x + ((last_boundsTop.getWidth() + OBSTACLE_SPACING)), last_boundsTop);
                //update the last position and boundary
                last_posTop.set(topObstacle.getPosTop());
                last_boundsTop.set(topObstacle.getBoundsTop());
            }


            if(topObstacle.collides(bubble.getBounds())) {
                //System.out.printf("Collide\n");

                bubble.colliding = true;
                gameover = true;
            }

        }


        for(BottomObstacle bottomObstacle : bottomObstacles) {
            if(cam.position.x - (cam.viewportWidth / 2) > bottomObstacle.getPosBottom().x + bottomObstacle.getBottomObstacle().getWidth()) {
                bottomObstacle.reposition(last_boundsBot.x + ((BottomObstacle.OBSTACLE_WIDTH + OBSTACLE_SPACING)), last_boundsBot);
                //update the last position and boundry
                last_posBot.set(bottomObstacle.getPosBottom());
                last_boundsBot.set(bottomObstacle.getBoundsBottom());
            }

            if(bottomObstacle.collides(bubble.getBounds())) {
                bubble.colliding = true;
                gameover = true;

            }
        }

        for(Vegetable vegetable : vegetables) {
            if(cam.position.x - (cam.viewportWidth / 2) > vegetable.getPosVegetable().x + vegetable.getVegetable().getWidth()) {
                vegetable.reposition(vegetable.getPosVegetable().x + ((Vegetable.VEGETABLE_WIDTH + VEGETABLE_SPACING) * VEGETABLE_COUNT), last_boundsTop, last_boundsBot, last_boundsJunk);
                vegetable.resetLock();//resets the lock on the object once it is out of view.
                last_posJunk.set(vegetable.getPosVegetable());
                last_boundsJunk.set(vegetable.getBoundsVeg());
            }

            //if the rectangular bounds of vegetable overlap with the bubbles rectangular bounds
            if(vegetable.collides(bubble.getBounds())) {
                healthPickup.play(musicControl);
                //On collision, reposition the vegetable out of the camera view towards the left of the bubble
                vegetable.reposition(cam.position.x - cam.viewportWidth, last_boundsTop, last_boundsBot, last_boundsJunk);
                last_posJunk.set(vegetable.getPosVegetable());
                last_boundsJunk.set(vegetable.getBoundsVeg());

                //if character wasn't big previously gains points
                if(!wasBig){
                    BubbleAdventure.increment();
                    scoreString = "Score: " + BubbleAdventure.getScore();//update string with new score value
                }
                if(size != 0) {
                    size--;
                    bubble.shrink((int) bubble.getPosition().x, (int) bubble.getPosition().y, size);
                }
                wasBig = false;//last state updated for character
            }
        }

        for(JunkFood junk : junkFoods) {
            if(cam.position.x - (cam.viewportWidth / 2) > junk.getPosJunk().x + junk.getJunk().getWidth()) {
                junk.reposition(junk.getPosJunk().x + ((JunkFood.JF_WIDTH + JUNK_SPACING) * JUNK_COUNT), last_boundsTop, last_boundsBot, last_boundsVeg);
                last_posJunk.set(junk.getPosJunk());
                last_boundsJunk.set(junk.getBoundsJunk());
            }

            if(junk.collides(bubble.getBounds())) {
                junkPickup.play(musicControl);
                if(size != bubble.MAX_SIZE) {
                    junk.reposition(cam.position.x - cam.viewportWidth, last_boundsTop, last_boundsBot, last_boundsVeg);
                    size++;
                    bubble.grow((int) bubble.getPosition().x, (int) bubble.getPosition().y, size);
                    wasBig = true;//state of the character has changed. will not be able to score until small again

                }
                else{
                    //blop.play();
                    junk.reposition(cam.position.x - cam.viewportWidth, last_boundsTop, last_boundsBot, last_boundsVeg);
                    bubble.colliding = true;
                    gameover = true;
                }
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

        /*Rendering score output*/
        scoreFont.draw(sb, scoreString, cam.position.x - cam.viewportWidth/2, cam.viewportHeight);
        scoreFont.setUseIntegerPositions(false);//fixes shaking of score display

        if(gameover)
        {
            sb.draw(gameoverImg, cam.position.x - gameoverImg.getWidth() / 2, cam.position.y);


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
        blop.dispose();
        junkPickup.dispose();
        healthPickup.dispose();

    }
}


