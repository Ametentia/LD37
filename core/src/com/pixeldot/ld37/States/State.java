package com.pixeldot.ld37.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.pixeldot.ld37.Game;
import com.pixeldot.ld37.Utilities.GameStateManager;

public abstract class State {

    protected Game game;
    protected GameStateManager gsm;

    protected OrthographicCamera camera;

    protected Box2DDebugRenderer debugRenderer;
    protected OrthographicCamera box2DCam;

    protected World world;

    protected SpriteBatch batch;
    protected ShapeRenderer renderer;

    protected final Vector3 mouse;

    public State(GameStateManager gsm) {
        this.gsm = gsm;
        game = gsm.game;

        camera = game.getCamera();

        batch = game.getBatch();
        renderer = game.getRenderer();

        world = game.getWorld();
        debugRenderer = game.getDebugRenderer();
        box2DCam = game.getBox2DCam();

        mouse = new Vector3(0, 0, 0);
    }


    public abstract void update(float dt);
    public abstract void render();

    public abstract void dispose();
}
