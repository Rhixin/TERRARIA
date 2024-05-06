package com.mygdx.game.Sprites;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Helper.AnimationHelper;
import com.mygdx.game.Helper.AnimationState;
import com.mygdx.game.Helper.Cell;
import com.mygdx.game.Helper.WorldCreator;
import com.mygdx.game.Screens.EntityScreen;
import com.mygdx.game.Sprites.Block.Block;
import com.mygdx.game.Sprites.Block.BlockType.Dirt;
import com.mygdx.game.Terraria;

public class Player extends Sprite{
    public World world;
    public Body b2body;
    private TextureRegion playerStand;
    private static final TextureAtlas ATLAS = new TextureAtlas("ANIMATION/steve.txt");

    //animation

    public Animation<TextureRegion> walkRightAnimation;
    public Animation<TextureRegion> walkLeftAnimation;
    public Animation<TextureRegion> idleAnimation;

    private AnimationState currentAnimationState;


    public float stateTime = 0f;

    public Player (World world, EntityScreen screen){
        super(ATLAS.findRegion("steve"));
        this.world = world;
        this.currentAnimationState = AnimationState.IDLE;

        definePlayer();

        playerStand = new TextureRegion(getTexture(), 0,20,65 ,44 );
        setBounds(0,0,48 / Terraria.PPM  , 44 / Terraria.PPM );
        setRegion(playerStand);

        initAnimations();
    }

    private void initAnimations() {


        Texture idleSheet = new Texture(Gdx.files.internal("RAW/player_idle.png"));
        Texture walkRightSheet = new Texture(Gdx.files.internal("RAW/player_walkright.png"));
        Texture walkLeftSheet = new Texture(Gdx.files.internal("RAW/player_walkleft.png"));
        walkRightAnimation = AnimationHelper.getAnimation(1,8,walkRightSheet,0.1f);
        walkLeftAnimation = AnimationHelper.getAnimation(1,8,walkLeftSheet,0.1f);
        idleAnimation = AnimationHelper.getAnimation(1,2,idleSheet,0.2f);

    }

    private void definePlayer() {
        //define and tuning BDEF
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(384 / Terraria.PPM * 3,384 / Terraria.PPM * 3);

        b2body = world.createBody(bdef);

        //define and tuning FDEF
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10 / Terraria.PPM , 22 / Terraria.PPM);
        fdef.shape = shape;
        fdef.friction = 20f;
        //fdef.density = 0.5f;


        b2body.createFixture(fdef);

        //b2body.setGravityScale(3f);


    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

        if(b2body.getLinearVelocity().x == 0 && b2body.getLinearVelocity().y == 0){
            setState(AnimationState.IDLE);
        } else if(b2body.getLinearVelocity().x > 0){
            setState(AnimationState.WALK_RIGHT);
        } else if (b2body.getLinearVelocity().x < 0){
            setState(AnimationState.WALK_LEFT);
        }

    }

    public void placeBlock(int x, int y){
        com.badlogic.gdx.math.Rectangle rect = new com.badlogic.gdx.math.Rectangle();
        rect.set( x * 32, y * 32 , 32 , 32);
        Dirt d = new Dirt(world,rect);
        d.changeRightTexture();
        WorldCreator.blocksMap.put(new Vector2(x,y), d);
        System.out.println("succcesss place bloc");
    }

    public void deleteBlock(int x, int y){

        Block b = WorldCreator.blocksMap.get(new Vector2(x,y));

        if(b == null){
            System.out.println("To delete X: " + x + " and Y: " + y);
            return;
        }

        b.breaklife -=1;

        System.out.println(b.breaklife);
        if(b.breaklife <= 0){
            b.destroyed();

        }

    }

    public void resetBlock(int x, int y) {
        Block b = WorldCreator.blocksMap.get(new Cell(x,y));
        if(b == null){
            return;
        }
        b.breaklife = 100;
    }

    private void setState(AnimationState newState) {
        currentAnimationState = newState;
    }

    public AnimationState getCurrentAnimationState(){
        return currentAnimationState;
    }





}
