package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.Screens.EntityScreen;

public class Terraria extends Game {
	public  static final int V_WIDTH = 800;
	public  static final int V_HEIGHT = 400;

	public static final int ZOOM_FACTOR = 1;
	public static final float PPM = 1;

	@Override
	public void create () {
		setScreen(new EntityScreen());
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
