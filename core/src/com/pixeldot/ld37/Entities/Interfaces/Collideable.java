package com.pixeldot.ld37.Entities.Interfaces;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.pixeldot.ld37.Entities.WorldObject;

public interface Collideable {
    void onCollisionBegin(WorldObject worldObject, Fixture fixture);
    void onCollisionEnd(WorldObject worldObject, Fixture fixture);
}
