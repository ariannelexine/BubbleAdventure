package com.team14.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by Arianne on 3/17/17.
 * GameStateManager manages the states of the game.
 * ex we have a play state, and the player pauses, the pause state is put on top of
 * the game state. So then the pause state is being updated and rendered. If we unpause, the
 * pause state gets removed and the active state is the play state again.
 * Best done with STACK of states. Renders the one on top.
 */

public class GameStateManager {

    private Stack<State> states; //stack of states - last in first out

    public GameStateManager() {
        states = new Stack<State>();
    }

    public void push(State state) {
        states.push(state);
    }

    public void pop() {
        states.pop().dispose();
    }

    //pop a state and immediately push one on
    public void set(State state){
        states.pop().dispose();//should dispose of a state we are no longer using to avoid memory leaks
        states.push(state);
    }

    //look at top of the stack
    public void update(float dt) {
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }

}