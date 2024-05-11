package com.mygdx.game.Items;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Block.Block;

public interface Placeable {
    public Block placeItem(int x, int y, World world);
}
