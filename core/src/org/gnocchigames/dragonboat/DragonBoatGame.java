package org.gnocchigames.dragonboat;

import java.util.ArrayList;
import java.util.List; 


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class DragonBoatGame extends Game {


	@Override
	public void create () {
		
		this.setScreen(new RaceLegScreen(this));

	}


	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {
	}
}
