package com.mygdx.game.Items.BlockItems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Block.Block;
import com.mygdx.game.Block.BlockType.Dirt;
import com.mygdx.game.Items.BlockItem;

public class DirtItem extends BlockItem {
    private static final Texture t = new Texture("RAW/dirt_item.png");

    public DirtItem(String name, String description, Block block) {
        super(name, description, block);
    }
    public DirtItem(Block block) {
        super("Dirt", "Useless Shit",block);
    }


    @Override
    public String getType() {
        return "Dirt";
    }

    @Override
    public Texture getTexture() {
        return t;
    }

    @Override
    public Block placeItem(int x, int y, World world) {
        Rectangle rect = new Rectangle();
        rect.set( x * 32, y * 32 , 32 , 32);

        Dirt d = new Dirt(world,rect);
        return d;
    }
}
