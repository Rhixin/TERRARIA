package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Helper.Pair;
import com.mygdx.game.Helper.SoundManager;
import com.mygdx.game.Helper.WorldCreator;
import com.mygdx.game.Items.Item;
import com.mygdx.game.Items.Weapon;
import com.mygdx.game.Screens.BlackSmithBoard;
import com.mygdx.game.Screens.Hud;
import com.mygdx.game.Block.Block;
import com.mygdx.game.Screens.MerchantBoard;
import com.mygdx.game.Sprites.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MiningWorld extends GameWorld{
    private static OrthographicCamera gamecam;
    private Viewport gamePort;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    public static HashMap<Vector2, Block> tilesMap = new HashMap<>();
    public static ArrayList<Drop> drops = new ArrayList<>();

    private World world;
    private Box2DDebugRenderer b2dr;
    private Player player;
    private Merchant merchant;
    private Blacksmith blacksmith;
    private Hud hud;
    private ArrayList<SpriteBatch> spriteBatches;
    public static HashSet<Body> bodiesToremove;

    private MyInputProcessorFactory.MyInputListenerA playerListenerMine;
    private MyInputProcessorFactory.MyInputListenerB playerListenerScroll;

    public MiningWorld() {
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Terraria.V_WIDTH, Terraria.V_HEIGHT,gamecam);
        map = new TmxMapLoader().load("MAPS/map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, Terraria.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight()/2,0);
        world = new World(new Vector2(0,-140f), true);
        spriteBatches = new ArrayList<>();
        bodiesToremove = new HashSet<>();

        //SoundManager.playBamyamgengMusic();
        SoundManager.playBackgroundMusic();


        for (int i = 0; i < 10; i++) {
            SpriteBatch spriteBatch = new SpriteBatch();
            spriteBatches.add(spriteBatch);
        }

        b2dr = new Box2DDebugRenderer();
        tilesMap = WorldCreator.syncWorldAndTiledMap(world, map);

        ArrayList<Pair<Item, Integer>> temp = new ArrayList<>(8);
        for(int i = 0; i < 8; i++){
            temp.add(new Pair<>(null,0));
        }
        hud = new Hud(spriteBatches.get(3),temp);

        player = new Player(world, hud);


        blacksmith = new Blacksmith(world, spriteBatches.get(3), player);

        merchant = new Merchant(world, spriteBatches.get(3), player);

        MyInputProcessorFactory inputFactory = new MyInputProcessorFactory();

        playerListenerMine = (MyInputProcessorFactory.MyInputListenerA) inputFactory.processInput(this, "A", player);
        playerListenerScroll = (MyInputProcessorFactory.MyInputListenerB) inputFactory.processInput(this, "B", player);

        WorldContactListener contactListener = new WorldContactListener(this);
        world.setContactListener(contactListener);
    }

    public void update(float dt){

        handleInput(dt);
        player.update(dt);

        blacksmith.update(dt);
        merchant.update(dt);


        Blacksmith.blackSmithBoard.update(player.getPosition(), blacksmith.getPosition());
        Merchant.merchantboard.update(player.getPosition(), merchant.getPosition());

        if(!world.isLocked()){
            for(Body b : bodiesToremove){
                world.destroyBody(b);
            }

            bodiesToremove.clear();
        }

        //todo fix gravity
        //------------------------------------------------------------------------------
        if(player.getB2body().getLinearVelocity().y < 0){
            player.getB2body().applyForceToCenter(new Vector2(0, -600 ), true);
        }
        //------------------------------------------------------------------------------



        world.step(1/60f,6, 2);
    }

    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        GameCamUpdate();
        renderer.setView(gamecam);
        renderer.setView(gamecam);
        renderer.render();

        player.render(delta);
        blacksmith.render(delta);
        merchant.render(delta);


        for(int i = 0; i < 10; i++){

            SpriteBatch sb = spriteBatches.get(i);
            if(i == 1){
                sb.setProjectionMatrix(gamecam.combined);
                sb.begin();
                player.draw(sb);
                blacksmith.draw(sb);
                merchant.draw(sb);
                sb.end();
            } else if (i == 2) {
                sb.setProjectionMatrix(gamecam.combined);
                sb.begin();

                for(Block block : tilesMap.values()){
                    if(block != null){
                        block.render(sb);
                    }

                }

                sb.end();
            } else if (i == 3) {



                sb.setProjectionMatrix(gamecam.combined);
                sb.begin();

                for (Drop drop : drops){
                    drop.render(delta);
                    drop.draw(sb);
                }


                sb.end();
            } else if (i == 4){
                sb.begin();

                Blacksmith.blackSmithBoard.render(delta);
                Merchant.merchantboard.render(delta);
                hud.render(delta);
                sb.end();
            }
        }

        //b2dr.render(world,gamecam.combined);
        //for debugging box2d ni
    }

    private void GameCamUpdate(){
        if(player.getB2body().getPosition().x >= 400 && player.getB2body().getPosition().x <= 4045){
            gamecam.position.x = player.getB2body().getPosition().x;
        }

        gamecam.position.y = player.getB2body().getPosition().y;
        gamecam.update();
    }

    public void handleInput(float dt){
        playerListenerMine.updateListenerA();

        if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
            player.getB2body().applyLinearImpulse(new Vector2(0, 900f), player.getB2body().getWorldCenter(), true);
            player.getB2body().applyLinearImpulse(new Vector2(0, 900f), player.getB2body().getWorldCenter(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D) && player.getB2body().getLinearVelocity().x <= 50){
            player.getB2body().applyLinearImpulse(new Vector2(80f, 0), player.getB2body().getWorldCenter(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A) && player.getB2body().getLinearVelocity().x >= -50){
            player.getB2body().applyLinearImpulse(new Vector2(-80f, 0), player.getB2body().getWorldCenter(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.Z) ){
            System.out.println("Player body pos in world. X,Y = " + player.getB2body().getPosition());
        }


    }

    public Vector2 getPlayerWorldCoordinates(){
       return player.getB2body().getPosition();
    }

    public Vector3 converToWorldCoordinates(int screenX, int screenY){
        Vector3 worldcoordinates = new Vector3(screenX,screenY,0);
        gamecam.unproject(worldcoordinates);

        return worldcoordinates;
    }

    public Player getPlayer() {
        return player;
    }

    public void dispose(){
        map.dispose();
        renderer.dispose();
        b2dr.dispose();
        world.dispose();
    }

    public void updateGameport(int width, int height){
        gamePort.update(width,height);
    }


    public Stage getHudStage(){
        return hud.getStage();
    }

    public MerchantBoard getMerchantboard() {
        return merchant.merchantboard;
    }

    public BlackSmithBoard getBlacksmithBoard(){
        return blacksmith.blackSmithBoard;
    }

    public MyInputProcessorFactory.MyInputListenerA getPlayerListenerMine() {
        return playerListenerMine;
    }

    public MyInputProcessorFactory.MyInputListenerB getPlayerListenerScroll() {
        return playerListenerScroll;
    }

    public void syncPlayerToPlayerInventory(Player player2, World world){

        ArrayList<Pair<Item, Integer>> new_inventory = new ArrayList<>();

        for(Pair<Item,Integer> p : player.getInventory()){
            new_inventory.add(p);
        }

        player2.setInventory(new_inventory);
    }
}

