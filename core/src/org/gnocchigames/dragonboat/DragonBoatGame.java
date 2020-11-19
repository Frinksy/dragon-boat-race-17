package org.gnocchigames.dragonboat;

import com.badlogic.gdx.Game;

import org.gnocchigames.dragonboat.screens.BoatSelectScreen;
import org.gnocchigames.dragonboat.screens.ControlsScreen;
import org.gnocchigames.dragonboat.screens.MainMenuScreen;
import org.gnocchigames.dragonboat.screens.PodiumScreen;
import org.gnocchigames.dragonboat.screens.RaceLegScreen;
import org.gnocchigames.dragonboat.screens.SettingsScreen;
import org.gnocchigames.dragonboat.util.GameSettings;

/**
 * Main Game class,
 * handles different game screens (MainMenuScreen, RaceLegScreen, etc.)
 */
public class DragonBoatGame extends Game {

	//declares all screens
	private MainMenuScreen main_menu_screen;
	private ControlsScreen controls_screen;
	private BoatSelectScreen boat_select_screen;
	private SettingsScreen settings_screen;
	private RaceLegScreen race_leg_screen;
	private PodiumScreen podium_Screen;
	private GameSettings game_settings;
	
	// declares variables holding integers to be used in case statement
	public final static int MENU = 0;
	public final static int CONTROLS = 1;
	public final static int BOAT_SELECT = 2;
	public final static int SETTINGS = 3;
	public final static int RACE_LEG = 4;
	public final static int PODIUM = 5;

	/**
	 * Called at the start to initialise the game
	 */
	@Override
	public void create () {
		/**initializes the loading screen,
		 * goes straight to main menu for now
		 * same as commented code beneath
		 */
		
		main_menu_screen = new MainMenuScreen(this);
		setScreen(main_menu_screen);

		// Set the screen to the actual game
		//this.setScreen(new RaceLegScreen(this));


		game_settings = new GameSettings();
	}

	/**
	 * case statement for choosing a screen
	 * called by changeScreen(DragonBoatGame.SCREEN)
	 */
	public void changeScreen(int screen){
		switch(screen){
			case MENU:
				if(main_menu_screen == null) main_menu_screen = new MainMenuScreen(this);
					this.setScreen(main_menu_screen);

					break;
			case CONTROLS:
				if(controls_screen == null) controls_screen = new ControlsScreen(this);
					this.setScreen(controls_screen);
					break;
			case BOAT_SELECT:
				if(boat_select_screen == null) boat_select_screen = new BoatSelectScreen(this);
					this.setScreen(boat_select_screen);
					break;
			case SETTINGS:
				if(settings_screen == null) settings_screen = new SettingsScreen(this);
					this.setScreen(settings_screen);
					break;
			case RACE_LEG:
				if(race_leg_screen == null) race_leg_screen = new RaceLegScreen(this);
					this.setScreen(race_leg_screen);
					break;
			case PODIUM:
				if(podium_Screen == null) podium_Screen = new PodiumScreen(this);
					this.setScreen(podium_Screen);
					break; 
		}
	}
	
	//allows screens to access the GameSettings class
	public GameSettings getSettings(){
		return this.game_settings;
	}

	/**
	 * Called at the end, to free up memory
	*/
	@Override
	public void dispose () {
		
	}
}