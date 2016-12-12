package com.pixeldot.ld37.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.pixeldot.ld37.Entities.Player;
import com.pixeldot.ld37.Entities.WorldObject;
import com.pixeldot.ld37.Entities.WorldObjects.*;
import com.pixeldot.ld37.Utilities.*;
import sun.font.TrueTypeFont;

import java.util.ArrayList;

import static com.pixeldot.ld37.Game.HEIGHT;
import static com.pixeldot.ld37.Game.PPM;
import static com.pixeldot.ld37.Game.WIDTH;

public class Level extends State {

    private ArrayList<WorldObject> worldObjects;
    private Player player;
    private CollisionListener collisionListener;
    private Door exit;
    private Door entrance;
    private int level;
    private BitmapFont font = new BitmapFont(true);

    public Level(GameStateManager gsm, int level) {
        super(gsm);
        this.level = level;
        world = new World(new Vector2(0, 9.81f), true);
        worldObjects = new ArrayList<>();
        world.setContactListener(collisionListener = new CollisionListener());

        ContentManager.loadTexture("PlayerRun", "Character/spritesheetSmol.png");
        ContentManager.loadTexture("PlayerWall", "Character/pushSpriteSheet.png");
        ContentManager.loadTexture("Shadow", "Character/shadow.png");
        ContentManager.loadTexture("Rice", "Materials/SpringSummerAutumnBackground.png");
        ContentManager.loadTexture("Background", "Materials/Background.png");
        ContentManager.loadTexture("LeftAutumn", "Side Panels/autumnLeftSlide.png");
        ContentManager.loadTexture("RightAutumn", "Side Panels/autumnRightSlide.png");

        ContentManager.loadTexture("Diamond", "Materials/diamondTexture.png");
        ContentManager.loadTexture("Brick", "Materials/brickTexture.png");
        ContentManager.loadTexture("Star", "Materials/starDesign.png");

        ContentManager.loadTexture("DoorClosed", "Materials/door.png");
        ContentManager.loadTexture("DoorOpen", "Character/doorSpritesheet.png");
        ContentManager.loadTexture("DoorClose", "Character/doorClosingSpritesheet.png");

        ContentManager.loadTexture("SwitchOff", "World Objects/leverOFF.png");
        ContentManager.loadTexture("SwitchOn", "World Objects/leverON.png");

        ContentManager.loadTexture("FloorButton", "Materials/button.png");

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

        System.out.println(level);
        switch (level) {
            case 1:
                setupLevel1();
                break;
            case 2:
                setupLevel2();
                break;
            case 3:
                setupLevel3();
                break;
            case 4:
                setupLevel4();
                break;
        }
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = shape;

        Animation idle = new Animation("Idle", ContentManager.getTexture("PlayerIdle"), 6, 4);
        idle.setTargetHeight(90);
        idle.setTargetWidth(60);
        player = new Player(BodyFactory.getBody(world, new Vector2(355, HEIGHT - 86), new Vector2(60, 90), BodyDef.BodyType.DynamicBody), world, idle);
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

        collisionListener.registerWorldObject(entrance);
        collisionListener.registerWorldObject(player);
        collisionListener.registerWorldObject(room);

        collisionListener.registerWorldObject(exit);
        player.setAlive(false);
        entrance.onTrigger();
        this.entrance = entrance;
        font = new BitmapFont();
    }

    public void update(float dt) {
        if(Gdx.input.isTouched()) {
            player.getBody().setTransform(Gdx.input.getX() / PPM, Gdx.input.getY() / PPM, 0);
        }

        for(WorldObject worldObject : worldObjects) {
            worldObject.update(dt);
        }
        endSequence();
        if(!entrance.isPlayerAdded() && entrance.getAnimation().isFinished()) {
            player.setAlive(true);
            entrance.setPlayerAdded(true);
            entrance.setClosed();
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
    public void endSequence(){
        if(!player.isAlive() && player.isCanExit()) {
            Room r = null;
            boolean doorFinished = false;
            for (WorldObject wo : worldObjects) {
                if (wo instanceof Room) {
                    r = (Room) wo;
                }
                else if(wo instanceof Door){
                        doorFinished=((Door) wo).getAnimation().isFinished();
                }
            }
            if (null != r && doorFinished && !r.isRunDone()) {
                r.setEndRun(true);
            }
            else if(null != r && doorFinished && r.isRunDone())
            {
                if(level == GameStateManager.LEVELS.length) {
                    gsm.popState();
                }
                else {
                    gsm.setState(GameStateManager.LEVELS[level]);
                }
            }
        }
    }
    public void dispose() {}

    public void setupLevel1(){
        Block block = new Block(BodyFactory.getBlockBody(world, new Vector2(WIDTH / 2, HEIGHT / 2), new Vector2(100, HEIGHT), BodyDef.BodyType.KinematicBody));
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
        collisionListener.registerWorldObject(block);
        collisionListener.registerWorldObject(swtch);
    }

    public void setupLevel2(){
        Block block = new Block(BodyFactory.getBlockBody(world, new Vector2(WIDTH/ 2 + WIDTH/8, HEIGHT -140), new Vector2(100, 225), BodyDef.BodyType.KinematicBody));
        block.setName("Pillar");
        block.setWidth(100);
        block.setHeight(225);

        worldObjects.add(block);

        Box box = new Box(BodyFactory.getBoxBody(world,new Vector2(WIDTH/2-WIDTH/8,HEIGHT-131),new Vector2(90,90)));
        box.setTexture("Diamond");
        box.setName("Box");
        box.setHeight(90);
        box.setWidth(90);

        worldObjects.add(box);

        collisionListener.registerWorldObject(box);
        collisionListener.registerWorldObject(block);
    }

    public void setupLevel3(){
        Block block = new Block(BodyFactory.getBlockBody(world, new Vector2(WIDTH/ 2 + WIDTH/8-10, HEIGHT -130), new Vector2(100, 200), BodyDef.BodyType.KinematicBody));
        block.setTexture("Brick");
        block.setName("Pillar");
        block.setWidth(100);
        block.setHeight(200);

        worldObjects.add(block);

        Block block2 = new Block(BodyFactory.getBlockBody(world, new Vector2(WIDTH/ 2, HEIGHT-280), new Vector2(400, 110), BodyDef.BodyType.KinematicBody));
        block2.setName("Stage");
        block2.setTexture("Brick");
        block2.setWidth(400);
        block2.setHeight(110);

        worldObjects.add(block2);

        Block block3 = new Block(BodyFactory.getBlockBody(world, new Vector2(WIDTH/ 2-330, HEIGHT-200), new Vector2(50, 50), BodyDef.BodyType.KinematicBody));
        block3.setName("help box");
        block3.setTexture("Brick");
        block3.setWidth(50);
        block3.setHeight(50);

        worldObjects.add(block3);

        Box box = new Box(BodyFactory.getBoxBody(world,new Vector2(WIDTH/2+WIDTH/12,HEIGHT-131),new Vector2(90,90)));
        box.setTexture("Star");
        box.setName("Box");
        box.setHeight(90);
        box.setWidth(90);

        worldObjects.add(box);

        collisionListener.registerWorldObject(block);
        collisionListener.registerWorldObject(block2);
        collisionListener.registerWorldObject(block3);
        collisionListener.registerWorldObject(box);
    }
    public void setupLevel4(){
        Block block = new Block(BodyFactory.getBlockBody(world, new Vector2(WIDTH / 2, HEIGHT - (HEIGHT / 4)), new Vector2(60, HEIGHT / 4), BodyDef.BodyType.KinematicBody));
        block.setHeight(HEIGHT / 4);
        block.setWidth(60);
        block.setName("Block");

        block.addState(new Vector2(WIDTH / 2, HEIGHT - 30));
        block.addState(new Vector2(WIDTH / 2, HEIGHT - (HEIGHT / 4)));

        FloorButton button = new FloorButton(BodyFactory.getBody(world, new Vector2(540, HEIGHT - 40), new Vector2(100, 15), BodyDef.BodyType.StaticBody), block);
        button.getBody().getFixtureList().get(0).setSensor(true);
        button.setName("Button");

        worldObjects.add(button);
        worldObjects.add(block);

        collisionListener.registerWorldObject(button);
        collisionListener.registerWorldObject(block);
    }

}
