package com.pixeldot.ld37.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.pixeldot.ld37.Game;
import com.pixeldot.ld37.Utilities.ContentManager;
import com.pixeldot.ld37.Utilities.GameStateManager;

/**
 * Created by james on 12/12/16.
 */
public class CreditScreen extends State {

    private Texture screen;
    private Texture back;

    private Rectangle bounds;

    public CreditScreen(GameStateManager gsm) {
        super(gsm);

        screen = ContentManager.getTexture("Credits");
        back = ContentManager.getTexture("Arrow");

        mouse.set(15, 15, 0);
        camera.unproject(mouse);

        bounds = new Rectangle(mouse.x, mouse.y, back.getWidth(), back.getHeight());
    }

    public void update(float dt) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gsm.popState();
        }

        if(Gdx.input.justTouched()) {
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mouse);
            if(mouse.x >= bounds.x && mouse.x <= (bounds.x + bounds.width)) {
                if(mouse.y >= bounds.y && mouse.y <= (bounds.y + bounds.height)) {
                    gsm.popState();
                }
            }
        }
    }

    public void render() {
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(screen, 0, 0, Game.WIDTH, Game.HEIGHT,
                0, 0, screen.getWidth(), screen.getHeight(), false, true);

        mouse.set(15, 15, 0);
        camera.unproject(mouse);
        batch.draw(back, mouse.x, mouse.y, back.getWidth(), back.getHeight(),
                0, 0, back.getWidth(), back.getHeight(), true, true);

        batch.end();
    }

    @Override
    public void dispose() {

    }
}
