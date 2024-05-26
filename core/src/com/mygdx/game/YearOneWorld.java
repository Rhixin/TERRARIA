package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Helper.AnimationState;
import com.mygdx.game.Helper.Pair;
import com.mygdx.game.Helper.WorldCreator;
import com.mygdx.game.Items.Item;
import com.mygdx.game.Items.Weapon;
import com.mygdx.game.Screens.Hud;
import com.mygdx.game.Block.Block;
import com.mygdx.game.Screens.MerchantBoard;
import com.mygdx.game.Sprites.*;
import com.mygdx.game.Sprites.Bullets.Missile;
import com.mygdx.game.Sprites.WorldWeapons.Paladin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;


public class YearOneWorld extends GameWorld{
    private static OrthographicCamera gamecam;
    private Viewport gamePort;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    private Player player;
    private YearOneBoss boss;
    private Hud hud;
    private ArrayList<SpriteBatch> spriteBatches;
    public static HashSet<Projectile> bodiesToremove;
    private MyInputProcessorFactory.MyInputListenerB playerListenerScroll;
    private MiningWorld past_world;


    public YearOneWorld(MiningWorld mineworld) {
        this.past_world = mineworld;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Terraria.V_WIDTH + 200, Terraria.V_HEIGHT + 200,gamecam);
        map = new TmxMapLoader().load("MAPS/yearone.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, Terraria.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight()/2,0);
        world = new World(new Vector2(0,-140f), true);
        spriteBatches = new ArrayList<>();
        bodiesToremove = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            SpriteBatch spriteBatch = new SpriteBatch();
            spriteBatches.add(spriteBatch);
        }

        b2dr = new Box2DDebugRenderer();

        WorldCreator.syncWorldAndTiledMapYearOne(world, map);

        ArrayList<Pair<Item, Integer>> temp = new ArrayList<>(8);
        for(int i = 0; i < 8; i++){
            temp.add(new Pair<>(null,0));
        }

        hud = new Hud(spriteBatches.get(3),temp);


        player = new Player(world, hud);
        player.getB2body().setTransform(new Vector2(320,320), 0);
        player.setCurrent_mode(GameMode.COMBAT_MODE);

        boss = new YearOneBoss(world, 520, 500);


        MyInputProcessorFactory inputFactory = new MyInputProcessorFactory();
        playerListenerScroll = (MyInputProcessorFactory.MyInputListenerB) inputFactory.processInput(this, "B", player);

        WorldContactListener contactListener = new WorldContactListener(this);
        world.setContactListener(contactListener);

    }

    public void update(float dt){

        handleInput(dt);
        player.update(dt);
        boss.update(dt, player.getPosition().x, player.getPosition().y);

        if(player.getLife() <= 0){
            Terraria.gameMode = GameMode.MINING_MODE;
        }

        if(!world.isLocked()){
            for(Projectile b : bodiesToremove){
                if(b != null){
                    world.destroyBody(b.getBody());
                    b.setAlpha(0);
                    bodiesToremove.remove(b);
                    break;
                }
            }
        }

        //todo fix gravity
        //------------------------------------------------------------------------------
        if(player.getB2body().getLinearVelocity().y < 0){
            player.getB2body().applyForceToCenter(new Vector2(0, -600 ), true);
        }
        //------------------------------------------------------------------------------

        world.step(1/60f,6, 2);

        hud.healthBarPlayer.update(player.getLife());
    }

    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        GameCamUpdate();
        renderer.setView(gamecam);
        renderer.setView(gamecam);
        renderer.render();

        player.render(delta);
        boss.render(delta);

        for(int i = 0; i < 10; i++){

            SpriteBatch sb = spriteBatches.get(i);
            if(i == 1){
                sb.setProjectionMatrix(gamecam.combined);
                sb.begin();
                player.draw(sb);
                boss.draw(sb);

                sb.end();
            } else if (i == 2) {
                sb.setProjectionMatrix(gamecam.combined);
                sb.begin();


                sb.end();
            } else if (i == 3) {
                sb.setProjectionMatrix(gamecam.combined);
                sb.begin();
                sb.end();
            } else if (i == 4){
                sb.begin();
                hud.render(delta);
                sb.end();
            }
        }

        b2dr.render(world,gamecam.combined);



    }

    private void GameCamUpdate(){
        gamecam.position.x = player.getB2body().getPosition().x;
        gamecam.position.y = player.getB2body().getPosition().y + 260;
        gamecam.update();

    }

    public void handleInput(float dt){

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


        if(Gdx.input.isKeyJustPressed(Input.Keys.M) ){
            int random = new Random().nextInt(701) + 50;
            new Missile(world, random,620);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            boss.getBody().applyLinearImpulse(new Vector2(80f, 0), player.getB2body().getWorldCenter(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            boss.getBody().applyLinearImpulse(new Vector2(-80f, 0), player.getB2body().getWorldCenter(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            Missile m = boss.attack(dt);
        }

        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            player.attack(dt);
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

    public Player getPLayer() {
        return player;
    }

    public MyInputProcessorFactory.MyInputListenerB getPlayerListenerScroll() {
        return playerListenerScroll;
    }

    public Player getPlayer() {
        return player;
    }
}
