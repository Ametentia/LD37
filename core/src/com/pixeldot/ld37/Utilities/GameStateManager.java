package com.pixeldot.ld37.Utilities;

import com.pixeldot.ld37.Game;
import com.pixeldot.ld37.States.Play;
import com.pixeldot.ld37.States.State;
import com.pixeldot.ld37.States.Testing;

import java.util.Stack;

public class GameStateManager {

    public static final int MENU = 0;
    public static final int PLAY = 1;
    public static final int PAUSE = 2;
    public static final int TESTING = 3;

    public final Game game;
    private Stack<State> states;

    public GameStateManager(Game game) {
        this.game = game;
        states = new Stack<>();
    }

    private State getState(int state) {
        switch (state) {
            case PLAY:
                return new Play(this);
            case TESTING:
                return new Testing(this);
            default:
                throw new IllegalArgumentException("Error: Unknown State");
        }
    }

    public void pushState(int state) {
        states.push(getState(state));
    }

    public void setState(int state) {
        popState();
        pushState(state);
    }

    public void popState() {
        State s = states.pop();

        if(s != null) s.dispose();
    }



    public void update(float dt) { states.peek().update(dt); }
    public void render() { states.peek().render(); }
}
