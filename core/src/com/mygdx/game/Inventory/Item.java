package com.mygdx.game.Inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Helper.BoxActor;
import com.mygdx.game.Screens.EntityScreen;

public class Item extends BoxActor implements Draggable {
    private float lastDragX, lastDragY;

    public Item(Texture backgroundTexture) {
        super(backgroundTexture);
    }

    @Override
    public void onDragStart(float screenX, float screenY) {

        lastDragX = getX();
        lastDragY = getY();

        //System.out.println("X: " + lastDragX + " Y: " + lastDragY);
    }

    @Override
    public void onDrag(float screenX, float screenY) {

        System.out.println("Mouse X: " + screenX + " and Mouse Y: " + screenY);
        float deltaX = screenX - lastDragX;
        float deltaY = screenY - lastDragY;
        moveBy(deltaX, deltaY);
        lastDragX = screenX;
        lastDragY = screenY;

    }

    @Override
    public void onDragEnd(float screenX, float screenY) {
        // Set the position to the last dragged position
        setPosition(lastDragX, lastDragY);
    }
}
