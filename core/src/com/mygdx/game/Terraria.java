package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.EntityScreen;

public class Terraria extends Game {
	public  static final int V_WIDTH = 700;
	public  static final int V_HEIGHT = 500;
	//ppm short for pixels per meter
	public static final float PPM = 20;
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
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
