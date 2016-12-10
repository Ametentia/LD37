package com.pixeldot.ld37.Utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;

/**
 *A direct copy from the last LD
 */

public class ContentManager {
    private static HashMap<String, Texture> textures;
    private static HashMap<String, BitmapFont> fonts;
    private static HashMap<String, Sound> sounds;
    private static HashMap<String, Music> music;

    public ContentManager(){
        textures = new HashMap<String,Texture>();
        fonts = new HashMap<String,BitmapFont>();
        sounds = new HashMap<String,Sound>();
        music = new HashMap<String,Music>();
    }
    public void loadTexture(String name, String path) {textures.put(name, new Texture("Textures/" +path));}

    public Texture getTexture(String name) {
        if(!textures.containsKey(name))
            throw new IllegalArgumentException("Error: Unknown Texture " + name);

        return textures.get(name);
    }

    public void loadFont(String name, String path, int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/" + path));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = size;
        param.color = Color.ORANGE;
        param.flip = true;

        fonts.put(name, generator.generateFont(param));
        generator.dispose();
    }

    public BitmapFont getFont(String name) {
        if(!fonts.containsKey(name))
            throw new IllegalArgumentException("Error: Unknown Font " + name);

        return fonts.get(name);
    }

    public void loadSound(String name, String path) {
        sounds.put(name, Gdx.audio.newSound(Gdx.files.internal("Sounds/" + path)));
    }

    public Sound getSound(String name) {
        if(!sounds.containsKey(name))
            throw new IllegalArgumentException("Error: Unknown Sound Effect " + name);

        return sounds.get(name);
    }

    public void loadMusic(String name, String path) {
        music.put(name, Gdx.audio.newMusic(Gdx.files.internal("Music/" + path)));
    }

    public Music getMusic(String name) {
        if(!music.containsKey(name))
            throw new IllegalArgumentException("Error: Unknown Music " + name);

        return music.get(name);
    }
}
