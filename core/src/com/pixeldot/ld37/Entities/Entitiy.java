package com.pixeldot.ld37.Entities;

import com.pixeldot.ld37.Utilities.Animation;

import java.util.HashMap;

/**
 * Created by matt on 10/12/16.
 */
public abstract class Entitiy {
    protected HashMap<String, Animation> animations;
    private String currentAnimation;
    private String prevAnimation;
    protected boolean alive;


}
