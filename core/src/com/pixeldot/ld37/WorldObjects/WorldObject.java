package com.pixeldot.ld37.WorldObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface WorldObject {
    public void render(SpriteBatch batch);
    public void update(float delta);
}
