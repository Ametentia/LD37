package com.pixeldot.ld37.Entities;

import com.pixeldot.ld37.Utilities.Animation;

import java.util.HashMap;

public abstract class Entitiy {
    protected HashMap<String, Animation> animations;
    private String currentAnimation;
    private String prevAnimation;
    protected boolean alive;


}
