package org.gnocchigames.dragonboat;

import java.util.ArrayList;
import org.gnocchigames.dragonboat.Boat;

public class RaceStructure extends DragonBoatGame {

    RaceLegScreen screen;

    public RaceStructure (RaceLegScreen screen) {
        this.screen = screen;
    }

    public void reset(){
        
    }
    public boolean allBoatsAcross(){
        return false;

    }
    public boolean playerBoatAcross(PlayerBoat player_boat){
        if (player_boat.pos_y > 1080*5){
            return true;
        }else{
            return false;
        }
    }
    public boolean raceover(PlayerBoat player_boat){
        if (playerBoatAcross(player_boat) || allBoatsAcross()){
            return true; 
        }else{
            return false;
        }
    }
    public void startLeg1(PlayerBoat player_boat){

        //When play buttton pressed from boat selection screen
        changeScreen(4);
        while (!raceover(player_boat)){}
        //when raceover() change to loading screen
        changeScreen(7);
        //reset()
        reset();
        
    }
    public void startLeg2(PlayerBoat player_boat){
        //from loading screen to new leg screen
        //load leg screen
        changeScreen(4);
        while (!raceover(player_boat)){}
        //when raceover() change to loading screen
        changeScreen(7);
        //reset()
        reset();
    }
    public void startLeg3(PlayerBoat player_boat){
        changeScreen(4);
        while (!raceover(player_boat)){}
        //when raceover() change to loading screen
        changeScreen(7);
        //reset()
        reset();
    }
    
    public void startFinal(PlayerBoat player_boat){
        changeScreen(4);
        while (!raceover(player_boat)){}
        //when raceover() change to podium screen
        changeScreen(5);
        
    }  
}
