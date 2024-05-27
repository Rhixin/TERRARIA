package com.mygdx.game.Block.BlockType;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Block.Block;
import com.mygdx.game.Helper.AnimationHelper;
import com.mygdx.game.Items.Item;
import com.mygdx.game.Items.Resources.GoldItem;
import com.mygdx.game.Sprites.Drop;

public class Gold extends Block {
    private static final Texture defaultTexture = new Texture("RAW/gold_block.png");

    public Gold(World world, Rectangle rect) {
        super(world,rect, defaultTexture);
    }


    @Override
    public Drop blocktodrop() {
        Item item = new GoldItem();
        Drop drop = new Drop(this.world, cell.row * 32, cell.column * 32, item);
        destroyed();
        return drop;
    }
}
