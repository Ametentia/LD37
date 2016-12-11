package com.pixeldot.ld37.WorldObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.pixeldot.ld37.Utilities.Animation;
import com.pixeldot.ld37.Utilities.CollisionListener;
import com.pixeldot.ld37.Utilities.ContentManager;

import static com.pixeldot.ld37.Game.PPM;

public class Box implements WorldObject{
    private Body body;
    private Animation animation;
    private CollisionListener collisions;
    private World world;
    private boolean beingPulled;

    public static int globalBoxId = 1;
    private int id;

    public Box(World w, String textureKey, Vector2 position, CollisionListener col){
        collisions=col;
        world=w;
        BodyDef bodydef = new BodyDef();
        bodydef.position.set(position);
        bodydef.type = BodyDef.BodyType.DynamicBody;
        body = w.createBody(bodydef);

        FixtureDef blockFDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(45 / PPM, 45 / PPM);
        blockFDef.shape = shape;

        body.createFixture(blockFDef);

        shape.setAsBox(45 / PPM, 10 / PPM,new Vector2(0,-40/PPM),0);
        blockFDef.shape = shape;
        blockFDef.isSensor=true;

        body.createFixture(blockFDef).setUserData("Floor");

        shape.setAsBox(15 / PPM, 20 / PPM,new Vector2(45/PPM,0),0);
        blockFDef.shape = shape;
        blockFDef.isSensor=true;

        body.createFixture(blockFDef).setUserData("BoxRight_"+globalBoxId);

        shape.setAsBox(-15 / PPM, 20 / PPM,new Vector2(-45/PPM,0),0);
        blockFDef.shape = shape;
        blockFDef.isSensor=true;

        body.createFixture(blockFDef).setUserData("BoxLeft_"+globalBoxId);

        id= globalBoxId;
        globalBoxId++;

        animation = new Animation(textureKey, ContentManager.getTexture(textureKey), 1, 1);
        animation.setTargetWidth(90);
        animation.setTargetHeight(90);
    }
    @Override
    public void render(SpriteBatch batch) {
        animation.render(batch,body.getPosition());
    }

    @Override
    public void update(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
            if(collisions.getPlayerBoxPulling()==id){
                beingPulled=true;
            }
        }
        else
        {
            beingPulled=false;
        }
    }
    public boolean isBeingPulled(){return beingPulled;}
    public Body getBody() { return body; }
}
