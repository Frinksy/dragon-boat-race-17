package org.gnocchigames.dragonboat.entities;

import com.badlogic.gdx.Gdx;

public class AIBoat{

    public Boat boat;

    public static double current_angle;
    public static double wanted_angle_rad;
    public static double wanted_angle_deg;
    public static double diff_angle;

    public static float current_x;
    public static float current_y;
    public static float check_x;
    public static float check_y;
    public static float dif_x;
    public static float dif_y;

    /**
     * The ai for our boat
     * takes a list of checkpoints and uses boat controls to turn/ move them to it
     */
    public static void AI(Boat boat){
        check_x = 1000;
        check_y = 500;

        // get all currrent necessary boat stats
        current_x = boat.pos_x;
        current_y = boat.pos_y;
        current_angle = boat.getDirection();

        //find the angle needed to get to the checkpoin
        wanted_angle_rad = Math.atan((check_y - current_y)/(check_x - current_x));
        wanted_angle_deg = Math.toDegrees(wanted_angle_rad);
        if((check_x - current_x) < 0){
            wanted_angle_deg += 90;
        } else {
            wanted_angle_deg -= 90;
        }
        
        //calculate the change in angle from current;
        diff_angle = (wanted_angle_deg - current_angle);

        //DEBUG
        //System.out.println(check_x + " " + current_x + " " + check_y + " " + current_y);
        //System.out.println(wanted_angle_deg + " " + current_angle + " " + diff_angle );
 
        //uses diff angle to decide which way to turn
        boat.accelerate();
        if (diff_angle < 1){
            boat.turnRight();
        } else if (diff_angle > 1){
            boat.turnLeft();
        } else {
            boat.accelerate();
        }
    }
}