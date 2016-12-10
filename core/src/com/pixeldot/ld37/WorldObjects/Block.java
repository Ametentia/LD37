package com.pixeldot.ld37.WorldObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.pixeldot.ld37.Utilities.ContentManager;

import static com.pixeldot.ld37.Game.HEIGHT;
import static com.pixeldot.ld37.Game.PPM;

public class Block {
    Body body;
    Texture texture;
    ContentManager content;

    public Block(World w, Vector2 position, String textureKey){
        BodyDef bodydef = new BodyDef();
        bodydef.position.set(position);
        bodydef.type = BodyDef.BodyType.KinematicBody;
        body = w.createBody(bodydef);

        FixtureDef blockFDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50 / PPM, 50 / PPM);
        blockFDef.shape = shape;

        body.createFixture(blockFDef);

        texture = new Texture("Textures/Materials/brickTexture.png");
    }

    public void render(SpriteBatch batch){
        batch.begin();

        batch.end();
    }
}
