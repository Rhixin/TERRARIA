package com.mygdx.game.Helper;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Block.Block;
import com.mygdx.game.Block.BlockType.Diamond;
import com.mygdx.game.Block.BlockType.Dirt;
import com.mygdx.game.Block.BlockType.Gold;
import com.mygdx.game.Block.BlockType.Iron;
import com.mygdx.game.Terraria;

import java.util.HashMap;

public class WorldCreator {
    public static HashMap<Vector2, Block> syncWorldAndTiledMap(World world, TiledMap map){

        HashMap<Vector2, Block> tiles = new HashMap<>();
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(2);

        for (int y = 0; y < layer.getHeight(); y++) {
            for (int x = 0; x < layer.getWidth(); x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    Rectangle rect = new Rectangle();
                    rect.set( x * layer.getTileWidth() * Terraria.ZOOM_FACTOR, y * layer.getTileHeight() * Terraria.ZOOM_FACTOR, layer.getTileWidth() * Terraria.ZOOM_FACTOR, layer.getTileHeight() * Terraria.ZOOM_FACTOR );


                    tiles.put(new Vector2(x,y), new Dirt(world, rect));
                }
            }
        }

//        layer = (TiledMapTileLayer) map.getLayers().get(3);
//
//        for (int y = 0; y < layer.getHeight(); y++) {
//            for (int x = 0; x < layer.getWidth(); x++) {
//                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
//                if (cell != null) {
//                    Rectangle rect = new Rectangle();
//                    rect.set( x * layer.getTileWidth() * Terraria.ZOOM_FACTOR, y * layer.getTileHeight() * Terraria.ZOOM_FACTOR, layer.getTileWidth() * Terraria.ZOOM_FACTOR, layer.getTileHeight() * Terraria.ZOOM_FACTOR );
//
//
//                    tiles.put(new Vector2(x,y), new Diamond(world, rect));
//                    System.out.println("Block: " + tiles.get(new Vector2(x,y)));
//                }
//            }
//        }

        layer = (TiledMapTileLayer) map.getLayers().get(3);

        for (int y = 0; y < layer.getHeight(); y++) {
            for (int x = 0; x < layer.getWidth(); x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    Rectangle rect = new Rectangle();
                    rect.set( x * layer.getTileWidth() * Terraria.ZOOM_FACTOR, y * layer.getTileHeight() * Terraria.ZOOM_FACTOR, layer.getTileWidth() * Terraria.ZOOM_FACTOR, layer.getTileHeight() * Terraria.ZOOM_FACTOR );


                    tiles.put(new Vector2(x,y), new Iron(world, rect));
                }
            }
        }

        layer = (TiledMapTileLayer) map.getLayers().get(4);

        for (int y = 0; y < layer.getHeight(); y++) {
            for (int x = 0; x < layer.getWidth(); x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    Rectangle rect = new Rectangle();
                    rect.set( x * layer.getTileWidth() * Terraria.ZOOM_FACTOR, y * layer.getTileHeight() * Terraria.ZOOM_FACTOR, layer.getTileWidth() * Terraria.ZOOM_FACTOR, layer.getTileHeight() * Terraria.ZOOM_FACTOR );


                    tiles.put(new Vector2(x,y), new Gold(world, rect));
                }
            }
        }

        layer = (TiledMapTileLayer) map.getLayers().get(5);

        for (int y = 0; y < layer.getHeight(); y++) {
            for (int x = 0; x < layer.getWidth(); x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    Rectangle rect = new Rectangle();
                    rect.set( x * layer.getTileWidth() * Terraria.ZOOM_FACTOR, y * layer.getTileHeight() * Terraria.ZOOM_FACTOR, layer.getTileWidth() * Terraria.ZOOM_FACTOR, layer.getTileHeight() * Terraria.ZOOM_FACTOR );


                    tiles.put(new Vector2(x,y), new Diamond(world, rect));
                }
            }
        }


        return tiles;
    }
}
