package com.pixeldot.ld37.Entities.WorldObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.pixeldot.ld37.Entities.Interfaces.Triggerable;
import com.pixeldot.ld37.Entities.WorldObject;
import com.pixeldot.ld37.Utilities.Animation;

public class Door extends WorldObject implements Triggerable {

    private Animation animation;

    public Door(Body body) {
        super(body);
    }

    public void update(float dt) {}

    public void onTrigger() {}
    public void offTrigger() {}

    public void render(SpriteBatch batch) {}

    public void onCollisionBegin(WorldObject worldObject, Fixture fixture) {}
    public void onCollisionEnd(WorldObject worldObject, Fixture fixture) {}
}
