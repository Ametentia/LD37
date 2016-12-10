package com.pixeldot.ld37.WorldObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.pixeldot.ld37.Utilities.ContentManager;

import static com.pixeldot.ld37.Game.PPM;

public class Block implements WorldObject{
    private Body body;
    private Texture texture;

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

        texture = ContentManager.getTexture("Brick");
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture,body.getPosition().x,body.getPosition().y);
    }

    @Override
    public void update(float delta) {

    }
}
