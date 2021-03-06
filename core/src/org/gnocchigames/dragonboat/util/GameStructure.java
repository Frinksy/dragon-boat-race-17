package org.gnocchigames.dragonboat.util;

import java.util.List;
import java.util.ArrayList;

import org.gnocchigames.dragonboat.DragonBoatGame;
import org.gnocchigames.dragonboat.entities.Boat;
import org.gnocchigames.dragonboat.entities.Duck;
import org.gnocchigames.dragonboat.entities.Obstacle;
import org.gnocchigames.dragonboat.entities.AIBoat;
import org.gnocchigames.dragonboat.screens.RaceLegScreen;
import org.gnocchigames.dragonboat.screens.BoatSelectScreen;
import org.gnocchigames.dragonboat.entities.PlayerBoat;
import org.gnocchigames.dragonboat.entities.Rock;
import org.gnocchigames.dragonboat.entities.TreeLog;
import org.gnocchigames.dragonboat.entities.Duck.DuckDirection;

/**
 * GameStructure sets up legs, boats and obstacles
 */
public class GameStructure {

    public static enum Legs {
        LEG_ONE, LEG_TWO, LEG_THREE, LEG_FINAL, LEG_PODIUM
    }
    
    
    private DragonBoatGame game;
    
    private RaceLegScreen race_screen; // The screen which will be used to draw and update the legs
    
    private List<Boat> players; // The list of players in the game
    private List<Obstacle> obstacles; // The list of obstacles in the game
    public Legs current_leg;

    private static int FINISH_HEIGHT = 20000;
    
    /**
     * Create a GameStructure instance
     * @param game the parent DragonBoatGame instance
     * @param parent the parent RaceLegScreen instance
     */
    public GameStructure(DragonBoatGame game, RaceLegScreen parent) {
        race_screen = parent;
        players = new ArrayList<Boat>(5);
        obstacles = new ArrayList<Obstacle>();
        this.game = game;
        current_leg = Legs.LEG_ONE;
    } 

    /**
     * Reset everything
     */
    public void resetAll() {
        players = new ArrayList<Boat>(5);
        obstacles = new ArrayList<Obstacle>();
        current_leg = Legs.LEG_ONE;
        // for (Obstacle obstacle : obstacles){
        //     race_screen.removeEntity(obstacle);
        // }
        for (Boat boat : players) {
            boat.stopTimer();
        }
        // for (Boat boat : players) {
        //     race_screen.removeEntity(boat);
        // }
        race_screen.resetEntities();
    }

    /**
     * Check if the boat is across the finish
     * @param boat the boat to check
     * @return true if the boat is over the finish, false otherwise
     */
    public boolean isBoatAcross(Boat boat){
        return boat.pos_y > FINISH_HEIGHT;
    }

    /**
     * Check if all the boats are across the finish
     * @return true if all boats are across the finish, false otherwise
     */
    public boolean allBoatsAcross(){
        for (Boat boat : players) {
            if (!isBoatAcross(boat)) {
                return false;
            }
        }

        return true;
    }
    
    /**
     * Check if the race is over and applies actions in that case
     * @param player_boat the player's boat
     * @return  true if the race is over, false otherwise
     */
    public boolean raceover(PlayerBoat player_boat){
        if (!player_boat.isAlive() && current_leg!=Legs.LEG_ONE) {
            game.changeScreen(DragonBoatGame.GAME_OVER);
            resetAll();
            return true;
        }
        else if (isBoatAcross(player_boat) || allBoatsAcross() || !player_boat.isAlive()){
            //System.out.println("test");
            // for (Obstacle obstacle : obstacles){
            //     race_screen.removeEntity(obstacle);
            // }
            for (Boat boat : players) {
                boat.stopTimer();
            }

            game.score_board.computeRoundEndScores();
            
            // for (Boat boat : players){
            //     race_screen.removeEntity(boat);
            // }
            if (current_leg != Legs.LEG_FINAL) {
                game.changeScreen(DragonBoatGame.NEXT);
                incrementCurrentLeg();
                game.score_board.eliminateBoats(current_leg);
            }else {
                game.changeScreen(DragonBoatGame.PODIUM);
                incrementCurrentLeg();
            }
            race_screen.resetEntities();
            // set_leg(current_leg);
            //resetAll();

            return true; 
        }else{
            return false;
        }
    }
   

    /**
     * Start the current set leg
     */
    public void start_leg() {

        //System.out.println(players.size());
        // Reset race_screen
        //race_screen.resetEntities();
        
        // Add the players to the race
        for (Boat player : players) {
            player.startTimer();
            race_screen.addEntity(player);
            race_screen.boats.add(player);
            //player.AIBoat(race_screen, players, 1, 1);
            
        }
        game.score_board.addPlayers(players);

        // Add the obstacles to the course
        for (Obstacle obstacle : obstacles) {
            race_screen.addEntity(obstacle);
        }


    }

    /**
     * Set all the elements for the specified leg
     * @param leg the leg to set
     */
    public void set_leg (Legs leg) {

        players = new ArrayList<Boat>();
        obstacles = new ArrayList<Obstacle>();

        switch (leg) {
            case LEG_ONE:
            for (int y = 1100; y < 20000; y+=1000) {
                for (int lane = 0; lane < 5; lane++) {
                    obstacles.add(new Duck(game, race_screen, lane*384+184, y, 20, DuckDirection.LEFT, lane));
                }
            }
            
            for(int y=0;y<20000;y+=1000){
                for(int x=0;x<=1980;x+=384){
                    obstacles.add(new Rock(game, race_screen,x+200,y+500));
                }
            }
            for(int y=0;y<20000;y+=1752){
                for(int x=0;x<=1980;x+=384){
                    obstacles.add(new TreeLog(game, race_screen,x+75,y+1752));
                }
            }
            for(int y=0;y<20000;y+=1752){
                for(int x=0;x<=1980;x+=384){
                    obstacles.add(new TreeLog(game, race_screen,x+300,y+876));
                }
            }
            add_boats_to_leg(1);
                break;

            case LEG_TWO:
            for (int y = 1100; y < 20000; y+=1000) {
                for (int lane = 0; lane < 5; lane++) {
                    obstacles.add(new Duck(game, race_screen, lane*384+184, y, 20, DuckDirection.LEFT, lane));
                }
            }
            for(int y=0;y<20000;y+=1000){
                for(int x=0;x<=1980;x+=384){
                    obstacles.add(new Rock(game, race_screen,x+200,y+500));
                }
            }
            for(int y=0;y<20000;y+=1500){
                for(int x=0;x<=1980;x+=384){
                    obstacles.add(new TreeLog(game, race_screen,x+300,y+1000));
                }
            }
            for(int y=0;y<20000;y+=750){
                for(int x=0;x<=1980;x+=384){
                    obstacles.add(new TreeLog(game, race_screen,x+25,y+1000));
                }
            }
            add_boats_to_leg(2);
                break;
            
            case LEG_THREE:
            for (int y = 3100; y < 20000; y+=3000) {
                for (int lane = 0; lane < 5; lane++) {
                    obstacles.add(new Duck(game, race_screen, lane*384+200, y, 25, DuckDirection.LEFT, lane));
                }
            }
            for(int y=0;y<20000;y+=1000){
                for(int x=0;x<=1980;x+=384){
                    obstacles.add(new Rock(game, race_screen,x+200,y+500));
                }
            }
            for(int y=0;y<20000;y+=876){
                for(int x=0;x<=1980;x+=384){
                    obstacles.add(new TreeLog(game, race_screen,x+75,y+1000));
                }
            }
            for(int y=0;y<20000;y+=876){
                for(int x=0;x<=1980;x+=384){
                    obstacles.add(new TreeLog(game, race_screen,x+300,y+600));
                }
            }
            add_boats_to_leg(3);
            break;
            case LEG_FINAL:
            for(int y=0;y<20000;y+=876){
                for(int x=0;x<=1920;x+=384){
                    obstacles.add(new TreeLog(game, race_screen,x+50,y+1000));
                }
            }
            for(int y=876;y<20000;y+=876){
                for(int x=0;x<=1920;x+=384){
                    obstacles.add(new TreeLog(game, race_screen,x+270,y+500));
                }
            }
            for (int y = 1100; y < 20000; y+=1000) {
                for (int lane = 0; lane < 5; lane++) {
                    obstacles.add(new Duck(game, race_screen, lane*384+200, y+200, 35, DuckDirection.LEFT, lane));
                }
            }
            for (int y = 100; y < 20000; y+=3000) {
                for (int lane = 0; lane < 5; lane++) {
                    obstacles.add(new Duck(game, race_screen, lane*384+200, y+1200, 30, DuckDirection.LEFT, lane));
                }
            }
            for(int y=0;y<=20000;y+=1000){
                for(int x=0;x<=1920;x+=384){
                    obstacles.add(new Rock(game, race_screen,x+100,y+200));
                }
            }
            for(int y=0;y<=20000;y+=1000){
                for(int x=0;x<=1920;x+=384){
                    obstacles.add(new Rock(game, race_screen,x+300,y+200));
                }
            }
            for(int y=0;y<=20000;y+=1000){
                for(int x=0;x<=1920;x+=384){
                    obstacles.add(new Rock(game, race_screen,x+198,y+400));
                }
            }
            //remove players who didnt qualify
            //players.remove()
            
            add_boats_to_leg(4);
            break;
        }
    }

    private void add_boats_to_leg(int leg_no) {
        Boat.Boat_Type type = BoatSelectScreen.getBoat();
        PlayerBoat player_boat = new PlayerBoat(game, this.race_screen, type);
        players.add(player_boat);
        race_screen.player_boat = player_boat;

        List<Boat.Boat_Type> available_types = new ArrayList<Boat.Boat_Type>();
        available_types.add(Boat.Boat_Type.FAST);
        available_types.add(Boat.Boat_Type.HARD);
        available_types.add(Boat.Boat_Type.ACCEL);
        available_types.add(Boat.Boat_Type.MANOEUVREABLE);
        available_types.add(Boat.Boat_Type.DEFAULT);

        available_types.remove(type);
        
        players.add(new AIBoat(game, race_screen, available_types.get(0), 0, leg_no));
        players.add(new AIBoat(game, race_screen, available_types.get(1), 1, leg_no));
        players.add(new AIBoat(game, race_screen, available_types.get(2), 3, leg_no));
        players.add(new AIBoat(game, race_screen, available_types.get(3), 4, leg_no));

        List<Boat> boats_to_remove = new ArrayList<Boat>();
        for (Boat boat : players) {
            if (game.score_board.disqualified_boats.contains(boat.getName())) {
                boats_to_remove.add(boat);
            }
        }
        players.removeAll(boats_to_remove);

        if (!players.contains(player_boat)) {
            game.changeScreen(DragonBoatGame.GAME_OVER);
            resetAll();
        }
    }
    /**
     * Increment the current leg to the next
     */
    private void incrementCurrentLeg() {

        switch (current_leg) {
            case LEG_ONE:
                current_leg = Legs.LEG_TWO;
                break;
            case LEG_TWO:
                current_leg = Legs.LEG_THREE;
                break;
            case LEG_THREE:
                current_leg = Legs.LEG_FINAL;
                break;
            default:
                current_leg = Legs.LEG_ONE;
                break;
        }

    }

}

