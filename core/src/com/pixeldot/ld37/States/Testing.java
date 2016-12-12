package com.pixeldot.ld37.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.pixeldot.ld37.Entities.Player;
import com.pixeldot.ld37.Entities.WorldObject;
import com.pixeldot.ld37.Entities.WorldObjects.*;
import com.pixeldot.ld37.Utilities.*;

import java.util.ArrayList;

import static com.pixeldot.ld37.Game.HEIGHT;
import static com.pixeldot.ld37.Game.PPM;
import static com.pixeldot.ld37.Game.WIDTH;

public class Testing extends State {

    private ArrayList<WorldObject> worldObjects;
    private Player player;
    private CollisionListener collisionListener;
    private Door exit;

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
        ContentManager.loadTexture("Brick", "Materials/brickTexture.png");

        ContentManager.loadTexture("DoorClosed", "Materials/door.png");
        ContentManager.loadTexture("DoorOpen", "Character/doorSpritesheet.png");
        ContentManager.loadTexture("DoorClose", "Character/doorClosingSpritesheet.png");

        ContentManager.loadTexture("SwitchOff", "World Objects/leverOFF.png");
        ContentManager.loadTexture("SwitchOn", "World Objects/leverON.png");

        Room room = new Room(BodyFactory.getRoomBody(world));
        room.setName("MainRoom");
        worldObjects.add(room);

        Door entrance = new Door(BodyFactory.getBody(world, new Vector2(350, HEIGHT - 86), new Vector2(125, 106), BodyDef.BodyType.StaticBody), false);
        entrance.getBody().getFixtureList().get(0).setSensor(true);
        entrance.setName("EntranceDoor");
        worldObjects.add(entrance);

        exit = new Door(BodyFactory.getBody(world, new Vector2(933, HEIGHT - 86), new Vector2(125, 106), BodyDef.BodyType.StaticBody), true);
        exit.getBody().getFixtureList().get(0).setSensor(true);
        exit.setName("ExitDoor");
        worldObjects.add(exit);


        Block block = new Block(BodyFactory.getBody(world, new Vector2(WIDTH / 2, HEIGHT / 2), new Vector2(100, HEIGHT), BodyDef.BodyType.KinematicBody));
        block.setName("Pillar");
        block.setWidth(100);
        block.setHeight(HEIGHT);
        block.addState(new Vector2(WIDTH / 2, HEIGHT / 2));
        block.addState(new Vector2(WIDTH / 2, HEIGHT + (HEIGHT / 2)));

        worldObjects.add(block);

        Switch swtch = new Switch(BodyFactory.getBody(world, new Vector2(500, HEIGHT - 100), new Vector2(60, 90), BodyDef.BodyType.StaticBody), block);
        swtch.setName("Switch");
        swtch.getBody().getFixtureList().get(0).setSensor(true);

        worldObjects.add(swtch);

        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = shape;

        Animation idle = new Animation("Idle", ContentManager.getTexture("PlayerIdle"), 6, 4);
        idle.setTargetHeight(90);
        idle.setTargetWidth(60);
        player = new Player(BodyFactory.getBody(world, new Vector2(400, 400), new Vector2(60, 90), BodyDef.BodyType.DynamicBody), world, idle);
        player.setName("Player");
        Animation a = new Animation("Run", ContentManager.getTexture("PlayerRun"), 2, 4);
        a.setTargetWidth(60);
        a.setTargetHeight(90);
        player.addAnimation(a);
        a = new Animation("SquishFace",ContentManager.getTexture("PlayerWall"), 2,4);
        a.setEndFrame(6);
        a.setTargetWidth(60);
        a.setTargetHeight(90);
        player.addAnimation(a);

        Body pBody = player.getBody();
        fixtureDef.isSensor = true;

        shape.setAsBox(15 / PPM, 6 / PPM, new Vector2(0, 45 / PPM), 0);
        pBody.createFixture(fixtureDef).setUserData("Foot");

        worldObjects.add(player);

        collisionListener.registerWorldObject(swtch);
        collisionListener.registerWorldObject(entrance);
        collisionListener.registerWorldObject(player);
        collisionListener.registerWorldObject(room);
        collisionListener.registerWorldObject(exit);
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
