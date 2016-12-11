package com.pixeldot.ld37.Utilities;

import com.badlogic.gdx.physics.box2d.*;
import com.pixeldot.ld37.Entities.WorldObject;
import com.pixeldot.ld37.States.Play;

import java.util.HashMap;

public class CollisionListener implements ContactListener {

    /*private boolean onGround;
    private boolean playerPushing;
    private boolean isSwitch;
    private int playerBoxPulling =-1;*/

    private HashMap<String, WorldObject> worldObjects;

    /**
     * Will run the onCollisionBegin method for registered WorldObjects
     * @param contact The collision information
     */
    public void beginContact(Contact contact) {

        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if(a.getUserData() == null || b.getUserData() == null) return;

        if(!worldObjects.containsKey(a.getUserData().toString())) {
            System.err.println("Warning: Body " + a.getUserData() + " has not been registered within the CollisionListener");
            return;
        }
        else if(!worldObjects.containsKey(b.getUserData().toString())) {
            System.err.println("Warning: Body " + b.getUserData() + " has not been registered within the CollisionListener");
            return;
        }

        WorldObject objectA = worldObjects.get(a.getUserData().toString());
        WorldObject objectB = worldObjects.get(b.getUserData().toString());

        objectA.onCollisionBegin(objectB, contact.getFixtureB());
        objectB.onCollisionBegin(objectA, contact.getFixtureA());

        /*Fixture a = contact.getFixtureA();
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


        if(a.getUserData().equals("Switch") && b.getUserData().equals("PlayerMain")){
            isSwitch = true;
        }
        else if(a.getUserData().equals("PlayerMain") && b.getUserData().equals("Switch")){
            isSwitch = true;
        }

        if(a.getUserData().equals("PlayerMain")) {
            if ((b.getUserData()).toString().startsWith("BoxRight")) {
                playerBoxPulling = Integer.parseInt(b.getUserData().toString().replaceAll("BoxRight_", ""));
            } else if ((b.getUserData()).toString().startsWith("BoxLeft")) {
                playerBoxPulling = Integer.parseInt(b.getUserData().toString().replaceAll("BoxLeft_", ""));
            }
        }
        else if(b.getUserData().equals("PlayerMain")) {
            if ((a.getUserData()).toString().startsWith("BoxRight")) {
                playerBoxPulling = Integer.parseInt(a.getUserData().toString().replaceAll("BoxRight_", ""));
            } else if ((a.getUserData()).toString().startsWith("BoxLeft")) {
                playerBoxPulling = Integer.parseInt(a.getUserData().toString().replaceAll("BoxLeft_", ""));
            }
        }*/
    }

    /**
     * Will run the onCollisionEnd method for registered WorldObjects
     * @param contact The collision information
     */
    public void endContact(Contact contact) {

        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if(a.getUserData() == null || b.getUserData() == null) return;

        if(!worldObjects.containsKey(a.getUserData().toString())) {
            System.err.println("Warning: Body " + a.getUserData() + " has not been registered within the CollisionListener");
            return;
        }
        else if(!worldObjects.containsKey(b.getUserData().toString())) {
            System.err.println("Warning: Body " + b.getUserData() + " has not been registered within the CollisionListener");
            return;
        }

        WorldObject objectA = worldObjects.get(a.getUserData().toString());
        WorldObject objectB = worldObjects.get(b.getUserData().toString());

        objectA.onCollisionEnd(objectB, contact.getFixtureB());
        objectB.onCollisionEnd(objectA, contact.getFixtureA());

        /*Fixture a = contact.getFixtureA();
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

        if(a.getUserData().equals("Switch") && b.getUserData().equals("PlayerMain")){
            isSwitch = false;
        }
        else if(a.getUserData().equals("PlayerMain") && b.getUserData().equals("Switch")){
            isSwitch = false;
        }
        if(a.getUserData().equals("PlayerMain")) {
            if ((b.getUserData()).toString().startsWith("BoxRight")) {
                playerBoxPulling = -1;
            } else if ((b.getUserData()).toString().startsWith("BoxLeft")) {
                playerBoxPulling = -1;
            } else if ((a.getUserData()).toString().startsWith("BoxRight")) {
                playerBoxPulling = -1;
            } else if ((a.getUserData()).toString().startsWith("BoxLeft")) {
                playerBoxPulling = -1;
            }
        }*/
    }

    public void preSolve(Contact contact, Manifold oldManifold) {}
    public void postSolve(Contact contact, ContactImpulse impulse) {}

    /**
     * Registers the WorldObject to listen for collisions
     * @param worldObject The WorldObject to register
     */
    public void registerWorldObject(WorldObject worldObject) {
        if(worldObjects.containsKey(worldObject.getName())) {
            System.err.println("Warning: Cannot add another object named: " + worldObject.getName()
                    + "\nConsider Using WorldObject#setName(String) to change it");
            return;
        }

        worldObjects.put(worldObject.getName(), worldObject);
    }

    /**
     * Unregisters a WorldObject from listening for collisions
     * @param name The name of the WorldObject to unregister
     * @return The WorldObject which was unregistered
     */
    public WorldObject unregisterObject(String name) {
        if(!worldObjects.containsKey(name)) {
            throw new IllegalArgumentException("Error: Could not unregister WorldObject with name: "
                    + name + " as it did not exist");
        }

        return worldObjects.remove(name);
    }

    /**
     * Gets a registered WorldObject from the CollisionListener
     * @param name The name of the WorldObject to retrieve
     * @return The WorldObject retrieved
     */
    public WorldObject getWorldObject(String name) {
        if(!worldObjects.containsKey(name)) {
            throw new IllegalArgumentException("Error: Could not retrieve WorldObject with name: "
                    + name + " as it did not exist");
        }

        return worldObjects.get(name);
    }

    /*public boolean isOnGround() { return onGround; }
    public boolean isPlayerPushing() {return playerPushing;}

    public int getPlayerBoxPulling() {
        return playerBoxPulling;
    }

    public void setPlayerBoxPulling(int playerBoxPulling) {
        this.playerBoxPulling = playerBoxPulling;
    }
    public boolean isSwitch() { return isSwitch; } */
}
