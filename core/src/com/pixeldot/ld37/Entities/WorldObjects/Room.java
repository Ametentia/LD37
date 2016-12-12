package com.pixeldot.ld37.Entities.WorldObjects;

import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.pixeldot.ld37.Entities.WorldObject;
import com.pixeldot.ld37.Utilities.Animation;
import com.pixeldot.ld37.Utilities.ContentManager;

import static com.pixeldot.ld37.Game.HEIGHT;
import static com.pixeldot.ld37.Game.PPM;
import static com.pixeldot.ld37.Game.WIDTH;

/**
 * Created by james on 11/12/16.
 */
public class Room extends WorldObject {

    private Texture background;
    private Texture backgroundRice;

    private Texture backgroundLeft;
    private Texture backgroundRight;

    private Animation aisu;
    private boolean endRun = false;
    private Vector2 endRunPos;
    private boolean runDone = false;
    private float timeSince = 0;
    private boolean soundStart = false;

    public Room(Body body) {
        super(body);

        background = ContentManager.getTexture("Background");
        backgroundRice = ContentManager.getTexture("Rice");
        backgroundLeft = ContentManager.getTexture("LeftAutumn");
        backgroundRight = ContentManager.getTexture("RightAutumn");
        aisu = new Animation("Aisu run",ContentManager.getTexture("Shadow"),1,1);
        aisu.setTargetHeight(90);
        aisu.setTargetWidth(60);
        aisu.setFlipX(true);
        endRunPos=new Vector2(933/PPM, (HEIGHT - 130)/PPM);

    }

    public void update(float dt) {
        if(endRun){
            if(!soundStart) {
                ContentManager.getRandomSound("Walk",1,3).play();
                soundStart=true;
            }
            timeSince+=dt;
            endRunPos.set(endRunPos.x-120*dt/PPM,endRunPos.y+1.5f*(float)Math.sin(timeSince* 20f)/PPM);
            if(endRunPos.x<350/PPM) {
                endRun = false;
                runDone = true;
                ContentManager.stopRandomNoise("Walk",1,3);
            }
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(backgroundRice, 0, 0, WIDTH, HEIGHT,
                0, 0, backgroundRice.getWidth(), backgroundRice.getHeight(), false, true);

        if(endRun){
            aisu.render(batch,endRunPos);
        }

        batch.draw(background, 0, 0, WIDTH, HEIGHT,
                0, 0, background.getWidth(), background.getHeight(), false, true);

        batch.draw(backgroundLeft, 0, 0, backgroundLeft.getWidth() / 1.5f, HEIGHT,
                0, 0, backgroundLeft.getWidth(), backgroundLeft.getHeight(), false, true);

        batch.draw(backgroundRight, (1161 + 378) / 1.5f, 0, backgroundRight.getWidth() / 1.5f, HEIGHT,
                0, 0, backgroundRight.getWidth(), backgroundRight.getHeight(), false, true);
    }

    public void onCollisionBegin(WorldObject worldObject, Contact contact) {}
    public void onCollisionEnd(WorldObject worldObject, Contact contact) {}

    private void setBackgroundRice(String key) { backgroundRice = ContentManager.getTexture(key); }
    private void setBackgroundLeft(String key) { backgroundLeft = ContentManager.getTexture(key); }
    private void setBackgroundRight(String key) { backgroundRight = ContentManager.getTexture(key); }

    public boolean isEndRun() {
        return endRun;
    }

    public void setEndRun(boolean endRun) {
        this.endRun = endRun;
    }

    public boolean isRunDone() {
        return runDone;
    }
}
