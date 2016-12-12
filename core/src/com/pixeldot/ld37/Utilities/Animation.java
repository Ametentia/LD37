package com.pixeldot.ld37.Utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import static com.pixeldot.ld37.Game.PPM;

/**
 * An exact copy from last LD
 */
public class Animation {

    private float targetWidth;
    private float targetHeight;

    private String name;

    private TextureRegion texture;
    private int rows;
    private int columns;

    private float timePerFrame;
    private float sinceLastFrame;
    private int currentFrame;

    private final int totalFrames;

    private boolean flipX;
    private boolean flipY;

    private boolean finished;
    private int totalPlays;
    private int maxPlays;

    private float offsetX;
    private float offsetY;

    private int startFrame;
    private int endFrame;

    /**
     * Creates an animation from a texture
     * @param texture The texture to represent the animation
     * @param rows How many rows the animation has
     * @param columns How many columns the animation has
     */
    public Animation(String name, Texture texture, int rows, int columns) {
        this(name, texture, rows, columns, 0.1f);
    }

    /**
     * Creates an animation from a texture
     * @param texture The texture to represent the animation
     * @param rows How many rows the animation has
     * @param columns How many columns the animation has
     * @param timePerFrame How much time (in seconds) a frame lasts for
     */
    public Animation(String name, Texture texture, int rows, int columns, float timePerFrame) {
        this.texture = new TextureRegion(texture);
        this.rows = rows;
        this.columns = columns;

        this.name = name;

        totalFrames = rows * columns;
        endFrame = totalFrames;

        this.timePerFrame = timePerFrame;

        flipX = flipY = false;

        totalPlays = 0;
        maxPlays = -1;

        offsetX = offsetY = 0;
        targetWidth = texture.getWidth() / columns;
        targetHeight = texture.getHeight() / rows;
        startFrame=0;
    }

    /**
     * Moves the animation along to the next frame <br>
     *     Only if the total time since last frame update is greater than the time per frame
     * @param dt The time since the last frame
     */
    public void update(float dt) {
        if(maxPlays == totalPlays) return;
        sinceLastFrame += dt;
        if(sinceLastFrame >= timePerFrame) {
            sinceLastFrame -= timePerFrame;

            currentFrame++;
            if (currentFrame == endFrame) {
                currentFrame = startFrame;
                totalPlays++;
                if(totalPlays == maxPlays) {
                    finished = true;
                }
            }
        }
    }

    /**
     * Draws the animation on the screen on its current frame
     * @param batch The Sprite Batch used to render the texture on screen
     */
    public void render(SpriteBatch batch, Vector2 position) {
        int width = texture.getTexture().getWidth() / columns;
        int height = texture.getTexture().getHeight() / rows;

        int row = (int)(((float) currentFrame) / ((float) columns));
        int col = currentFrame % columns;

        texture.setRegion(col * width, row * height, width, height);

        texture.flip(flipX, !flipY);
        batch.draw(texture, (position.x * PPM) - (targetWidth / 2), (position.y * PPM) - (targetHeight / 2),
                targetWidth, targetHeight);
    }

    // Getters
    public TextureRegion getTexture() { return texture; }
    public int getRows() { return rows; }
    public int getColumns() { return columns; }
    public boolean isFlipX() { return flipX; }
    public boolean isFlipY() { return flipY; }
    public boolean isFinished() { return finished; }
    public int getCurrentFrame() { return currentFrame; }
    public int getTotalFrames() { return totalFrames; }
    public String getName() { return name; }

    // Setters
    public void setTimePerFrame(float time) {
        timePerFrame = time;
    }
    public void setFlipX(boolean flip) { flipX = flip; }
    public void setFlipY(boolean flip) { flipY = flip; }
    public void setFrame(int frame) { this.currentFrame = frame; }
    public void setMaxPlays(int plays) { this.maxPlays = plays; }
    public void reset() {
        maxPlays = -1;
        totalPlays = 0;
        finished = false;
    }
    public void setOffset(float x, float y) {
        offsetX = x;
        offsetY = y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTargetWidth(float width) { targetWidth = width; }
    public void setTargetHeight(float height) { targetHeight = height; }

    public int getStartFrame() {return startFrame;}
    public void setStartFrame(int startFrame) {this.startFrame = startFrame;}

    public int getEndFrame() {
        return endFrame;
    }

    public void setEndFrame(int endFrame) {
        this.endFrame = endFrame;
    }
}
