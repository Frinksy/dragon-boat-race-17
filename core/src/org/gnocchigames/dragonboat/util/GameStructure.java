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


public class GameStructure {

    public static enum Legs {
        LEG_ONE, LEG_TWO, LEG_THREE, LEG_FINAL
    }


    private DragonBoatGame game;

    private RaceLegScreen race_screen; // The screen which will be used to draw and update the legs

    private List<Boat> players; // The list of players in the game
    private List<Obstacle> obstacles; // The list of obstacles in the game
    //multiple is amount of screens
    private static int FINISH_HEIGHT = 20000;

    public boolean playerBoatAcross(PlayerBoat player_boat){
        if (player_boat.pos_y > FINISH_HEIGHT){
            return true;
        }else{
            return false;
        }
    }
    public boolean allBoatsAcross(){
        return false;
    }
    public boolean raceover(PlayerBoat player_boat){
        if (playerBoatAcross(player_boat) || allBoatsAcross()){
            System.out.println("test");
            for (Obstacle obstacle : obstacles){
                race_screen.removeEntity(obstacle);
            }
            for (Boat boat : players){
                race_screen.removeEntity(boat);
            }
            set_leg(Legs.LEG_TWO);
            start_leg();
            return true; 
        }else{
            return false;
        }
    }
   

    
    public GameStructure(RaceLegScreen parent) {
        race_screen = parent;
        players = new ArrayList<Boat>(5);
        obstacles = new ArrayList<Obstacle>();
    } 

    public void callScreen(){

    }

    /**
     * Start the current set leg
     */
    public void start_leg() {

        System.out.println(players.size());
        // Reset race_screen
        //race_screen.resetEntities();
        
        // Add the players to the race
        for (Boat player : players) {
            player.startTimer();
            race_screen.addEntity(player);
            race_screen.boats.add(player);
            //player.AIBoat(race_screen, players, 1, 1);
            
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
    public void set_leg (Legs leg) {

        players = new ArrayList<Boat>();
        obstacles = new ArrayList<Obstacle>();

        Boat.Boat_Type type = BoatSelectScreen.getBoat();
        PlayerBoat player_boat = new PlayerBoat(this.race_screen, type);
        players.add(player_boat);
        race_screen.player_boat = player_boat;

        List<Boat.Boat_Type> available_types = new ArrayList<Boat.Boat_Type>();
        available_types.add(Boat.Boat_Type.FAST);
        available_types.add(Boat.Boat_Type.HARD);
        available_types.add(Boat.Boat_Type.ACCEL);
        available_types.add(Boat.Boat_Type.MANOEUVREABLE);
        available_types.add(Boat.Boat_Type.DEFAULT);

        available_types.remove(type);
        
        players.add(new AIBoat(race_screen, available_types.get(0), 0, 1));
        //
        // Commented out to have the game run
        // These lines should be put back into the switch statement
        players.add(new AIBoat(race_screen, available_types.get(1), 1, 1));
        players.add(new AIBoat(race_screen, available_types.get(2), 3, 1));
        players.add(new AIBoat(race_screen, available_types.get(3), 4, 1));

        switch (leg) {
            case LEG_ONE:
            for(int y=0;y<=20000;y+=3000){
                for (int lane=0;lane<=5;lane++){
                    obstacles.add(new Duck(race_screen, 50, y+200, 20, DuckDirection.LEFT,lane));
                }
            }
            for(int y=0;y<=20000;y+=1000){
                for(int x=0;x<=1980;x+=384){
                    obstacles.add(new Rock(race_screen,x+200,y+500));
                }
            }
            for(int y=0;y<=20000;y+=1752){
                for(int x=0;x<=1980;x+=384){
                    obstacles.add(new TreeLog(race_screen,x+75,y+1752));
                }
            }
            for(int y=0;y<=20000;y+=1752){
                for(int x=0;x<=1980;x+=384){
                    obstacles.add(new TreeLog(race_screen,x+300,y+876));
                }
            }
                break;

            case LEG_TWO:
            for(int y=0;y<=20000;y+=3000){
                for (int lane=0;lane<=5;lane++){
                    obstacles.add(new Duck(race_screen, 50, y+200, 20, DuckDirection.LEFT,lane));
                    obstacles.add(new Duck(race_screen, 50, y+1000, 20, DuckDirection.LEFT,lane));
                }
            }
            for(int y=0;y<=20000;y+=1000){
                for(int x=0;x<=1980;x+=384){
                    obstacles.add(new Rock(race_screen,x+200,y+500));
                }
            }
            for(int y=0;y<=20000;y+=1500){
                for(int x=0;x<=1980;x+=384){
                    obstacles.add(new TreeLog(race_screen,x+300,y+1000));
                }
            }
            for(int y=0;y<=20000;y+=750){
                for(int x=0;x<=1980;x+=384){
                    obstacles.add(new TreeLog(race_screen,x+25,y+1000));
                }
            }
                break;
            
            case LEG_THREE:
            for(int y=0;y<=20000;y+=3000){
                for (int lane=0;lane<=5;lane++){
                    obstacles.add(new Duck(race_screen, 50, y+200, 20, DuckDirection.LEFT,lane));
                }
            }
            for(int y=0;y<=20000;y+=1000){
                for(int x=0;x<=1980;x+=384){
                    obstacles.add(new Rock(race_screen,x+200,y+500));
                }
            }
            for(int y=0;y<=20000;y+=876){
                for(int x=0;x<=1980;x+=384){
                    obstacles.add(new TreeLog(race_screen,x+75,y+1000));
                }
            }
            for(int y=0;y<=20000;y+=876){
                for(int x=0;x<=1980;x+=384){
                    obstacles.add(new TreeLog(race_screen,x+300,y+600));
                }
            }

                break;

            case LEG_FINAL:
                //remove players who didnt qualify
                //players.remove()

                break;
        }
    }
}

