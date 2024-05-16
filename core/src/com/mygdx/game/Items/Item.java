package com.mygdx.game.Items;

import com.badlogic.gdx.graphics.Texture;


public abstract class Item {
    private String name;
    private String description;


    Item(String name, String description){
        this.name = name;
        this.description = description;
    }

    public abstract String getType();

    public abstract Texture getTexture();

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

}
