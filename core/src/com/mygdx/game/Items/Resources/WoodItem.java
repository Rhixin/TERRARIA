package com.mygdx.game.Items.Resources;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Items.Resource;

public class WoodItem extends Resource {

    private static final Texture t = new Texture("RAW/gold.png");
    public WoodItem(String name, String description) {
        super(name, description);
    }

    public WoodItem() {
        super("Wood", "Craft Simple Items");
    }

    @Override
    public String getType() {
        return "Gold";
    }

    @Override
    public Texture getTexture() {
        return t;
    }

    @Override
    public int getValueinCoin() {
        return 5;
    }
}
