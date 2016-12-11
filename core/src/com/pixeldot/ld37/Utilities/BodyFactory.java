package com.pixeldot.ld37.Utilities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.pixeldot.ld37.Game.PPM;

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

        b.createFixture(fixtureDef).setUserData("");

        return b;
    }
}
