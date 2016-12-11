package com.pixeldot.ld37.Entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.pixeldot.ld37.Entities.Interfaces.Collideable;
import com.pixeldot.ld37.Entities.Interfaces.Renderable;
import com.pixeldot.ld37.Entities.Interfaces.Updateable;

/**
 * Represents a single object within the world
 */
public abstract class WorldObject implements Updateable, Renderable, Collideable {

    /** The Name of the WorldObject */
    protected String name;
    /** The Box2D body of the WorldObject */
    protected Body body;

    /**
     * Creates a WorldObject from the body given and calls it 'Unnamed'
     * @param body The body to create the WorldObject with
     */
    public WorldObject(Body body) { this(body, "Unnamed"); }
    public WorldObject(Body body, String name) {
        this.name = name;
        this.body = body;
        body.setUserData(name);
    }

    /**
     * Gets the name of the WorldObject
     * @return The name
     */
    public final String getName() { return name; }

    /**
     * Gets the body of the WorldObject
     * @return The body
     */
    public final Body getBody() {return body; }

    /**
     * Changes the name of the WorldObject
     * @param name The new name for the WorldObject
     */
    public final void setName(String name) {
        this.name = name;
        body.setUserData(name);
    }
}

