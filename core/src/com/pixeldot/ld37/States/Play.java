package com.pixeldot.ld37.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.pixeldot.ld37.Utilities.CollisionListener;
import com.pixeldot.ld37.Utilities.GameStateManager;

import static com.pixeldot.ld37.Game.WIDTH;
import static com.pixeldot.ld37.Game.HEIGHT;
import static com.pixeldot.ld37.Game.PPM;

public class Play extends State {

    private Body player;
    public static boolean bodyOnGround = false;

    private BitmapFont font = new BitmapFont(true);

    public Play(GameStateManager gsm) {
        super(gsm);

        world.setContactListener(new CollisionListener());

        createRoom();
        createPlayer();
    }

    public void update(float dt) {
        if(Gdx.input.isTouched()) {
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            box2DCam.unproject(mouse);

            player.setTransform(mouse.x, mouse.y, 0);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.applyForceToCenter(-20 / PPM, 0, true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.applyForceToCenter(20 / PPM, 0, true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && bodyOnGround) {
            player.applyForceToCenter(0, -450 / PPM, true);
        }

        world.step(dt, 6, 2);
    }

    public void render() {

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, "On Ground: " + bodyOnGround, 100, 100);
        batch.end();
        //renderer.setProjectionMatrix(box2DCam.combined);
        debugRenderer.render(world, box2DCam.combined);
    }

    public void dispose() {}

    private void createRoom() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(WIDTH / PPM, 20 / PPM, new Vector2(-(WIDTH / 2) / PPM, ((HEIGHT / 2) - 10) / PPM), 0);

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((WIDTH / 2) / PPM, (HEIGHT / 2) / PPM);

        Body room = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.friction = 0.2f;
        room.createFixture(fdef).setUserData("Floor");

        shape.setAsBox(20 / PPM, HEIGHT / PPM, new Vector2(((-WIDTH / 2) + 10) / PPM, 0), 0);
        room.createFixture(fdef);

        shape.setAsBox(20 / PPM, HEIGHT / PPM, new Vector2(((WIDTH / 2) - 10) / PPM, 0), 0);
        room.createFixture(fdef);

        shape.setAsBox(WIDTH / PPM, 20 / PPM, new Vector2(0, ((-HEIGHT / 2) - 10) / PPM), 0);
        room.createFixture(fdef);

        shape.dispose();
    }
    private void createPlayer() {
        BodyDef playerDef = new BodyDef();
        playerDef.position.set(100 / PPM, (HEIGHT - 100) / PPM);
        playerDef.type = BodyDef.BodyType.DynamicBody;
        playerDef.active = true;
        playerDef.allowSleep = true;
        playerDef.fixedRotation = true;

        player = world.createBody(playerDef);

        FixtureDef playerFDef = new FixtureDef();
        playerFDef.density = 1;
        playerFDef.friction = 0.2f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(9 / PPM, 16 / PPM);
        playerFDef.shape = shape;

        player.createFixture(playerFDef);

        shape.setAsBox(4 / PPM, 4 / PPM, new Vector2(0, 16 / PPM), 0);
        playerFDef.isSensor = true;

        player.createFixture(playerFDef).setUserData("Foot");

        shape.dispose();
    }
}
