package com.mygdx.game.Items;


import com.mygdx.game.Block.Block;

public abstract class BlockItem extends Item implements Placeable{
    Block block;
    public BlockItem(String name, String description, Block block) {
        super(name, description);
        this.block = block;
    }

}
