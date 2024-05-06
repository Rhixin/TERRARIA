package com.mygdx.game.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Helper.AnimationState;
import com.mygdx.game.Inventory.Draggable;
import com.mygdx.game.Inventory.InventoryBox;
import com.mygdx.game.Inventory.Item;
import com.mygdx.game.Sprites.Block.Block;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Terraria;
import com.mygdx.game.Helper.WorldCreator;

public class EntityScreen implements Screen {
    private final Terraria game;

    Texture texture;
    private final OrthographicCamera gamecam;
    private final Viewport gamePort;


    //TILED MAP VARIABLES
    private TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;

    //BOX2D VARIABLES
    private World world;
    private Box2DDebugRenderer b2dr;

    //Player
    private Player player;

    //hud
    private Hud hud;

    //break block temporrary rani
    public boolean isLeftHold = false;
    public int currentX = 0, currentY = 0;

    public EntityScreen(Terraria game){

        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Terraria.V_WIDTH / Terraria.PPM, Terraria.V_HEIGHT / Terraria.PPM,gamecam);

        //setting up the map
        //TILED MAP VARIABLES
        TmxMapLoader maploader = new TmxMapLoader();
        map = maploader.load("MAPS/map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, Terraria.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight()/2,0);

        //y is gravity not sure pa hehe
        world = new World(new Vector2(0,-140f), true);

        b2dr = new Box2DDebugRenderer();

        //create the world using sa tiledmap nga na create nga very long process grrr create objects diri
        new WorldCreator(world, map);

        //Player put in world
        player = new Player(world, this);

        hud = new Hud(game.batch3);

        InputProcessor inputProcessor = new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                // Convert screen coordinates to map coordinates
                Vector3 worldCoordinates = new Vector3(screenX, screenY, 0);
                gamecam.unproject(worldCoordinates);

                worldCoordinates.x /= Terraria.PPM;
                worldCoordinates.y /= Terraria.PPM;

                // Convert map coordinates to tile coordinates
                int tileX = (int) (worldCoordinates.x / 32);
                int tileY = (int) (worldCoordinates.y / 32);


                if(button == Input.Buttons.LEFT){
                    isLeftHold = true;
                    //player.deleteBlock(tileX, tileY);
                    currentX = tileX;
                    currentY = tileY;

                    //System.out.println(WorldCreator.blocksMap);
                } else {
                    player.placeBlock(tileX,tileY);
                }

                return true;
            }
        };

        InputProcessor ip2 = Hud.stage;
        Gdx.input.setInputProcessor(new InputMultiplexer(ip2, inputProcessor));
    }

    public void handleInput(float dt){

        if(isLeftHold){
            player.deleteBlock(currentX, currentY);
        } else {
            player.resetBlock(currentX, currentY);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
            //impulse is immediate change
            // with x for x direction and y for y direction
            //where in the body we want to apply the force. If side kay mo spin off ang body
            //true para ma wake up ang body

            //player.b2body.applyForce(new Vector2(0,1500), player.b2body.getWorldCenter(), true);
            player.b2body.applyLinearImpulse(new Vector2(0, 900f), player.b2body.getWorldCenter(), true);
            player.b2body.applyLinearImpulse(new Vector2(0, 900f), player.b2body.getWorldCenter(), true);
        }

        // dapat <= 150 para naay max speed imong player
        //naay && 5 para di siya mo accelerate
        if(Gdx.input.isKeyPressed(Input.Keys.D) && player.b2body.getLinearVelocity().x <= 50){
            player.b2body.applyLinearImpulse(new Vector2(80f, 0), player.b2body.getWorldCenter(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A) && player.b2body.getLinearVelocity().x >= -50){
            player.b2body.applyLinearImpulse(new Vector2(-80f, 0), player.b2body.getWorldCenter(), true);
        }


        if(Gdx.input.isKeyPressed(Input.Keys.Z) ){
            System.out.println("Player body pos in world. X,Y = " + player.b2body.getPosition());
        }





    }


    public void update(float dt){

        player.update(dt);

        player.stateTime += dt;
        TextureRegion currentFrame;
        if(player.getCurrentAnimationState() == AnimationState.IDLE){
            currentFrame = player.idleAnimation.getKeyFrame(player.stateTime, true);
        } else if (player.getCurrentAnimationState() == AnimationState.WALK_RIGHT){
            currentFrame = player.walkRightAnimation.getKeyFrame(player.stateTime, true);
        } else {
            currentFrame = player.walkLeftAnimation.getKeyFrame(player.stateTime, true);
        }
        player.setRegion(currentFrame);


        if(player.b2body.getLinearVelocity().y < 0){
            player.b2body.applyForceToCenter(new Vector2(0, -600 ), true);
        }

        if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            isLeftHold = false;
        }
        //handle user inputs ni
        handleInput(dt);

        world.step(1/60f,6, 2);


        //update the gamecam position
        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.position.y = player.b2body.getPosition().y;
        gamecam.update();

        //ingnan si renderer nga idraw ra ang makita sa gamecam sa world
        renderer.setView(gamecam);

        //hud.updateActors(gamecam);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Update the game logic
        update(delta);

        // Clear the screen with a black color
        Gdx.gl.glClearColor(89, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render the map using the TiledMapRenderer
        renderer.setView(gamecam);
        renderer.render();

        // Set the camera's projection matrix
        game.batch.setProjectionMatrix(gamecam.combined);

        // Begin batch rendering for sprites
        game.batch.begin();



        // Render the player
        player.draw(game.batch);

        // End sprite batch rendering
        game.batch.end();

        // Set the projection matrix for the second batch (batch2)
        game.batch2.setProjectionMatrix(gamecam.combined);

        // Begin batch rendering for another batch (batch2)
        game.batch2.begin();

        // Render each block in the WorldCreator's list of blocks
        for(Block block : WorldCreator.blocksMap.values()){
            if(block != null){
                block.render(game.batch2);
            }

        }

        // End batch2 rendering
        game.batch2.end();

        // Set the projection matrix for the third batch (batch3)
        game.batch3.setProjectionMatrix(gamecam.combined);

        // Begin batch rendering for the third batch (batch3)
        game.batch3.begin();

        // End batch3 rendering
        game.batch3.end();

        // Draw the HUD (heads-up display) stage
        Hud.stage.draw();


        //b2dr.render(world,gamecam.combined);
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
