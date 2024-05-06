package com.mygdx.game.Sprites.Block.BlockType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Sprites.Block.Block;
import com.mygdx.game.Terraria;

public class Dirt extends Block {
    public Dirt(World world,Rectangle rect) {
        super(world,rect, new Texture(Gdx.files.internal("dirt.png")));
    }
}
