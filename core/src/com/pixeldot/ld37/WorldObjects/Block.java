package com.pixeldot.ld37.WorldObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.pixeldot.ld37.Utilities.ContentManager;

import static com.pixeldot.ld37.Game.PPM;

public class Block implements WorldObject, Triggerable {
    private Body body;
    private Texture texture;

    private Vector2 offPosition;
    private Vector2 onPosition;
    private boolean isOn;

    public Block(World w, Vector2 position, String textureKey){
        BodyDef bodydef = new BodyDef();
        bodydef.position.set(position);
        bodydef.type = BodyDef.BodyType.KinematicBody;
        body = w.createBody(bodydef);

        FixtureDef blockFDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50 / PPM, 50 / PPM);
        blockFDef.shape = shape;

        body.createFixture(blockFDef).setUserData("Block");

        onPosition = new Vector2(position.x - (50 / PPM), position.y - (50 / PPM));
        offPosition = new Vector2(onPosition.x, onPosition.y - (100 / PPM));

        texture = ContentManager.getTexture(textureKey);
        isOn = true;
    }

    public void triggerOn() {
        // Do Stuffs
        body.setLinearVelocity(0, 20 / PPM);
        isOn = true;
    }

    public void triggerOff() {
        body.setLinearVelocity(0, -20 / PPM);
        isOn = false;
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
        batch.draw(texture, body.getPosition().x * PPM - 50, body.getPosition().y * PPM - 50, 100, 100);
    }

    @Override
    public void update(float delta) {
        if(!isOn && body.getPosition().y < offPosition.y) {
            body.setTransform(body.getPosition().x, offPosition.y, 0);
            body.setLinearVelocity(0, 0);
        }
        else if(isOn && body.getPosition().y > onPosition.y) {
            body.setTransform(body.getPosition().x, onPosition.y, 0);
            body.setLinearVelocity(0, 0);
        }
    }
}
