package com.mygdx.game.Screens;

import com.badlogic.gdx.*;
import com.mygdx.game.MyWorld;

public class EntityScreen implements Screen {

    private final MyWorld world;

    public EntityScreen(){
        world = new MyWorld();
    }

    @Override
    public void show() {
    }



    @Override
    public void render(float delta) {
        world.update(delta);
        world.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        world.updateGameport(width, height);
        world.getHudStage().getViewport().update(width, height, true);
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
        world.dispose();
    }
}
