package com.pixeldot.ld37.Entities.WorldObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.pixeldot.ld37.Entities.Interfaces.Triggerable;
import com.pixeldot.ld37.Entities.WorldObject;

import static com.pixeldot.ld37.Game.PPM;

public class FloorButton extends WorldObject {

    private Triggerable target;
    private Texture texture;

    public FloorButton(Body body, Triggerable target) {
        super(body);
        this.target = target;
    }

    public void update(float dt) {}

    public void render(SpriteBatch batch) {}

    public void onCollisionBegin(WorldObject worldObject, Contact contact) {}
    public void onCollisionEnd(WorldObject worldObject, Contact contact) {}
}
