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
        check_x = getXCoords(lane, leg_num, type);
        check_y = getYCoords(lane, leg_num, type);
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


    public static float[] getXCoords(int lane_num, int race_leg, Boat_Type type){

        System.out.println(lane_num + " " + type + " " + race_leg);
        //Speed
        if ((lane_num == 0) & (type == Boat_Type.FAST)){
            if (race_leg == 1){
                float[] x_coords = {0f, 75f, 330f, 75f, 330f, 75f, 330f, 75f, 330f, 75f, 192f, 75f, 
                    330f, 75f, 330f, 125f, 125f};
                return x_coords;
            }
        //Robust
        } else if (((lane_num == 1) & (type == Boat_Type.HARD)) || ((lane_num == 0) & (type == Boat_Type.HARD))){
            if (race_leg == 1){
                float[] x_coords = {0f, 400f, 500f, 750f};
                return x_coords;
            }
        //Acc
        } else if (((lane_num == 1) & (type == Boat_Type.ACCEL)) || ((lane_num == 3) & (type == Boat_Type.ACCEL))){
            if (race_leg == 1){
                float[] x_coords = {0f, 1000f, 900f, 750f};
                return x_coords;
            }
        //Maneuver
        } else if (((lane_num == 3) & (type == Boat_Type.MANOEUVREABLE)) || ((lane_num == 4) & (type == Boat_Type.MANOEUVREABLE))){
            if (race_leg == 1){
                float[] x_coords = {0f, 1300f, 1400f, 1300f};
                return x_coords;
            }
        //Default
        } else if ((lane_num == 4) & (type == Boat_Type.DEFAULT)){
            if (race_leg == 1){
                float[] x_coords = {0f, 1700f, 1650f, 1750f};
                return x_coords;
            }
        }
        return x_coords;
    }

    public static float[] getYCoords(int lane_num, int race_leg, Boat_Type type){
        if ((lane_num == 0) & (type == Boat_Type.FAST)){
            if (race_leg == 1){
                float[] y_coords = {0f, 500f, 1500f, 2500f, 3500f, 4500f, 5500f, 6250f, 7250f, 8250f, 9000f, 9750f, 
                    10500f, 11250f, 12250f, 12750, 15000};
                return y_coords;
            }
        //Robust
        } else if (((lane_num == 1) & (type == Boat_Type.HARD)) || ((lane_num == 0) & (type == Boat_Type.HARD))){
            if (race_leg == 1){
                float[] y_coords = {0f, 500f, 1000f, 2000f};
                return y_coords;
            }
        //Acc
        } else if (((lane_num == 1) & (type == Boat_Type.ACCEL)) || ((lane_num == 3) & (type == Boat_Type.ACCEL))){
            if (race_leg == 1){
                float[] y_coords = {0f, 500f, 1000f, 2000f};
                return y_coords;
            }
        //Maneuver
        } else if (((lane_num == 3) & (type == Boat_Type.MANOEUVREABLE)) || ((lane_num == 4) & (type == Boat_Type.MANOEUVREABLE))){
            if (race_leg == 1){
                float[] y_coords = {0f, 500f, 1000f, 2000f};
                return y_coords;
            }
        //Default
        } else if ((lane_num == 4) & (type == Boat_Type.DEFAULT)){
            if (race_leg == 1){
                float[] y_coords = {0f, 500f, 1000f, 2000f};
                return y_coords;
            }
        }
        return y_coords;
    }
}