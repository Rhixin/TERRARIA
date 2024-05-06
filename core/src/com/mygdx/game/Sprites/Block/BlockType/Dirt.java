package com.mygdx.game.Sprites.Block.BlockType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Null;
import com.mygdx.game.Helper.AnimationHelper;
import com.mygdx.game.Helper.Cell;
import com.mygdx.game.Helper.WorldCreator;
import com.mygdx.game.Sprites.Block.Block;
import com.mygdx.game.Terraria;

public class Dirt extends Block {

    private static final TextureRegion[][] dirtTextures = AnimationHelper.getTexturePack(3, 6, new Texture("RAW/dynamic_dirt.png"));;
    public Dirt(World world,Rectangle rect) {

        super(world,rect, new Texture(Gdx.files.internal("RAW/dirt.png")));
    }

    public Dirt(World world,Rectangle rect, int x , int y) {

        super(world,rect, new Texture(Gdx.files.internal("RAW/dirt.png")));
        changeTexture(dirtTextures[x][y]);
    }

    public void changeRightTexture(){
        Vector2 top = new Vector2(cell.row - 1, cell.column);
        Vector2 bottom = new Vector2(cell.row + 1, cell.column);
        Vector2 left = new Vector2(cell.row, cell.column -1);
        Vector2 right = new Vector2(cell.row, cell.column + 1);

        int ansX, ansY;

        if(WorldCreator.blocksMap.get(top) == null){
            if(WorldCreator.blocksMap.get(left) == null){
                ansX = 0;
                ansY = 0;
            } else if (WorldCreator.blocksMap.get(right) == null) {
                ansX = 0;
                ansY = 2;
            } else {
                ansX = 0;
                ansY = 1;
            }
        } else if (WorldCreator.blocksMap.get(left) == null){
            if(WorldCreator.blocksMap.get(bottom) == null){
                ansX = 2;
                ansY = 0;
            } else {
                ansX = 1;
                ansY = 0;
            }
        } else if (WorldCreator.blocksMap.get(right) == null){
            if(WorldCreator.blocksMap.get(bottom) == null){
                ansX = 2;
                ansY = 2;
            } else {
                ansX = 1;
                ansY = 2;
            }
        } else if (WorldCreator.blocksMap.get(bottom) == null) {
            ansX = 2;
            ansY = 1;
        } else {
            ansX = 1;
            ansY = 1;
        }

        changeTexture(dirtTextures[ansX][ansY]);
    }
}
