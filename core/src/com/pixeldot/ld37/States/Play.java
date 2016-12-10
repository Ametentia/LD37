package com.pixeldot.ld37.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.pixeldot.ld37.Utilities.GameStateManager;

import static com.pixeldot.ld37.Game.WIDTH;
import static com.pixeldot.ld37.Game.HEIGHT;
import static com.pixeldot.ld37.Game.PPM;

public class Play extends State {

    private Body player;

    public Play(GameStateManager gsm) {
        super(gsm);

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
            player.applyForceToCenter(-100 / PPM, 0, true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.applyForceToCenter(100 / PPM, 0, true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            player.applyForceToCenter(0, -100 / PPM, true);
        }

        world.step(dt, 6, 2);
    }

    public void render() {

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
        room.createFixture(fdef);

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

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(9 / PPM, 16 / PPM);
        playerFDef.shape = shape;

        player.createFixture(playerFDef);

        shape.dispose();
    }
}
