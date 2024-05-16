package com.mygdx.game.Items.Resources;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Items.Resource;

public class IronItem extends Resource {
    private static final Texture t = new Texture("RAW/iron.png");
    public IronItem(String name, String description) {
        super(name, description);
    }

    public IronItem() {
        super("Iron", "Trade valuable stuffs with villagers");
    }

    @Override
    public String getType() {
        return "Iron";
    }

    @Override
    public Texture getTexture() {
        return t;
    }

    @Override
    public int getValueinCoin() {
        return 15;
    }
}
