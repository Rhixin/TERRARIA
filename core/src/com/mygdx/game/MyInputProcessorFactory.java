package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Sprites.Player;

public class MyInputProcessorFactory {

    public InputProcessor processInput (MyWorld world, String listenerType){
        switch (listenerType) {
            case "A":
                return new MyInputListenerA(world);
            case "B":
                return new MyInputListenerB(world.getPlayer());
            default:
                return new MyInputListenerA(world);
        }

    }

    public class MyInputListenerA implements InputProcessor {
        private boolean isLeftHold = false;
        private Vector2 current_tile = new Vector2(0,0);
        private MyWorld world;

        public MyInputListenerA(MyWorld world) {
            this.world = world;
        }
        public void updateListenerA(){
            if(isLeftHold){
                world.getPlayer().deleteBlock((int) current_tile.x,(int) current_tile.y);
            } else {
                world.getPlayer().resetBlock((int)current_tile.x, (int)current_tile.y);
            }
        }

        @Override
        public boolean keyDown(int i) {
            return false;
        }

        @Override
        public boolean keyUp(int i) {
            return false;
        }

        @Override
        public boolean keyTyped(char c) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            Vector3 worldCoordinates = world.converToWorldCoordinates(screenX,screenY);
            int tileX = (int) (worldCoordinates.x / 32);
            int tileY = (int) (worldCoordinates.y / 32);

            if(button == Input.Buttons.LEFT){
                isLeftHold = true;
                current_tile.set(tileX,tileY);
                world.getPlayer().deleteBlock(tileX, tileY);
            } else {
                world.getPlayer().placeBlock(tileX,tileY);
            }

            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            if (button == Input.Buttons.LEFT) {
                isLeftHold = false;
            }
            return true;
        }

        @Override
        public boolean touchCancelled(int i, int i1, int i2, int i3) {
            return false;
        }

        @Override
        public boolean touchDragged(int i, int i1, int i2) {
            return false;
        }

        @Override
        public boolean mouseMoved(int i, int i1) {
            return false;
        }

        @Override
        public boolean scrolled(float v, float v1) {
            return false;
        }
    }

    public class MyInputListenerB implements InputProcessor {
        Player player;

        public MyInputListenerB(Player player) {
            this.player = player;
        }

        @Override
        public boolean keyDown(int i) {
            return false;
        }

        @Override
        public boolean keyUp(int i) {
            return false;
        }

        @Override
        public boolean keyTyped(char c) {
            return false;
        }

        @Override
        public boolean touchDown(int i, int i1, int i2, int i3) {
            return false;
        }

        @Override
        public boolean touchUp(int i, int i1, int i2, int i3) {
            return false;
        }

        @Override
        public boolean touchCancelled(int i, int i1, int i2, int i3) {
            return false;
        }

        @Override
        public boolean touchDragged(int i, int i1, int i2) {
            return false;
        }

        @Override
        public boolean mouseMoved(int i, int i1) {
            return false;
        }

        @Override
        public boolean scrolled(float amountX, float amountY) {
            if (amountY < 0) {
                player.setCurrentItem( (player.getCurrentItem() + 1) % 8);
            } else {

                if(player.getCurrentItem() - 1 < 0){
                    player.setCurrentItem(7);
                } else {
                    player.setCurrentItem(player.getCurrentItem() - 1);
                }
            }

            player.syncHudInventory();
            return true;
        }
    }




}
