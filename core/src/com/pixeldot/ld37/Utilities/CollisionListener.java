package com.pixeldot.ld37.Utilities;

import com.badlogic.gdx.physics.box2d.*;
import com.pixeldot.ld37.States.Play;

public class CollisionListener implements ContactListener {

    private boolean onGround;
    private boolean playerPushing;

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if(a.getUserData() == null || b.getUserData() == null) return;

        if(a.getUserData().equals("Foot") && b.getUserData().equals("Floor")) {
            onGround = true;
        }
        else if (a.getUserData().equals("Floor") && b.getUserData().equals("Foot")) {
            onGround = true;
        }

        if(a.getUserData().equals("PlayerMain") && b.getUserData().equals("Wall")){
            playerPushing=true;
        }
        else if(a.getUserData().equals("Wall") && b.getUserData().equals("PlayerMain")){
            playerPushing=true;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if(a.getUserData() == null || b.getUserData() == null) return;

        if(a.getUserData().equals("Foot") && b.getUserData().equals("Floor")) {
            onGround = false;
        }
        else if (a.getUserData().equals("Floor") && b.getUserData().equals("Foot")) {
            onGround = false;
        }
        if(a.getUserData().equals("PlayerMain") && b.getUserData().equals("Wall")){
            playerPushing=false;
        }
        else if(a.getUserData().equals("Wall") && b.getUserData().equals("PlayerMain")){
            playerPushing=false;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isOnGround() { return onGround; }
    public boolean isPlayerPushing() {return playerPushing;}
}
