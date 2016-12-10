package com.pixeldot.ld37.WorldObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.pixeldot.ld37.Utilities.ContentManager;

public class Block {
    Body body;
    Texture texture;
    ContentManager content;

    public Block(World w, Vector2 position, String textureKey){
        BodyDef bodydef = new BodyDef();
        bodydef.position.set(position);
        bodydef.type = BodyDef.BodyType.KinematicBody;
        body = w.createBody(bodydef);

        texture = content.getTexture(textureKey);
    }

    public void render(SpriteBatch batch){
        batch.begin();
        batch.draw(texture, body.getPosition().x, body.getPosition().y);
        batch.end();
    }
}
