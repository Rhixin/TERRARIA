package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Helper.Cell;
import com.mygdx.game.Helper.WorldCreator;
import com.mygdx.game.Screens.EntityScreen;
import com.mygdx.game.Sprites.Block.Block;
import com.mygdx.game.Sprites.Block.BlockType.Dirt;
import com.mygdx.game.Terraria;
import jdk.internal.net.http.common.Pair;
import org.w3c.dom.Text;

import java.awt.*;

public class Player extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion playerStand;
    private static final TextureAtlas ATLAS = new TextureAtlas("steve.txt");

    public Player (World world, EntityScreen screen){
        super(ATLAS.findRegion("steve"));
        this.world = world;
        definePlayer();
        playerStand = new TextureRegion(getTexture(), 0,0,32 ,64 );
        setBounds(0,0,30 / Terraria.PPM  , 60 / Terraria.PPM );
        setRegion(playerStand);
    }

    private void definePlayer() {
        //define and tuning BDEF
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(384 / Terraria.PPM ,384 / Terraria.PPM );

        b2body = world.createBody(bdef);

        //define and tuning FDEF
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(15 / Terraria.PPM , 30 / Terraria.PPM);
        fdef.shape = shape;


        b2body.createFixture(fdef);

        //b2body.setGravityScale(3f);


    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }

    public void placeBlock(int x, int y){
        com.badlogic.gdx.math.Rectangle rect = new com.badlogic.gdx.math.Rectangle();
        rect.set( x * 32, y * 32 , 32 , 32);
        WorldCreator.blocks.add(new Dirt(world,rect));
        System.out.println("succcesss place bloc");
    }

    public void deleteBlock(int x, int y){
        //Cell cell = new Cell(x,y);
        for (Block b :WorldCreator.blocks){
            if( x == b.cell.row && y == b.cell.column){
                b.destroyed();
            }
        }
    }
}
