package com.pixeldot.ld37.Entities.WorldObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.pixeldot.ld37.Entities.Interfaces.Triggerable;
import com.pixeldot.ld37.Entities.WorldObject;
import com.pixeldot.ld37.Utilities.Animation;
import com.pixeldot.ld37.Utilities.ContentManager;

public class Door extends WorldObject implements Triggerable {

    private Animation animation;
    private boolean isOpen;
    private boolean isExit;
    private boolean playerAdded = false;

    public Door(Body body, boolean isExit) {
        super(body);
        animation = new Animation("Closed", ContentManager.getTexture("DoorClosed"), 1, 1);
        animation.setTargetWidth(125);
        animation.setTargetHeight(106);

        this.isExit = isExit;
        isOpen = false;
    }

    public void update(float dt) {
        animation.update(dt);
    }

    public void setClosed(){
        animation = new Animation("Closed", ContentManager.getTexture("DoorClosed"), 1, 1);
        animation.setTargetWidth(125);
        animation.setTargetHeight(106);
    }

    public void onTrigger() {
        animation = new Animation("Open", ContentManager.getTexture("DoorOpen"), 4, 4);
        animation.setTargetWidth(125);
        animation.setTargetHeight(106);
        animation.setMaxPlays(1);
        isOpen = true;
    }

    public void offTrigger() {
        animation = new Animation("Close", ContentManager.getTexture("DoorClose"), 4, 4);
        animation.setTargetWidth(125);
        animation.setTargetHeight(106);
        animation.setMaxPlays(1);
        isOpen = false;
    }

    public void render(SpriteBatch batch) {
        animation.render(batch, body.getPosition());
    }

    public void onCollisionBegin(WorldObject worldObject, Contact contact) {}
    public void onCollisionEnd(WorldObject worldObject, Contact contact) {}

    public boolean isOpen() { return isOpen; }
    public boolean isExit() { return isExit; }

    public Animation getAnimation() {
        return animation;
    }

    public boolean isPlayerAdded() {
        return playerAdded;
    }

    public void setPlayerAdded(boolean playerAdded) {
        this.playerAdded = playerAdded;
    }
}
