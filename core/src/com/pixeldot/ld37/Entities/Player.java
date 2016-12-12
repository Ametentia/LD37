package com.pixeldot.ld37.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.pixeldot.ld37.Entities.WorldObjects.Switch;
import com.pixeldot.ld37.Utilities.Animation;

import java.util.HashMap;

import static com.pixeldot.ld37.Game.PPM;

public class Player extends WorldObject {

    private World world;

    private HashMap<String, Animation> animations;
    private String currentAnimation;
    private String prevAnimation;
    private boolean alive;

    private boolean onGround;
    private boolean pushing;

    private boolean isPulling;
    private Joint pullingJoint;
    private DistanceJointDef jointDef;
    private boolean createJoint;

    private Switch currentSwitch;
    private boolean canSwitch = false;

    public Player(Body body, World world, Animation...anim) {
        super(body);
        this.body = body;
        this.world = world;

        jointDef = new DistanceJointDef();
        //isPulling = false;

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
            float fX = isPulling && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) ? -1400 : -400;
            body.applyForceToCenter(-400  / PPM, 0, true);
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
            float fX = isPulling && !Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) ? 1400 : 400;
            body.applyForceToCenter(fX  / PPM, 0, true);
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

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && onGround && !Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            body.applyForceToCenter(0, -5700 / PPM, true);
        }

        // Pulling Stuffs
        if(createJoint && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && pullingJoint == null) {
            pullingJoint = world.createJoint(jointDef);
            isPulling = true;
            createJoint = false;
        }

        if(isPulling && !Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            world.destroyJoint(pullingJoint);
            isPulling = false;
            createJoint = false;
            //jointDef = null;
            pullingJoint = null;
        }

        if(canSwitch && currentSwitch != null && Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            currentSwitch.flick();
        }
    }

    public void render(SpriteBatch batch) {
        animations.get(currentAnimation).render(batch, body.getPosition());
    }

    public void onCollisionBegin(WorldObject worldObject, Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        // Check for ground collision beginning
        if(a.getUserData() != null && a.getUserData().equals("Bottom")) {
            if(b.getUserData() != null && b.getUserData().equals("Foot")) {
                onGround = true;
            }
        }


        if(a.getUserData() != null && (a.getUserData().equals("BoxRight") || a.getUserData().equals("BoxLeft"))) {
            System.out.println("Should Pull!");
            jointDef = new DistanceJointDef();
            createJoint = true;
            jointDef.length = 83 / PPM;
            jointDef.collideConnected = true;
            jointDef.bodyB = worldObject.getBody();
            jointDef.bodyA = body;
        }
        else if(b.getUserData() != null && (b.getUserData().equals("BoxRight") || b.getUserData().equals("BoxLeft"))) {
            System.out.println("Should Pull!");
            jointDef = new DistanceJointDef();
            createJoint = true;
            jointDef.length = 83 / PPM;
            jointDef.collideConnected = true;
            jointDef.bodyB = worldObject.getBody();
            jointDef.bodyA = body;
        }

        if(worldObject instanceof Switch) {
            currentSwitch = (Switch) worldObject;
            canSwitch = true;
        }
    }
    public void onCollisionEnd(WorldObject worldObject, Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        // Check for ground collision ending
        if(a.getUserData() != null && a.getUserData().equals("Bottom")) {
            if(b.getUserData() != null && b.getUserData().equals("Foot")) {
                onGround = false;
            }
        }
        else if(a.getUserData() != null && a.getUserData().equals("Foot")) {
            if(b.getUserData() != null && b.getUserData().equals("Bottom")) {
                onGround = false;
            }
        }

        if(currentSwitch != null && currentSwitch.equals(worldObject)) {
            canSwitch = false;
            currentSwitch = null;
        }
    }

    public void dispose() {}

    public void addAnimation(Animation animation){
        animations.put(animation.getName(), animation);
    }
    public boolean isAlive() { return alive; }
    public boolean isOnGround() { return onGround; }

    public Animation getCurAnim() { return animations.get(currentAnimation); }
    public void setOnGround(boolean onGround) { this.onGround = onGround; }
}
