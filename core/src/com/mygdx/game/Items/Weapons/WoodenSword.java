package com.mygdx.game.Items.Weapons;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Items.Weapon;

public class WoodenSword extends Weapon {

    public WoodenSword(String name, String description, float damage, float hp) {
        super(name, description, damage, hp);
    }

    @Override
    public void useItem() {

    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public Texture getTexture() {
        return null;
    }
}
