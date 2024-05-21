package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Items.Item;

public abstract class Projectile extends Sprite {
    public float damage;
    public float width, height;
    public float speed;

    private World world;
    protected Body b2body;




    public Body getBody(){
        return b2body;
    }


    public void render(float delta) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }

    public abstract float getDamage();

}
