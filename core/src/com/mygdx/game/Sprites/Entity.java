package com.mygdx.game.Sprites;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public class Entity extends Fixture {

    protected Entity(Body body, long addr) {
        super(body, addr);
    }
}
