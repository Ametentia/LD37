package com.pixeldot.ld37;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pixeldot.ld37.Utilities.GameStateManager;

public class Game extends ApplicationAdapter {

    public static final float PPM = 100;

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public static final float DELTA = 1 / 60f;
    private float accum;

    private GameStateManager gsm;

    private OrthographicCamera camera;
    private Viewport viewport;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Viewport debugViewport;
    private OrthographicCamera box2DCam;

    private SpriteBatch batch;
    private ShapeRenderer renderer;

	@Override
	public void create () {
        camera = new OrthographicCamera();
        camera.setToOrtho(true, WIDTH, HEIGHT);

        viewport = new ExtendViewport(WIDTH, HEIGHT, camera);
        viewport.apply();

        debugRenderer = new Box2DDebugRenderer();
        debugRenderer.setDrawBodies(true);
        //debugRenderer.setDrawAABBs(true);

        world = new World(new Vector2(0, 9.81f), true);
        box2DCam = new OrthographicCamera();
        box2DCam.setToOrtho(true, WIDTH / PPM, HEIGHT / PPM);

        debugViewport = new ExtendViewport(WIDTH / PPM, HEIGHT / PPM, box2DCam);
        debugViewport.apply();

        batch = new SpriteBatch();
        renderer = new ShapeRenderer();

        gsm = new GameStateManager(this);
        gsm.pushState(GameStateManager.TESTING);

        accum = 0;
	}

	@Override
	public void render () {

        accum += Gdx.graphics.getDeltaTime();
        while (accum >= DELTA) {
            accum -= DELTA;
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

            gsm.update(DELTA);
            gsm.render();

            if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        }
	}

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        debugViewport.update(width, height, true);
    }

    @Override
	public void dispose () {
        batch.dispose();
        renderer.dispose();
        world.dispose();
    }

    public OrthographicCamera getCamera() { return camera; }

    public World getWorld() { return world; }
    public Box2DDebugRenderer getDebugRenderer() { return debugRenderer; }
    public OrthographicCamera getBox2DCam() { return box2DCam; }

    public SpriteBatch getBatch() { return batch; }
    public ShapeRenderer getRenderer() { return renderer; }
}
