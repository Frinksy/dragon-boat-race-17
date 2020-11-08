package org.gnocchigames.dragonboat;

import com.badlogic.gdx.Game;



/**
 * Main Game class,
 * handles different game screens (MainMenuScreen, RaceLegScreen, etc.)
 */
public class DragonBoatGame extends Game {

	/**
	 * Called at the start to initialise the game
	 */
	@Override
	public void create () {
		
		// Set the screen we want
		this.setScreen(new RaceLegScreen(this));
		
		// Uncomment the following line (comment the above line) to show the menu instead
		//this.setScreen(new MainMenuScreen(this));
	}


	/**
	 * Called every frame to render the scene
	 */
	@Override
	public void render () {
		super.render();

	}
	
	/**
	 * Called at the end, to free up memory
	*/
	@Override
	public void dispose () {
	}
}