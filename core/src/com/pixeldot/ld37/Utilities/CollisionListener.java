package com.pixeldot.ld37.Utilities;

import com.badlogic.gdx.physics.box2d.*;
import com.pixeldot.ld37.States.Play;

public class CollisionListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if(a.getUserData() == null || b.getUserData() == null) return;

        if(a.getUserData().equals("Foot") && b.getUserData().equals("Floor")) {
            Play.bodyOnGround = true;
        }
        else if (a.getUserData().equals("Floor") && b.getUserData().equals("Foot")) {
            Play.bodyOnGround = true;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if(a.getUserData() == null || b.getUserData() == null) return;

        if(a.getUserData().equals("Foot") && b.getUserData().equals("Floor")) {
            Play.bodyOnGround = false;
        }
        else if (a.getUserData().equals("Floor") && b.getUserData().equals("Foot")) {
            Play.bodyOnGround = false;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
