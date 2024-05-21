package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class WeaponObject extends Sprite {

    protected float damage;
    public float width, height, angle;
    public float speed;

    private World world;
    public Body b2body;
    protected Player player;

    public abstract void update(float delta);


    public abstract void render(float delta);

    public abstract void useWeapon(float delta);

    public abstract float getDamage();
}
