package com.pixeldot.ld37.Entities.WorldObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.pixeldot.ld37.Entities.Interfaces.Triggerable;
import com.pixeldot.ld37.Entities.WorldObject;
import com.pixeldot.ld37.Utilities.ContentManager;

import java.util.ArrayList;

/**
 * Represents a static block in the world which can only be moved/ altered via a trigger
 */
public class Block extends WorldObject implements Triggerable {

    // The texture of the block
    private Texture texture;
    private ArrayList<Vector2> states;
    private int currentState;

    /**
     * Creates a new Block with the "Brick" texture
     * @param body The body to create the block from
     */
    public Block(Body body) {
        super(body);

        texture = ContentManager.getTexture("Brick");
        states = new ArrayList<>();
        currentState = 0;
    }

    public void update(float dt) {}

    public void onTrigger() {}
    public void offTrigger() {}

    public void render(SpriteBatch batch) {}

    public void onCollisionBegin(WorldObject worldObject, Contact contact) {}
    public void onCollisionEnd(WorldObject worldObject, Contact contact) {}

    /**
     * Loads the texture from the {@link ContentManager} via the key
     * @param key The key linked to the texture in the content manager
     */
    public void setTexture(String key) { texture = ContentManager.getTexture(key); }

    public Vector2 getState(int i) {
        return states.get(i);
    }

    public void addState(Vector2 state) {
        states.add(state);
    }

    public void setState(int i){
        currentState=i;
    }
    public void addState(){
        currentState++;
    }
    public void backState(){
        currentState--;
    }
}
