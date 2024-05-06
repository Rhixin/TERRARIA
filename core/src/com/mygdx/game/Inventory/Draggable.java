package com.mygdx.game.Inventory;

public interface Draggable {
    void onDragStart(float screenX, float screenY);
    void onDrag(float screenX, float screenY);
    void onDragEnd(float screenX, float screenY);
}
