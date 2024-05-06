package com.mygdx.game.Sprites.Block.BlockType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Sprites.Block.Block;

public class Stone extends Block {

    public Stone(World world, Rectangle rect) {
        super(world,rect, new Texture(Gdx.files.internal("RAW/stone.png")));
    }
}
