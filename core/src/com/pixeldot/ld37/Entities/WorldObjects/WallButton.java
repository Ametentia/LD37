package com.pixeldot.ld37.Entities.WorldObjects;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.pixeldot.ld37.Entities.Interfaces.Triggerable;
import com.pixeldot.ld37.Entities.WorldObject;

public class WallButton extends WorldObject {

    private Triggerable target;

    public WallButton(Body body, Triggerable target) {
        super(body);
        this.target = target;
    }

    public void update(float dt) {}

    public void render(SpriteBatch batch) {}

    public void onCollisionBegin(WorldObject worldObject, Fixture fixture) {}
    public void onCollisionEnd(WorldObject worldObject, Fixture fixture) {}
}
