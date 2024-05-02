package com.mygdx.game.Sprites.Block;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Terraria;

public abstract class Block {
    protected World world;
    protected TiledMap map;

    protected Rectangle rect;
    protected Body body;

    public Block (World world, TiledMap map, Rectangle rect){
        this.world = world;
        this.map = map;
        this.rect = rect;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        //BDEF DEFINING AND TUNINGGGGGG --------------
        //there are many types of body def, naay moving, static chuchu
        bdef.type = BodyDef.BodyType.StaticBody;
        //set ang postion sa imo bdef sa world. need nimo ug properties sa tile or rect rather kay naa niya ang point x ug point y
        bdef.position.set((rect.getX() + rect.getWidth() / 2) / Terraria.PPM, (rect.getY() + rect.getHeight() / 2) / Terraria.PPM);
        //Add to the world with our created bodydef
        body = world.createBody(bdef);

        //FDEF DEFINING AND TUNIIINGGGGGG------------------
        //define shape for -> fixturedef for -> body's actual Fixture
        //divide 2 kay start mn measurement sa shape sa center
        shape.setAsBox(rect.getWidth() / 2 / Terraria.PPM, rect.getHeight() / 2 / Terraria.PPM);
        fdef.shape = shape;
        body.createFixture(fdef);

    }
}
