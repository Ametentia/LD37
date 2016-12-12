package com.pixeldot.ld37.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.pixeldot.ld37.Entities.Player;
import com.pixeldot.ld37.Entities.WorldObject;
import com.pixeldot.ld37.Entities.WorldObjects.Box;
import com.pixeldot.ld37.Entities.WorldObjects.Door;
import com.pixeldot.ld37.Entities.WorldObjects.Room;
import com.pixeldot.ld37.Entities.WorldObjects.Switch;
import com.pixeldot.ld37.Utilities.*;

import java.util.ArrayList;

import static com.pixeldot.ld37.Game.HEIGHT;
import static com.pixeldot.ld37.Game.PPM;
import static com.pixeldot.ld37.Game.WIDTH;

public class Testing extends State {

    private ArrayList<WorldObject> worldObjects;
    private Player player;
    private CollisionListener collisionListener;

    public Testing(GameStateManager gsm) {
        super(gsm);

        worldObjects = new ArrayList<>();
        world.setContactListener(collisionListener = new CollisionListener());

        ContentManager.loadTexture("PlayerRun", "Character/spritesheetSmol.png");
        ContentManager.loadTexture("PlayerWall", "Character/pushSpriteSheet.png");
        ContentManager.loadTexture("PlayerIdle", "Character/idleSpriteSheet.png");
        ContentManager.loadTexture("Rice", "Materials/SpringSummerAutumnBackground.png");
        ContentManager.loadTexture("Background", "Materials/Background.png");
        ContentManager.loadTexture("LeftAutumn", "Side Panels/autumnLeftSlide.png");
        ContentManager.loadTexture("RightAutumn", "Side Panels/autumnRightSlide.png");
        ContentManager.loadTexture("Diamond", "Materials/diamondTexture.png");

        ContentManager.loadTexture("DoorClosed", "Materials/door.png");
        ContentManager.loadTexture("DoorOpen", "Character/doorSpritesheet.png");
        ContentManager.loadTexture("DoorClose", "Character/doorClosingSpritesheet.png");

        ContentManager.loadTexture("SwitchOff", "World Objects/leverOFF.png");
        ContentManager.loadTexture("SwitchOn", "World Objects/leverON.png");

        Room room = new Room(BodyFactory.getWorldBody(world));
        room.setName("MainRoom");
        worldObjects.add(room);

        Door entrance = new Door(BodyFactory.getBody(world, new Vector2(337, HEIGHT - 75), new Vector2(100, 85), BodyDef.BodyType.StaticBody), false);
        entrance.getBody().getFixtureList().get(0).setSensor(true);

        worldObjects.add(entrance);

        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Box box = new Box(BodyFactory.getBody(world, new Vector2(640, 360), new Vector2(100, 100), BodyDef.BodyType.DynamicBody));
        box.setName("Box");
        box.setWidth(100);
        box.setHeight(100);

        Body boxBody = box.getBody();

        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        shape.setAsBox(8 / PPM, 8 / PPM, new Vector2(-50 / PPM, 0), 0);
        boxBody.createFixture(fixtureDef).setUserData("BoxLeft");

        shape.setAsBox(8 / PPM, 8 / PPM, new Vector2(50 / PPM, 0), 0);
        boxBody.createFixture(fixtureDef).setUserData("BoxRight");
        worldObjects.add(box);

        Switch swtch = new Switch(BodyFactory.getBody(world, new Vector2(WIDTH - 400, HEIGHT - 100), new Vector2(60, 90), BodyDef.BodyType.StaticBody), entrance);
        swtch.setName("Switch");
        swtch.getBody().getFixtureList().get(0).setSensor(true);

        worldObjects.add(swtch);


        Animation idle = new Animation("Idle", ContentManager.getTexture("PlayerIdle"), 6, 4);
        idle.setTargetHeight(90);
        idle.setTargetWidth(60);
        player = new Player(BodyFactory.getBody(world, new Vector2(400, 400), new Vector2(60, 90), BodyDef.BodyType.DynamicBody), world, idle);
        player.setName("Player");

        Body pBody = player.getBody();
        fixtureDef.isSensor = true;

        shape.setAsBox(15 / PPM, 6 / PPM, new Vector2(0, 45 / PPM), 0);
        pBody.createFixture(fixtureDef).setUserData("Foot");

        worldObjects.add(player);

        collisionListener.registerWorldObject(swtch);
        collisionListener.registerWorldObject(entrance);
        collisionListener.registerWorldObject(box);
        collisionListener.registerWorldObject(player);
        collisionListener.registerWorldObject(room);
    }

    public void update(float dt) {
        if(Gdx.input.isTouched()) {
            player.getBody().setTransform(Gdx.input.getX() / PPM, Gdx.input.getY() / PPM, 0);
        }

        for(WorldObject worldObject : worldObjects) {
            worldObject.update(dt);
        }

        world.step(dt, 6, 2);
    }
    public void render() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for(WorldObject worldObject : worldObjects) {
            worldObject.render(batch);
        }
        batch.end();

        // Debug Render Bodies
        debugRenderer.render(world, box2DCam.combined);
    }

    public void dispose() {}
}
