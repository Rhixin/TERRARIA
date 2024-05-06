package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.EntityScreen;
import com.mygdx.game.Sprites.Block.Block;

import java.util.ArrayList;

public class Terraria extends Game {
	public  static final int V_WIDTH = 800;
	public  static final int V_HEIGHT = 400;

	public static final int ZOOM_FACTOR = 1;
	//ppm short for pixels per meter
	public static final float PPM = 1;

	public SpriteBatch batch;
	public SpriteBatch batch2;

	public SpriteBatch batch3;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		batch2 = new SpriteBatch();
		batch3 = new SpriteBatch();
		setScreen(new EntityScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
