package com.pixeldot.ld37.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.pixeldot.ld37.Utilities.Animation;

import java.util.HashMap;

import static com.pixeldot.ld37.Game.PPM;

public class Player {
    protected HashMap<String, Animation> animations;
    private String currentAnimation;
    private String prevAnimation;
    protected boolean alive;

    private boolean onGound;
    private Body body;

    public Player(Body body, Animation...anim) {
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
        a.setPosition(body.getPosition());

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            body.applyForceToCenter(-400 / PPM, 0, true);
            animations.get(currentAnimation).setFlipX(true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.applyForceToCenter(400 / PPM, 0, true);
            animations.get(currentAnimation).setFlipX(false);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && onGound) {
            body.applyForceToCenter(0, -12000 / PPM, true);
        }
    }

    public void render(SpriteBatch batch) {
        animations.get(currentAnimation).render(batch);
    }

    public void dispose() {}

    public Body getBody() { return body; }
    public boolean isAlive() { return alive; }
    public boolean isOnGound() { return onGound; }

    public Animation getCurAnim() { return animations.get(currentAnimation); }
    public void setOnGound(boolean onGound) { this.onGound = onGound; }
}
