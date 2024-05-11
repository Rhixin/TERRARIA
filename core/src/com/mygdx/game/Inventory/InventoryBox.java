package com.mygdx.game.Inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Helper.BoxActor;
import com.mygdx.game.Items.Item;

public class InventoryBox extends BoxActor {
    private static final Texture texture = new Texture(Gdx.files.internal("RAW/item_box.png"));

    private static final Texture texture_onhold = new Texture(Gdx.files.internal("RAW/item_box_hold.png"));
    public ItemBox itembox;
    public InventoryBox(ItemBox itemBox) {
        super(texture);
        this.itembox = itemBox;
    }

    public Item getItem(){
        return itembox.getItem();
    }

    public int getItemCount(){
        return itembox.getCount();
    }

    public void setOnHoldTexture(){
        setTexture(texture_onhold);
    }


    public void setDefaultTexture() {
        setTexture(texture);
    }
}
