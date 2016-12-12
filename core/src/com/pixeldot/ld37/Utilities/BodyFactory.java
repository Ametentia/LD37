package com.pixeldot.ld37.Utilities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.pixeldot.ld37.Game.HEIGHT;
import static com.pixeldot.ld37.Game.PPM;
import static com.pixeldot.ld37.Game.WIDTH;

public class BodyFactory {

    public static Body getBody(World world, Vector2 position, Vector2 size, BodyDef.BodyType type) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.fixedRotation = true;
        bodyDef.position.set(position.x / PPM, position.y / PPM);
        bodyDef.type = type;

        Body b = world.createBody(bodyDef);

        shape.setAsBox((size.x / 2) / PPM, (size.y / 2) / PPM);

        fixtureDef.shape = shape;
        fixtureDef.friction = 0.1f;

        b.createFixture(fixtureDef).setUserData("");


        return b;
    }
    public static Body getBlockBody(World world, Vector2 position, Vector2 size, BodyDef.BodyType type) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.fixedRotation = true;
        bodyDef.position.set(position.x / PPM, position.y / PPM);
        bodyDef.type = type;

        Body b = world.createBody(bodyDef);

        shape.setAsBox((size.x / 2) / PPM, (size.y / 2) / PPM);


        fixtureDef.shape = shape;
        fixtureDef.friction = 0.1f;
        b.createFixture(fixtureDef).setUserData("");

        fixtureDef = new FixtureDef();
        shape.setAsBox(size.x/2 / PPM, 10 / PPM, new Vector2(0,-size.y/2/PPM),0);
        fixtureDef.isSensor = true;
        fixtureDef.shape = shape;
        b.createFixture(fixtureDef).setUserData("Bottom");

        shape.dispose();


        return b;
    }

    public static Body getRoomBody(World world) {

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((WIDTH / 2) / PPM, (HEIGHT / 2) / PPM);

        Body room = world.createBody(bodyDef);
        room.setUserData("Room");

        fixtureDef.shape = shape;

        // Top of the room
        shape.setAsBox(WIDTH / PPM, 20 / PPM, new Vector2(0, ((-HEIGHT / 2) + 12) / PPM), 0);
        room.createFixture(fixtureDef).setUserData("Top");

        // Left of the room
        shape.setAsBox(252 / PPM, HEIGHT / PPM, new Vector2(((-WIDTH / 2) + 35) / PPM, 0), 0);
        room.createFixture(fixtureDef).setUserData("Left");

        // Right of the room
        shape.setAsBox(252 / PPM, HEIGHT / PPM, new Vector2(((WIDTH / 2) - 32) / PPM, 0), 0);
        room.createFixture(fixtureDef).setUserData("Right");

        // Bottom of the room
        shape.setAsBox(WIDTH / PPM, 20 / PPM, new Vector2(0, ((HEIGHT / 2) - 13) / PPM), 0);
        room.createFixture(fixtureDef).setUserData("Bottom");

        shape.dispose();

        return room;
    }

    public static Body getBoxBody(World world, Vector2 position, Vector2 size) {

        Body boxBody = getBody(world, position, size, BodyDef.BodyType.DynamicBody);
        boxBody.setUserData("Box");

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        fixtureDef.isSensor = true;
        fixtureDef.shape = shape;

        shape.setAsBox(8 / PPM, 8 / PPM, new Vector2((-size.x / 2) / PPM, 0), 0);
        boxBody.createFixture(fixtureDef).setUserData("BoxLeft");

        shape.setAsBox(8 / PPM, 8 / PPM, new Vector2((size.x / 2) / PPM, 0), 0);
        boxBody.createFixture(fixtureDef).setUserData("BoxRight");

        shape.setAsBox((size.x / 2) / PPM, 8 / PPM, new Vector2(0, (-size.y / 2) / PPM), 0);
        boxBody.createFixture(fixtureDef).setUserData("Bottom");

        return boxBody;
    }
}
