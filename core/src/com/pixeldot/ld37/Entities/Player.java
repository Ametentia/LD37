package com.pixeldot.ld37.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.pixeldot.ld37.Entities.WorldObjects.Door;
import com.pixeldot.ld37.Entities.WorldObjects.Switch;
import com.pixeldot.ld37.Utilities.Animation;
import com.pixeldot.ld37.Utilities.ContentManager;

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

    private boolean walking;
    private int standingon=0;

    private Switch currentSwitch;
    private int canSwitch = 0;
    private boolean canExit = false;
    private Door exitDoor;

    public Player(Body body, World world, Animation...anim) {
        super(body);
        this.body = body;
        this.world = world;
        walking=false;

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

        body.setLinearDamping(2);
    }

    public void update(float dt) {
        Animation a = animations.get(currentAnimation);
        a.update(dt);
        if(a.isFinished()) {
            currentAnimation = !prevAnimation.equals("") ? prevAnimation : currentAnimation;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A) && alive) {
            body.applyForceToCenter(-700  / PPM, 0, true);

            currentAnimation = "Run";
            animations.get(currentAnimation).setStartFrame(1);
            if(!walking && onGround){
                walking=true;
                System.out.println("Started walk sound");
                ContentManager.getRandomSound("Walk",1,3).play();
            }

            animations.get(currentAnimation).setFlipX(true);
            animations.get("Idle").setFlipX(true);

        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D) && alive) {
            body.applyForceToCenter(700  / PPM, 0, true);

            if(!walking && onGround){
                walking=true;
                ContentManager.getRandomSound("Walk",1,3).play();
            }
            currentAnimation = "Run";
            animations.get(currentAnimation).setStartFrame(1);

            animations.get(currentAnimation).setFlipX(false);
            animations.get("Idle").setFlipX(false);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && onGround && !Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && alive) {
            body.applyForceToCenter(0, -5700 * 7.5f / PPM, true);
            ContentManager.getRandomSound("Jump",1,3).play();
            if(walking){
                ContentManager.stopRandomNoise("Walk",1,3);
                walking=false;
                System.out.println("Stop");
            }
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


        if(canSwitch > 0 && currentSwitch != null && Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            currentSwitch.flick();
        }
        else if(exitDoor != null &&Gdx.input.isKeyJustPressed(Input.Keys.E) && canExit && alive){
            alive=false;
            exitDoor.offTrigger();
        }

        if(!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D))
        {
            currentAnimation = "Idle";
            if(walking){
                walking=false;
                ContentManager.stopRandomNoise("Walk",1,3);
            }
        }
    }

    public void render(SpriteBatch batch) {
        if(alive){
            animations.get(currentAnimation).render(batch, body.getPosition());
        }
    }

    public void onCollisionBegin(WorldObject worldObject, Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        // Check for ground collision beginning
        if(a.getUserData() != null && a.getUserData().equals("Bottom")) {
            if(b.getUserData() != null && b.getUserData().equals("Foot")) {
                standingon++;
                onGround = standingon>0;
            }
        }


        if(( a.getUserData() != null)&&  b.getUserData().equals("BoxLeft")|| b.getUserData().equals("BoxRight")){
            jointDef = new DistanceJointDef();
            createJoint = true;
            jointDef.length = 83 / PPM;
            jointDef.collideConnected = true;
            jointDef.bodyB = worldObject.getBody();
            jointDef.bodyA = body;
            pushing = true;
        }
        else if(b.getUserData() != null && (a.getUserData().equals("BoxRight") || a.getUserData().equals("BoxLeft")))
        {
            jointDef = new DistanceJointDef();
            createJoint = true;
            jointDef.length = 83 / PPM;
            jointDef.collideConnected = true;
            jointDef.bodyB = worldObject.getBody();
            jointDef.bodyA = body;
            pushing = true;
        }
        if(worldObject instanceof Switch) {
            currentSwitch = (Switch) worldObject;
            canSwitch++;
        }

        if(worldObject instanceof Door && ((Door) worldObject).isExit()){
            canExit=true;
            exitDoor=(Door)worldObject;
        }

    }
    public void onCollisionEnd(WorldObject worldObject, Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        // Check for ground collision ending
        if(a.getUserData() != null && a.getUserData().equals("Bottom")) {
            if(b.getUserData() != null && b.getUserData().equals("Foot")) {
                standingon--;
                onGround = standingon>0;
            }
        }
        else if(a.getUserData() != null && a.getUserData().equals("Foot")) {
            if(b.getUserData() != null && b.getUserData().equals("Bottom")) {
                standingon--;
                onGround = standingon>0;
            }
        }

        if(a.getUserData() != null && (a.getUserData().equals("BoxLeft") || a.getUserData().equals("BoxRight"))) {
            jointDef = null;
            createJoint = false;
        } else if (b.getUserData() != null && (b.getUserData().equals("BoxLeft") || b.getUserData().equals("BoxRight"))) {
            jointDef = null;
            createJoint = false;
        }

        if(currentSwitch != null && currentSwitch.equals(worldObject)) {
            canSwitch--;
            if(canSwitch <= 0) {
                currentSwitch = null;
                canSwitch = 0;
            }
        }
        if(worldObject instanceof Door && ((Door) worldObject).isExit()){
            canExit=false;
            exitDoor=null;
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

    public boolean isCanExit() {
        return canExit;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
