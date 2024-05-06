package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Helper.BoxActor;
import com.mygdx.game.Inventory.Draggable;
import com.mygdx.game.Inventory.InventoryBox;
import com.mygdx.game.Terraria;

import java.util.ArrayList;

public class Hud {
    public static Stage stage;
    private Viewport viewport;
    public ArrayList<InventoryBox> inventoryBoxDisplay = new ArrayList<>(8);

    public Hud(SpriteBatch batch, Viewport viewport){
        this.viewport = viewport;

        stage = new Stage(viewport, batch);

        int prev = 32;
        for(int i = 1; i <= 8; i++){
            InventoryBox box = new InventoryBox(new Texture(Gdx.files.internal("item_box.png")));

            box.setPosition(prev, Terraria.V_HEIGHT - 85);
            box.item1.setPosition(prev, Terraria.V_HEIGHT - 85);
            prev += 88;


            box.item1.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float screenX, float screenY, int pointer, int button) {

                    if (button == Input.Buttons.LEFT) {
                        System.out.println("Start");
                        ((Draggable) event.getTarget()).onDragStart(screenX, screenY);
                        return true;
                    }
                    return false;
                }
                @Override
                public void touchDragged(InputEvent event, float screenX, float screenY, int pointer) {
                    System.out.println("X: " + screenX + " Y: " + screenY);

                    ((Draggable) event.getTarget()).onDrag(screenX, screenY);
                }
                @Override
                public void touchUp(InputEvent event, float screenX, float screenY, int pointer, int button) {

                    if (button == Input.Buttons.LEFT) {
                        ((Draggable) event.getTarget()).onDragEnd(screenX, screenY);
                    }
                }
            });


            inventoryBoxDisplay.add(box);

            stage.addActor(box);
            stage.addActor(box.item1);
        }

        Gdx.input.setInputProcessor(stage);
    }
}
