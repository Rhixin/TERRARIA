package com.mygdx.game.Helper;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Null;
import com.mygdx.game.Sprites.Block.Block;
import com.mygdx.game.Sprites.Block.BlockType.Dirt;
import com.mygdx.game.Sprites.Block.BlockType.Stone;
import com.mygdx.game.Terraria;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorldCreator {

    public static HashMap<Vector2, Block> blocksMap = new HashMap<>();

    public WorldCreator(World world, TiledMap map){


        //create body and fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

//        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
//            Rectangle rect = ((RectangleMapObject) object).getRectangle();
//
//            bdef.type = BodyDef.BodyType.StaticBody;
//            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Terraria.PPM, (rect.getY() + rect.getHeight() / 2) / Terraria.PPM);
//
//            body = world.createBody(bdef);
//
//            shape.setAsBox(rect.getWidth() / 2 / Terraria.PPM, rect.getHeight() / 2 / Terraria.PPM);
//            fdef.shape = shape;
//            body.createFixture(fdef);
//        }

        //mo create ko diri dynamically sa 2dbox collisions sa blocks using rectangle object pero wala pa sila sa world or tiledmap
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(2);

        for (int y = 0; y < layer.getHeight(); y++) {
            for (int x = 0; x < layer.getWidth(); x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    Rectangle rect = new Rectangle();
                    rect.set( x * layer.getTileWidth() * Terraria.ZOOM_FACTOR, y * layer.getTileHeight() * Terraria.ZOOM_FACTOR, layer.getTileWidth() * Terraria.ZOOM_FACTOR, layer.getTileHeight() * Terraria.ZOOM_FACTOR );

                    //after creating rectangle object with x and y position sa corresponding graphics nila kay add a collison box sa rectangle
                    //ari na ma add sa world
                    blocksMap.put(new Vector2(x,y), new Dirt(world, rect));
                    System.out.println("Block: " + blocksMap.get(new Vector2(x,y)));
                }
            }
        }

        blocksMap.forEach(((vector2, block) -> ((Dirt) block).changeRightTexture()));

//        layer = (TiledMapTileLayer) map.getLayers().get(2);
//
//        for (int y = 0; y < layer.getHeight(); y++) {
//            for (int x = 0; x < layer.getWidth(); x++) {
//                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
//                if (cell != null) {
//                    Rectangle rect = new Rectangle();
//                    rect.set( x * layer.getTileWidth() * Terraria.ZOOM_FACTOR, y * layer.getTileHeight() * Terraria.ZOOM_FACTOR, layer.getTileWidth() * Terraria.ZOOM_FACTOR, layer.getTileHeight() * Terraria.ZOOM_FACTOR );
//                    System.out.println("Block: " + rect.getX() + " and " + rect.getY());
//                    //after creating rectangle object with x and y position sa corresponding graphics nila kay add a collison box sa rectangle
//                    //ari na ma add sa world
//                    blocks.add(new Stone(world,rect));
//                }
//            }
//        }






    }
}
