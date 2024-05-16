package com.mygdx.game.Items.Resources;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Items.Resource;

public class GoldItem extends Resource {
    private static final Texture t = new Texture("RAW/gold.png");
    public GoldItem(String name, String description) {
        super(name, description);
    }

    public GoldItem() {
        super("Gold", "Trade valuable stuffs with villagers");
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
        return 30;
    }
}
