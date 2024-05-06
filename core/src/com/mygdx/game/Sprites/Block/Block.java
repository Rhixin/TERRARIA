package com.mygdx.game.Sprites.Block;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Helper.Cell;
import com.mygdx.game.Helper.WorldCreator;
import com.mygdx.game.Terraria;
import jdk.internal.net.http.common.Pair;
import org.w3c.dom.Text;

public abstract class Block {
    protected World world;
    protected Body body;

    protected Sprite sprite;

    protected Texture texture;

    public Cell cell;


    public Block (World world, Rectangle rect, Texture texture){
        this.texture = texture;

        cell = new Cell( (int) rect.getX() / 32, (int) rect.getY() / 32);

        sprite = new Sprite(texture);
        sprite.setSize(32,32);

        this.world = world;

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

        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);

    }

    public void destroyed(){
        //WorldCreator.blocks.remove(this);
        world.destroyBody(body);
        sprite.setAlpha(0);

        //sprite.getTexture().dispose();
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

}
