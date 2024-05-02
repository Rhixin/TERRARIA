package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Screens.EntityScreen;
import com.mygdx.game.Terraria;
import org.w3c.dom.Text;

public class Player extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion playerStand;
    private static final TextureAtlas ATLAS = new TextureAtlas("steve.txt");

    public Player (World world, EntityScreen screen){
        super(ATLAS.findRegion("steve"));
        this.world = world;
        definePlayer();
        playerStand = new TextureRegion(getTexture(), 0,0,32,64);
        setBounds(0,0,30 / Terraria.PPM, 60 / Terraria.PPM);
        setRegion(playerStand);

    }

    private void definePlayer() {
        //define and tuning BDEF
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(384 / Terraria.PPM,384 / Terraria.PPM);

        b2body = world.createBody(bdef);

        //define and tuning FDEF
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(15 / Terraria.PPM, 30 / Terraria.PPM);
        fdef.shape = shape;

        b2body.createFixture(fdef);
        //b2body.setGravityScale(3f);


    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }
}
