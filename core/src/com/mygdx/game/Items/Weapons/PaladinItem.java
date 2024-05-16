package com.mygdx.game.Items.Weapons;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Items.Weapon;
import com.mygdx.game.Sprites.WorldWeapons.Paladin;

public class PaladinItem extends Weapon {
    private static final Texture t = new Texture("item_demo.png");
    private Paladin paladin;
    public PaladinItem(String name, String description, Paladin paladin) {
        super(name, description);
        this.paladin = paladin;
    }

    public PaladinItem(Paladin paladin) {
        super("Paladin", "To damage enemies");
        this.paladin = paladin;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public Texture getTexture() {
        return t;
    }

    public Paladin getWeaponObject(){
        return paladin;
    }
}
