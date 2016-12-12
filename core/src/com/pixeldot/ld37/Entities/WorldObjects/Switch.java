package com.pixeldot.ld37.Entities.WorldObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.pixeldot.ld37.Entities.Interfaces.Triggerable;
import com.pixeldot.ld37.Entities.WorldObject;
import com.pixeldot.ld37.Utilities.ContentManager;

import static com.pixeldot.ld37.Game.PPM;

public class Switch extends WorldObject {

    private Triggerable target;
    private Texture texture;
    private boolean state;

    public Switch(Body body, Triggerable target) {
        super(body);
        this.target = target;

        texture = ContentManager.getTexture("SwitchOff");
        state = false;
    }

    public void update(float dt) {}

    public void render(SpriteBatch batch) {
        batch.draw(texture, body.getPosition().x * PPM - 30, body.getPosition().y * PPM - 45,
                60, 90, 0, 0, texture.getWidth(), texture.getHeight(), false, true);
    }

    public void onCollisionBegin(WorldObject worldObject, Contact contact) {}
    public void onCollisionEnd(WorldObject worldObject, Contact contact) {}

    public boolean getState() { return state; }
    public void flick() {
        state = !state;
        if(state) {
            target.onTrigger();
            texture = ContentManager.getTexture("SwitchOn");
        }
        else {
            target.offTrigger();
            texture = ContentManager.getTexture("SwitchOff");
        }
    }
}
