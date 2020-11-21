package org.gnocchigames.dragonboat.entities;

import java.util.List;

import org.gnocchigames.dragonboat.util.GameStructure;
import org.gnocchigames.dragonboat.screens.RaceLegScreen;


public class AIBoat extends Boat{

    public Boat boat;

    public float[] check_x;
    public float[] check_y;

    public static double current_angle;
    public static double wanted_angle_rad;
    public static double wanted_angle_deg;
    public static double diff_angle;

    public static float current_x;
    public static float current_y;
    public static float[] x_coords;
    public static float[] y_coords;
    public static float dif_x;
    public static float dif_y;

    public AIBoat(RaceLegScreen parent, Boat_Type type, int lane, int leg_num){

        
        super(parent, type, lane);
        check_x = getXCoords(lane, leg_num);
        check_y = getYCoords(lane, leg_num);
    }

    public void update(float delta_time, List<Entity> entities){

        super.update(delta_time, entities);

        for (int i = 1; i < check_y.length; i++){

            if ((current_y < check_y[i]) & (current_y >= check_y[i-1])){
                // get all currrent necessary boat stats
                current_x = pos_x;
                current_y = pos_y;
                current_angle = getDirection();

                //find the angle needed to get to the checkpoin
                wanted_angle_rad = Math.atan((check_y[i] - current_y)/(check_x[i] - current_x));
                wanted_angle_deg = Math.toDegrees(wanted_angle_rad);
                if((check_x[i] - current_x) < 0){
                    wanted_angle_deg += 90;
                } else {
                    wanted_angle_deg -= 90;
                }
                
                //calculate the change in angle from current;
                diff_angle = (wanted_angle_deg - current_angle);

                //DEBUG
                //System.out.println(check_x + " " + current_x + " " + check_y + " " + current_y);
                //System.out.println(wanted_angle_deg + " " + current_angle + " " + diff_angle );
                //System.out.println(i);
        
                //uses diff angle to decide which way to turn
                accelerate();
                if (diff_angle < 1){
                    turnRight();
                } else if (diff_angle > 1){
                    turnLeft();
                } else {
                    accelerate();
                }
            }
        }
    }


    public static float[] getXCoords(int boat_num, int race_leg){

        System.out.println(boat_num + " " + race_leg);
        if ((boat_num == 0) & (race_leg == 1)){
            float[] x_coords = {0f, 100f, 50f, 250f};
            return x_coords;
        } else if ((boat_num == 1) & (race_leg == 1)) {
            float[] x_coords = {0f, 400f, 300f, 750f};
            return x_coords;
        }  else if ((boat_num == 2) & (race_leg == 1)) {
            float[] x_coords = {0f, 900f, 1200f, 1050f};
            return x_coords;
        } else if ((boat_num == 3) & (race_leg == 1)) {
            float[] x_coords = {0f, 1400f, 1300f, 1550f};
            return x_coords;
        } else if ((boat_num == 4) & (race_leg == 1)) {
            float[] x_coords = {0f, 1650f, 1700f, 1850f};
            return x_coords;
        }

        
        return x_coords;
    }

    public static float[] getYCoords(int boat_num, int race_leg){
        if ((boat_num == 0) & (race_leg == 1)){
            float[] y_coords = {0f, 500f, 1000f, 1250f};
            return y_coords;
        }  else if ((boat_num == 1) & (race_leg == 1)) {
            float[] y_coords = {0f, 500f, 500f, 750f};
            return y_coords;
        }  else if ((boat_num == 2) & (race_leg == 1)) {
            float[] y_coords = {0f, 500f, 500f, 750f};
            return y_coords;
        } else if ((boat_num == 3) & (race_leg == 1)) {
            float[] y_coords = {0f, 500f, 500f, 750f};
            return y_coords;
        } else if ((boat_num == 4) & (race_leg == 1)) {
            float[] y_coords = {0f, 500f, 500f, 750f};
            return y_coords;
        }
        return y_coords;
    }
}