package com.mygdx.game.Inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Helper.BoxActor;

public class InventoryBox extends BoxActor {

    public Item item1 = new Item(new Texture(Gdx.files.internal("RAW/item_demo.png")));
    private final int MAX_NUMBER = 64;

    public InventoryBox(Texture backgroundTexture) {
        super(backgroundTexture);
    }


}
