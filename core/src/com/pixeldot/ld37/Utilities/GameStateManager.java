package com.pixeldot.ld37.Utilities;

import com.pixeldot.ld37.Game;
import com.pixeldot.ld37.States.*;

import java.util.Stack;

public class GameStateManager {

    public static final int MENU = 0;
    public static final int CREDITS = 7;
    public static final int PLAY = 1;
    public static final int PAUSE = 2;
    public static final int LEVEL1 = 3;
    public static final int LEVEL2 = 4;
    public static final int LEVEL3 = 5;
    public static final int LEVEL4 = 6;
    public static final int LEVEL5 = 8;
    public static final int LEVEL6 = 9;
    public static final int LEVEL7 = 10;
    public static final int LEVEL8 = 11;

    public static final int[] LEVELS= {LEVEL1,LEVEL2,LEVEL3,LEVEL4,LEVEL5,LEVEL6,LEVEL7,LEVEL8};

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
            case LEVEL1:
                return new Level(this, 1);
            case LEVEL2:
                return new Level(this, 2);
            case LEVEL3:
                return new Level(this, 3);
            case LEVEL4:
                return new Level(this, 4);
            case LEVEL5:
                return new Level(this, 5);
            case LEVEL6:
                return new Level(this, 6);
            case LEVEL7:
                return new Level(this, 7);
            case LEVEL8:
                return new Level(this, 8);
            case MENU:
                return new MainMenu(this);
            case CREDITS:
                return new CreditScreen(this);

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
