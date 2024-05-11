package com.mygdx.game.Inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Helper.BoxActor;
import com.mygdx.game.Items.Item;

public class ItemBox extends BoxActor implements Draggable {
    private static final int MAX_NUMBER = 64;
    public static final Texture defaultTexture = new Texture("RAW/empty.png");
    private Item item;
    public Label countLabel;
    private int count = 0;

    public ItemBox(Item item) {
        super(item.getTexture());
        this.item = item;

        initLabel();
        addDragListener();
    }

    public ItemBox(){
        super(defaultTexture);
        initLabel();
        addDragListener();
    }

    public void initLabel(){
        BitmapFont font = new BitmapFont();
        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
        countLabel = new Label(String.format("%02d", this.count), style);
        countLabel.setSize(5,5);
        countLabel.setPosition(getX()  - 5, getY() - 5);
        addActor(countLabel);
    }

    public void render(){
        countLabel.setText(Integer.toString(count));

        if(item == null || item.getTexture() == null){
            setTexture(defaultTexture);
        } else {
            setTexture(item.getTexture());
        }

    }

    public void setItem(Item item, int count) {
        this.item = item;
        this.count = count;
    }


    Item getItem() {
        return item;
    }

    int getCount(){
        return count;
    }



    @Override
    public void addDragListener() {
        this.addListener(new DragListener() {
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                //System.out.println("Pos X: " + countLabel.getX() + " Pos Y: " + getY());
                moveBy(x - getWidth() / 2, y - getHeight() / 2);
                countLabel.setPosition(getX()  - 5, getY() - 5);
            }
        });
    }



}
