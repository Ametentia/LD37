package com.pixeldot.ld37.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.pixeldot.ld37.Entities.Player;
import com.pixeldot.ld37.Utilities.Animation;
import com.pixeldot.ld37.Utilities.CollisionListener;
import com.pixeldot.ld37.Utilities.ContentManager;
import com.pixeldot.ld37.Utilities.GameStateManager;

import static com.pixeldot.ld37.Game.WIDTH;
import static com.pixeldot.ld37.Game.HEIGHT;
import static com.pixeldot.ld37.Game.PPM;

public class Play extends State {

    private Player player;

    private Body boxBody;
    private Animation boxAnim;
    private BodyDef boxDef;
    private FixtureDef boxFDef;

    private BitmapFont font = new BitmapFont(true);

    private CollisionListener contactListener;

    private boolean isThere = true;

    public Play(GameStateManager gsm) {
        super(gsm);

        world.setContactListener(contactListener = new CollisionListener());

        ContentManager.loadTexture("PlayerRun", "Character/spritesheetSmol.png");
        ContentManager.loadTexture("Brick", "Materials/brickTexture.png");

        createRoom();
        createPlayer();
    }

    public void update(float dt) {

        if(Gdx.input.isTouched()) {
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            box2DCam.unproject(mouse);

            player.getBody().setTransform(mouse.x, mouse.y, 0);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            if(isThere) {
                boxBody = world.createBody(boxDef);
                boxBody.createFixture(boxFDef).setUserData("Floor");
                isThere = false;
            }
            else {
                world.destroyBody(boxBody);
                isThere = true;
            }
        }


        world.step(dt, 6, 2);
        player.setOnGound(contactListener.isOnGround());
        player.update(dt);
        boxAnim.setPosition(boxBody.getPosition());
        boxAnim.update(dt);
    }

    public void render() {

        batch.setProjectionMatrix(camera.combined);
        renderer.setProjectionMatrix(camera.combined);

        batch.begin();
        player.render(batch);
        boxAnim.render(batch);
        font.draw(batch, "On Ground: " + player.isOnGound(), 100, 100);
        batch.end();
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
        playerDef.linearDamping = 2;

        Body playerBody = world.createBody(playerDef);

        FixtureDef playerFDef = new FixtureDef();
        playerFDef.density = 1;
        playerFDef.friction = 0.1f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(30 / PPM, 45 / PPM);
        playerFDef.shape = shape;

        playerBody.createFixture(playerFDef);

        shape.setAsBox(16 / PPM, 8 / PPM, new Vector2(0, 49 / PPM), 0);
        playerFDef.isSensor = true;

        playerBody.createFixture(playerFDef).setUserData("Foot");

        Animation a = new Animation("Run", ContentManager.getTexture("PlayerRun"), 2, 4);
        a.setTargetWidth(60);
        a.setTargetHeight(90);

        player = new Player(playerBody, a);

        shape.dispose();

        boxDef = new BodyDef();
        boxDef.type = BodyDef.BodyType.StaticBody;
        boxDef.position.set(200 / PPM, (HEIGHT - 300) / PPM);

        boxBody = world.createBody(boxDef);

        boxFDef = new FixtureDef();

        PolygonShape box = new PolygonShape();
        box.setAsBox(100 / PPM, 100 / PPM);
        boxFDef.shape = box;

        boxBody.createFixture(boxFDef).setUserData("Floor");

        boxAnim = new Animation("Brick", ContentManager.getTexture("Brick"), 1, 1);
        boxAnim.setTargetWidth(200);
        boxAnim.setTargetHeight(200);
    }
}
