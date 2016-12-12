package com.pixeldot.ld37.Entities.WorldObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.pixeldot.ld37.Entities.Interfaces.Triggerable;
import com.pixeldot.ld37.Entities.Player;
import com.pixeldot.ld37.Entities.WorldObject;

import static com.pixeldot.ld37.Game.PPM;

public class FloorButton extends WorldObject {

    private Triggerable target;
    private Texture texture;
    private int collisionCount;
    private boolean state;

    public FloorButton(Body body, Triggerable target) {
        super(body);
        this.target = target;
        state = false;
    }

    public void update(float dt) {}

    public void render(SpriteBatch batch) {
        batch.draw(texture, body.getPosition().x * PPM - 15, body.getPosition().y * PPM - 15,
                30, 30, 0, 0, texture.getWidth(), texture.getHeight(), false, true);
    }

    public void onCollisionBegin(WorldObject worldObject, Contact contact) {
        if(worldObject instanceof Player || worldObject instanceof Box) {
            if(collisionCount == 0) {
                target.onTrigger();
                state = true;
            }
            collisionCount++;
        }
    }

    public void onCollisionEnd(WorldObject worldObject, Contact contact) {
        if(worldObject instanceof Player || worldObject instanceof Box) {
            collisionCount--;
            if(collisionCount == 0) {
                target.offTrigger();
                state = false;
            }
        }
    }

    public boolean getState() { return state; }
}
