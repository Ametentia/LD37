package com.pixeldot.ld37.Entities.Interfaces;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.pixeldot.ld37.Entities.WorldObject;

public interface Collideable {
    void onCollisionBegin(WorldObject worldObject, Contact contact);
    void onCollisionEnd(WorldObject worldObject, Contact contact);
}
