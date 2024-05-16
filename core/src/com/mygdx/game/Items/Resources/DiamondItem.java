package com.mygdx.game.Items.Resources;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Items.Resource;

public class DiamondItem extends Resource {
    private static final Texture t = new Texture("RAW/diamond.png");

    public DiamondItem(String name, String description) {
        super(name, description);
    }

    public DiamondItem() {
        super("Diamond", "You can trade diamonds with villagers for information or weapons");
    }

    @Override
    public String getType() {
        return "Diamond";
    }

    @Override
    public Texture getTexture() {
        return t;
    }

    @Override
    public int getValueinCoin() {
        return 50;
    }
}
