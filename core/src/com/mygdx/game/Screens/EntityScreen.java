package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Terraria;
import com.mygdx.game.Helper.WorldCreator;

public class EntityScreen implements Screen {
    private final Terraria game;
    private TextureAtlas atlas;
    Texture texture;
    private final OrthographicCamera gamecam;
    private final Viewport gamePort;

    //TILED MAP VARIABLES
    private TmxMapLoader maploader;

    private TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;

    //BOX2D VARIABLES
    private World world;
    private Box2DDebugRenderer b2dr;




    //Player
    private Player player;


    public EntityScreen(Terraria game){
        atlas = new TextureAtlas("steve.txt");
        this.game = game;
        texture = new Texture("badlogic.jpg");
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Terraria.V_WIDTH / Terraria.PPM, Terraria.V_HEIGHT / Terraria.PPM,gamecam);


        //setting up the map
        maploader = new TmxMapLoader();
        map = maploader.load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Terraria.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight()/2 + 100,0);


        //y is gravity not sure pa hehe
        world = new World(new Vector2(0,-50f), true);
        b2dr = new Box2DDebugRenderer();

        //create the world using sa tiledmap nga na create nga very long process grrr
        new WorldCreator(world, map);

        //Player put in world
        player = new Player(world, this);

        Gdx.input.setInputProcessor(new MyInputListener());
    }

    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.W) && player.b2body.getLinearVelocity().y <= 5){
            //impulse is immediate change
            // with x for x direction and y for y direction
            //where in the body we want to apply the force. If side kay mo spin off ang body
            //true para ma wake up ang body
            player.b2body.applyLinearImpulse(new Vector2(0, 24f), player.b2body.getWorldCenter(), true);
        }

        // dapat <= 150 para naay max speed imong player
        //naay && 5 para di siya mo accelerate
        if(Gdx.input.isKeyPressed(Input.Keys.D) && player.b2body.getLinearVelocity().x <= 5){
            player.b2body.applyLinearImpulse(new Vector2(8f, 0), player.b2body.getWorldCenter(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A) && player.b2body.getLinearVelocity().x >= -5){
            player.b2body.applyLinearImpulse(new Vector2(-8f, 0), player.b2body.getWorldCenter(), true);
        }


        if(Gdx.input.isKeyPressed(Input.Keys.Z) ){
            System.out.println("Player body pos in world. X,Y = " + player.b2body.getPosition());
        }





    }

    private static class MyInputListener extends InputAdapter {


        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            // Convert screen coordinates to world coordinates
            float x = screenX;
            float y = Gdx.graphics.getHeight() - screenY; // Flip y-coordinate

            // Now x and y contain the coordinates where the cursor was clicked
            System.out.println("Clicked at: x=" + x + ", y=" + y);

            return true; // Return true to indicate that the input was handled
        }

    }

    public void update(float dt){
        //handle user inputs ni
        handleInput(dt);

        world.step(1/60f,6, 2);

        //player update
        player.update(dt);

        //update the gamecam position
        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.position.y = player.b2body.getPosition().y;
        gamecam.update();

        //ingnan si renderer nga idraw ra ang makita sa gamecam sa world
        renderer.setView(gamecam);
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        update(delta);

        //clear game screen sa
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render ang map.tmx
        renderer.render();

        //render ang box2debuggerlines for physics. Para makita ang boxes
        b2dr.render(world, gamecam.combined);

        //only set what the game can see
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        b2dr.dispose();
        world.dispose();

    }
}
