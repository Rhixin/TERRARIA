package com.mygdx.game.Helper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BoxActor extends Actor {

    private Texture backgroundTexture;

    public BoxActor(Texture backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
        setWidth(backgroundTexture.getWidth());
        setHeight(backgroundTexture.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(backgroundTexture, getX(), getY(), getWidth(), getHeight());
    }
}
