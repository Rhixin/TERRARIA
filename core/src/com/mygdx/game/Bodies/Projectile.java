package com.mygdx.game.Bodies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Projectile extends Sprite {
    public float damage;
    public float width, height;
    public float speed;

    protected World world;
    protected Body b2body;

    public Projectile(Texture texture) {
        super(texture);
    }

    public Body getBody(){
        return b2body;
    }


    public void render(float delta) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }

    public abstract float getDamage();

}
