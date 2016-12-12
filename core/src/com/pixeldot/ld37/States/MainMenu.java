package com.pixeldot.ld37.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pixeldot.ld37.Game;
import com.pixeldot.ld37.Utilities.Animation;
import com.pixeldot.ld37.Utilities.ContentManager;
import com.pixeldot.ld37.Utilities.GameStateManager;

import static com.pixeldot.ld37.Game.PPM;

public class MainMenu extends State {

    private Texture background;
    private Texture arrow;

    private BitmapFont font;

    private Vector2[] positions;
    private Rectangle[] bounds;
    private int curPos;

    private Animation player;

    public MainMenu(GameStateManager gsm) {
        super(gsm);

        bounds = new Rectangle[3];
        bounds[0] = new Rectangle(710, 395, 90, 60);
        bounds[1] = new Rectangle(710, 475, 150, 60);
        bounds[2] = new Rectangle(710, 550, 90, 60);

        ContentManager.loadTexture("MenuBackground", "Materials/menu.png");
        ContentManager.loadTexture("Arrow", "Materials/menuArrow.png");
        background = ContentManager.getTexture("MenuBackground");
        arrow = ContentManager.getTexture("Arrow");

        curPos = 0;
        positions = new Vector2[3];

        positions[0] = new Vector2(635, 393);
        positions[1] = new Vector2(635, 472);
        positions[2] = new Vector2(635, 548);

        font = new BitmapFont(true);

        player = new Animation("Idle", ContentManager.getTexture("PlayerIdle"), 6, 4);
        player.setTargetHeight(120);
        player.setTargetWidth(80);
    }

    public void update(float dt) {
        for (int i = 0; i < bounds.length; i++) {
            Rectangle r = bounds[i];
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mouse);
            if (mouse.x >= r.x && mouse.x <= (r.x + r.width)) {
                if (mouse.y >= r.y && mouse.y <= (r.y + r.height)) {
                    curPos = i;

                    if(Gdx.input.justTouched()) {
                        choose();
                    }
                }
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            curPos--;
            if(curPos < 0) curPos = 2;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            curPos++;
            if(curPos == 3) curPos = 0;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            choose();
        }

        player.update(dt);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
    }
    public void render() {
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0, Game.WIDTH, Game.HEIGHT,
                0, 0, background.getWidth(), background.getHeight(), false, true);

        batch.draw(arrow, positions[curPos].x, positions[curPos].y, arrow.getWidth(), arrow.getHeight(),
                0, 0, arrow.getWidth(), arrow.getHeight(), false, true);

        mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);

        player.render(batch, new Vector2(350 / PPM, 580 / PPM));
        batch.end();
    }

    private void choose() {
        switch (curPos) {
            case 0:
                System.out.println("Play Selected");
                gsm.pushState(GameStateManager.LEVEL1);
                break;
            case 1:
                gsm.pushState(GameStateManager.CREDITS);
                break;
            case 2:
                Gdx.app.exit();
                break;
        }
    }

    public void dispose() {}
}
