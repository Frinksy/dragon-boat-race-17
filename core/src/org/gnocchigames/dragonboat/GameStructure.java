package org.gnocchigames.dragonboat;

import java.util.List;

public class GameStructure {

    private static enum LEGS {
        LEG_ONE, LEG_TWO, LEG_THREE, LEG_FINAL
    }


    private DragonBoatGame game;

    private RaceLegScreen race_screen; // The screen which will be used to draw and update the legs

    private List<Boat> players; // The list of players in the game
    private List<Obstacle> obstacles; // The list of obstacles in the game

    private static int FINISH_HEIGHT = 1080 * 5;


    /**
     * Start the current set leg
     */
    public void start_leg() {

        // Reset race_screen
        race_screen.dispose();
        race_screen = new RaceLegScreen(game);

        // Add the players to the race
        for (Boat player : players) {
            race_screen.addEntity(player);
        }

        // Add the obstacles to the course
        for (Obstacle obstacle : obstacles) {
            race_screen.addEntity(obstacle);
        }


    }

    /**
     * Set all the elements for the specified leg
     * @param leg the leg to set
     */
    public void set_leg (LEGS leg) {
        switch (leg) {
            case LEG_ONE:
                
                break;
        
            case LEG_TWO:

                break;
            
            case LEG_THREE:

                break;

            case LEG_FINAL:

                break;
        }
    }

    
}
