package com.pixeldot.ld37.Entities.WorldObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.pixeldot.ld37.Entities.Interfaces.Triggerable;
import com.pixeldot.ld37.Entities.WorldObject;
import com.pixeldot.ld37.Utilities.ContentManager;

import java.util.ArrayList;

import static com.pixeldot.ld37.Game.HEIGHT;
import static com.pixeldot.ld37.Game.PPM;

/**
 * Represents a static block in the world which can only be moved/ altered via a trigger
 */
public class Block extends WorldObject implements Triggerable {

    // The texture of the block
    private Texture texture;
    private ArrayList<Vector2> states;
    private int currentState;
    private float moveSpeed;
    private float tolerance;

    private float width, height;

    /**
     * Creates a new Block with the "Brick" texture
     * @param body The body to create the block from
     */
    public Block(Body body) {
        super(body);

        texture = ContentManager.getTexture("Brick");
        states = new ArrayList<>();
        currentState = 0;
        moveSpeed=100;
        tolerance = 0.5f;

        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        width = texture.getWidth();
        height = texture.getHeight();
    }

    public void update(float dt) {
        if(states.size() == 0) return;

        Vector2 dist = new Vector2(body.getPosition().x * PPM, body.getPosition().y * PPM).sub(states.get(currentState));
        if(MathUtils.isZero(dist.len(), tolerance)) {
            body.setLinearVelocity(0, 0);
        }
    }

    public void onTrigger() {
        if(states.size() == 0) return;

        currentState++;
        System.out.println("state is now "+currentState);


        if(states.get(currentState).y < body.getPosition().y * PPM) {
            body.setLinearVelocity(0, -moveSpeed / PPM);
        }
        else if(states.get(currentState).y > body.getPosition().y * PPM) {
            body.setLinearVelocity(0, moveSpeed / PPM);
        }
    }
    public void offTrigger() {
        if(states.size() == 0) return;

        currentState--;
        System.out.println("state is now "+currentState);


        if(states.get(currentState).y < body.getPosition().y * PPM) {
            body.setLinearVelocity(0, -moveSpeed / PPM);
        }
        else if(states.get(currentState).y > body.getPosition().y * PPM){
            body.setLinearVelocity(0, moveSpeed / PPM);
        }
    }

    public void render(SpriteBatch batch) {
        int u = width < height ? 100 : 1162;
        int v = height < width ? 100 : 1077;
        batch.draw(texture, body.getPosition().x * PPM - (width / 2), body.getPosition().y * PPM - (height / 2),
                width, height, 0, 0, u, v, false, true);
    }

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

    public void setWidth(float width) { this.width = width; }
    public void setHeight(float height) { this.height = height; }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
        tolerance = (moveSpeed / 200);
    }
}
