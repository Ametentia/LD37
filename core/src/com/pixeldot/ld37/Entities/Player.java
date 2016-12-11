package com.pixeldot.ld37.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.pixeldot.ld37.Utilities.Animation;
import com.pixeldot.ld37.Utilities.CollisionListener;

import java.util.HashMap;

import static com.pixeldot.ld37.Game.PPM;

public class Player extends WorldObject {

    private HashMap<String, Animation> animations;
    private String currentAnimation;
    private String prevAnimation;
    private boolean alive;

    private boolean onGound;
    private Body body;

    public Player(Body body, Animation...anim) {
        super(body);
        this.body = body;
        animations = new HashMap<>();

        currentAnimation = "";
        prevAnimation = "";

        alive = true;
        for(Animation a : anim) {
            animations.put(a.getName(), a);
            currentAnimation = currentAnimation.equals("") ? a.getName() : currentAnimation;
            prevAnimation = prevAnimation.equals("") ? a.getName() : prevAnimation;
        }
    }

    public void update(float dt) {
        Animation a = animations.get(currentAnimation);
        a.update(dt);
        if(a.isFinished()) {
            currentAnimation = !prevAnimation.equals("") ? prevAnimation : currentAnimation;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            body.applyForceToCenter(-400 / PPM, 0, true);
            /*if(collisions.isPlayerPushing())
                currentAnimation="SquishFace";
            else {
                currentAnimation = "Run";
                animations.get(currentAnimation).setStartFrame(1);
            }*/
            animations.get(currentAnimation).setFlipX(true);
            animations.get("Idle").setFlipX(true);
            animations.get(currentAnimation).setStartFrame(1);

        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.applyForceToCenter(400 / PPM, 0, true);
            /*if(collisions.isPlayerPushing())
                currentAnimation="SquishFace";
            else {
                currentAnimation = "Run";
                animations.get(currentAnimation).setStartFrame(1);
            }*/
            animations.get(currentAnimation).setFlipX(false);
            animations.get("Idle").setFlipX(false);
        }
        else
        {
            currentAnimation = "Idle";
        }


        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && onGound && !Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            body.applyForceToCenter(0, -5700 / PPM, true);
        }
    }

    public void render(SpriteBatch batch) {
        animations.get(currentAnimation).render(batch, body.getPosition());
    }

    public void onCollisionBegin(WorldObject worldObject, Contact contact) {}
    public void onCollisionEnd(WorldObject worldObject, Contact contact) {}

    public void dispose() {}

    public void addAnimation(Animation animation){
        animations.put(animation.getName(), animation);
    }
    public boolean isAlive() { return alive; }
    public boolean isOnGound() { return onGound; }

    public Animation getCurAnim() { return animations.get(currentAnimation); }
    public void setOnGound(boolean onGound) { this.onGound = onGound; }
}
