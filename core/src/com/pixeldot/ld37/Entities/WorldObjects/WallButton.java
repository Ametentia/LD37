package com.pixeldot.ld37.Entities.WorldObjects;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.pixeldot.ld37.Entities.Interfaces.Triggerable;
import com.pixeldot.ld37.Entities.WorldObject;

import static com.pixeldot.ld37.Game.PPM;

public class WallButton extends WorldObject {

    private Triggerable target;
    private Texture texture;

    private boolean state;
    private float accum;
    private float pushTime;

    public WallButton(Body body, Triggerable target, float pushTime) {
        super(body);
        this.target = target;

        state = false;
        accum = 0;
        this.pushTime = pushTime;
    }

    public void update(float dt) {
        if(state) {
            accum += dt;
            if(accum >= pushTime) {
                target.offTrigger();
                state = false;
                accum = 0;
            }
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, body.getPosition().x * PPM - 15, body.getPosition().y * PPM - 15,
                30, 30, 0, 0, texture.getWidth(), texture.getHeight(), false, true);
    }

    public void onCollisionBegin(WorldObject worldObject, Contact contact) {}
    public void onCollisionEnd(WorldObject worldObject, Contact contact) {}

    public void push() {
        state = true;
        target.onTrigger();
    }
}
