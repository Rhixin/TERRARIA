package com.mygdx.game.Helper;

import com.badlogic.gdx.graphics.Texture;

public class Heart extends BoxActor{

    public static final Texture texture = new Texture("RAW/heart.png");

    public Heart(float x, float y) {
        super(texture);
        setPosition(x, y);
    }

    public void render(){
        setTexture(texture);
    }


}
