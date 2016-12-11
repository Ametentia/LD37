package com.pixeldot.ld37.Entities.WorldObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.pixeldot.ld37.Entities.WorldObject;
import com.pixeldot.ld37.Utilities.ContentManager;

import static com.pixeldot.ld37.Game.PPM;

public class Box extends WorldObject {

    private Texture texture;
    private float width, height;

    public Box(Body body) {
        super(body);

        texture = ContentManager.getTexture("Diamond");
        width = texture.getWidth();
        height = texture.getHeight();
    }

    public void update(float dt) {}

    public void render(SpriteBatch batch) {
        batch.draw(texture, body.getPosition().x * PPM - (width / 2), body.getPosition().y * PPM - (height / 2),
                width, height, 0, 0, texture.getWidth(), texture.getHeight(), false, true);
    }

    public void onCollisionBegin(WorldObject worldObject, Fixture fixture) {}
    public void onCollisionEnd(WorldObject worldObject, Fixture fixture) {}

    public void setTexture(String key) { texture = ContentManager.getTexture(key); }
    public void setWidth(float width) { this.width = width; }
    public void setHeight(float height) { this.height = height; }
}
