package com.pixeldot.ld37.WorldObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.pixeldot.ld37.Game.PPM;

public class Switch {
    private Body body;
    private final Vector2 Size = new Vector2(20,20);
    private boolean on = false;

    public Switch(World w, Vector2 position){
        BodyDef bodydef = new BodyDef();
        bodydef.position.set(position);
        bodydef.type = BodyDef.BodyType.KinematicBody;
        body = w.createBody(bodydef);

        FixtureDef sensorFixDef = new FixtureDef();
        sensorFixDef.isSensor=true;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Size.x / PPM, Size.y / PPM);
        sensorFixDef.shape = shape;

        body.createFixture(sensorFixDef);
    }
    public void render(SpriteBatch sb){

    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Vector2 getSize() {
        return Size;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
    public void flick(){
        on = !on;
    }
}
