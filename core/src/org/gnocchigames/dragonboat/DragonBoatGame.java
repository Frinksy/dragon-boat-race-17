package org.gnocchigames.dragonboat;

import java.util.ArrayList;
import java.util.List; 


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class DragonBoatGame extends Game {


	@Override
	public void create () {
		
		// Set the screen we want
		this.setScreen(new RaceLegScreen(this));
		
		// Uncomment the following line (comment the above line) to show the menu instead
		this.setScreen(new MainMenuScreen(this));
	}


	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {
	}
}
