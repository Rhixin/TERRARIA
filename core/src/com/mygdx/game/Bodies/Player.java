package com.mygdx.game.Bodies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Helper.*;
import com.mygdx.game.Items.Coin;
import com.mygdx.game.Items.Item;
import com.mygdx.game.Items.Placeable;
import com.mygdx.game.Items.Weapon;
import com.mygdx.game.Items.Weapons.PaladinItem;
import com.mygdx.game.Items.Weapons.PistolItem;
import com.mygdx.game.MiningWorld;
import com.mygdx.game.Block.Block;
import com.mygdx.game.Screens.Hud;
import com.mygdx.game.Terraria;
import java.util.ArrayList;
import java.util.HashSet;

public class Player extends Sprite{
    private static final TextureAtlas ATLAS = new TextureAtlas("ANIMATION/steve.txt");
    private World world;
    private Body b2body;
    private Animation<TextureRegion> walkRightAnimation;
    private Animation<TextureRegion> walkLeftAnimation;
    private Animation<TextureRegion> idleAnimation;

    private Animation<TextureRegion> mineRightAnimation;
    private Animation<TextureRegion> mineLeftAnimation;
    private Animation<TextureRegion> mineFrontAnimation;

    private AnimationState currentAnimationState;
    private float stateTime = 0f;
    private ArrayList<Pair<Item, Integer>> inventory;
    private HashSet<Body> playerDeletes;
    private int currentItem = 0;
    private Hud hud;
    private GameMode current_mode;
    private SoundManager soundManager = new SoundManager();

    private float life;

    public boolean isMining = false;

    public Player (World world, Hud hud){
        super(ATLAS.findRegion("steve"));
        this.world = world;
        this.hud = hud;
        this.currentAnimationState = AnimationState.IDLE;
        life = 100;
        current_mode = GameMode.MINING_MODE;

        inventory = new ArrayList<>(8);
        playerDeletes = new HashSet<>();

        for(int i = 0; i < 8; i++){
            inventory.add(new Pair<>(null,0));
        }

        inventory.add(0,new Pair<>(new PaladinItem(), 1));
        inventory.add(1,new Pair<>(new PistolItem(), 1));


        definePlayer();

        TextureRegion playerStand = new TextureRegion(getTexture(), 0, 20, 65, 44);
        setBounds(0,0,48 / Terraria.PPM  , 44 / Terraria.PPM );
        setRegion(playerStand);


        initAnimations();
    }

    private void initAnimations() {
        Texture idleSheet = new Texture(Gdx.files.internal("RAW/player_idle.png"));
        Texture walkRightSheet = new Texture(Gdx.files.internal("RAW/player_walkright.png"));
        Texture walkLeftSheet = new Texture(Gdx.files.internal("RAW/player_walkleft.png"));

        Texture mineFrontSheet = new Texture(Gdx.files.internal("RAW/player_minefront.png"));
        Texture mineRightSheet = new Texture(Gdx.files.internal("RAW/player_mineright.png"));
        Texture mineLeftSheet = new Texture(Gdx.files.internal("RAW/player_mineleft.png"));

        walkRightAnimation = AnimationHelper.getAnimation(1,11,walkRightSheet,0.1f);
        walkLeftAnimation = AnimationHelper.getAnimation(1,11,walkLeftSheet,0.1f);
        idleAnimation = AnimationHelper.getAnimation(1,12,idleSheet,0.2f);

        mineFrontAnimation = AnimationHelper.getAnimation(1,12,mineFrontSheet,0.1f);
        mineRightAnimation = AnimationHelper.getAnimation(1,12,mineRightSheet,0.1f);
        mineLeftAnimation = AnimationHelper.getAnimation(1,12,mineLeftSheet,0.2f);

    }

    private void definePlayer() {

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(384 / Terraria.PPM * 3,384 / Terraria.PPM * 3);

        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10 / Terraria.PPM , 22 / Terraria.PPM);
        fdef.shape = shape;
        fdef.friction = 20f;


        b2body.createFixture(fdef).setUserData(this);
    }

    public void update(float dt){
        stateTime += dt;
    }

    public void render(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

        if(b2body.getLinearVelocity().x == 0 && b2body.getLinearVelocity().y == 0){
            setState(AnimationState.IDLE);
        } else if(b2body.getLinearVelocity().x > 0){
            setState(AnimationState.WALK_RIGHT);
        } else if (b2body.getLinearVelocity().x < 0){
            setState(AnimationState.WALK_LEFT);
        }

        TextureRegion currentFrame;
        if(currentAnimationState == AnimationState.IDLE){

            if(isMining){
                currentFrame = mineFrontAnimation.getKeyFrame(stateTime, true);
            } else {
                currentFrame = getIdleAnimation().getKeyFrame(stateTime, true);
            }


        } else if (currentAnimationState == AnimationState.WALK_RIGHT){
            if(isMining){
                currentFrame = mineRightAnimation.getKeyFrame(stateTime, true);
            } else {
                currentFrame = walkRightAnimation.getKeyFrame(stateTime, true);
            }

        } else {

            if(isMining){
                currentFrame = mineLeftAnimation.getKeyFrame(stateTime, true);
            } else {
                currentFrame = getWalkLeftAnimation().getKeyFrame(stateTime, true);
            }

        }

        setRegion(currentFrame);
    }

    public void placeBlock(int x, int y){

        if(current_mode == GameMode.COMBAT_MODE) return;

        Vector2 tile = new Vector2(x,y);
        Block b = MiningWorld.tilesMap.get(tile);
        if(b != null){
            return;
        }

        Block new_block = null;

        Item item = inventory.get(currentItem).getFirst();
        Integer count = inventory.get(currentItem).getSecond();

        if(item instanceof Placeable && count > 0){
            new_block = ((Placeable) item).placeItem(x, y, world);
            inventory.get(currentItem).setSecond(count - 1);
            if(count - 1 == 0){
                inventory.set(currentItem, new Pair<>(null,0));
            }
        }

        MiningWorld.tilesMap.put(tile, new_block);
        soundManager.playPlaceDirt();

        syncHudInventory();
    }

    public void deleteBlock(int x, int y){

        if(current_mode == GameMode.COMBAT_MODE) return;


        Vector2 tile = new Vector2(x,y);
        Block b = MiningWorld.tilesMap.get(tile);

        if(b == null){
            return;
        }

        //TODO damage of player based on current item
        b.breaklife -=2;
        //--------------------------------------------

        isMining = true;

        if(b.breaklife <= 0){
            SoundManager.playBreakBlock();
            Drop drop = b.blocktodrop();
            MiningWorld.drops.add(drop);
            MiningWorld.tilesMap.put(tile, null);
        }

    }

    public void resetBlock(int x, int y) {
        if(current_mode == GameMode.COMBAT_MODE) return;

        isMining = false;

        Block b = MiningWorld.tilesMap.get(new Cell(x,y));
        if(b == null){
            return;
        }

        b.breaklife = 100;
    }

    public void getDrop(Drop drop, int count){
        if(current_mode == GameMode.COMBAT_MODE) return;

        MiningWorld.drops.remove(drop);
        addToInventory(drop, count);
        drop.setAlpha(0);
        MiningWorld.bodiesToremove.add(drop.getBody());
        syncHudInventory();
        soundManager.playGetDrop();
    }


    private boolean addToInventory(Drop drop, int count){
        int free_place = -1;
        int ctr = 0;
        for(Pair<Item, Integer> p : inventory){
            if(p.getFirst() == null && free_place == -1) {
                free_place = ctr;
            }

            if (ctr == 7 && p.getFirst() == null){
                inventory.set(free_place, new Pair<>(drop.getItem(), count));
            } else if(p.getFirst() != null && p.getFirst().getClass()  == drop.getItem().getClass()){
                p.setSecond(p.getSecond() + count);
                break;
            }

            ctr++;
        }

        return false;
    }

    public void attack(float dt, float x, float y){

        Item weapon =  currentItemPair().getFirst();

        if(weapon instanceof Weapon){
            WeaponObject wpo = ((Weapon) weapon).getWeaponObject(world, this);
            wpo.useWeapon(dt, x,y);
        }

        setCurrent_mode(GameMode.ATTACKING_MODE);
    }

    public void buySomething(Drop drop, int cost){
        int balance = getBalance();

        if(balance >= cost){
            SoundManager.playGoodLookingWeapon();
            getDrop(drop,1);
            minusMoney(cost);
        } else {
            SoundManager.playNotEnoughMoney();
        }

    }

    private int getBalance(){
        for(Pair<Item, Integer> i : inventory){
            if(i.getFirst() instanceof Coin){
                return i.getSecond();
            }
        }

        return 0;
    }

    private void minusMoney(int minus){
        for(Pair<Item, Integer> i : inventory){
            if(i.getFirst() instanceof Coin){
                i.setSecond(i.getSecond() - minus);
            }
        }
    }

    public void syncHudInventory(){
        hud.update(0, inventory, currentItem);
    }

    public void syncInventoryHud(){
        hud.update(0, inventory);
    }



    private void setState(AnimationState newState) {
        currentAnimationState = newState;
    }

    public AnimationState getCurrentAnimationState(){
        return currentAnimationState;
    }


    public Body getB2body() {
        return b2body;
    }

    public Animation<TextureRegion> getWalkRightAnimation() {
        return walkRightAnimation;
    }

    public Animation<TextureRegion> getWalkLeftAnimation() {
        return walkLeftAnimation;
    }

    public Animation<TextureRegion> getIdleAnimation() {
        return idleAnimation;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public ArrayList<Pair<Item,Integer>> getInventory() {
        return inventory;
    }

    public int getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
    }

    public Vector2 getPosition(){
        return b2body.getPosition();
    }

    public void setCurrent_mode(GameMode current_mode) {
        this.current_mode = current_mode;
    }

    public Pair<Item, Integer> currentItemPair(){
        return inventory.get(currentItem);
    }

    public float getLife() {
        return life;
    }

    public void setLife(float life) {
        this.life = life;
    }

    public void setInventory(ArrayList<Pair<Item, Integer>> inventory) {
        this.inventory = inventory;
    }
}
