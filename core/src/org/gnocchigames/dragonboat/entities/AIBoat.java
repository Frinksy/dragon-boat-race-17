package org.gnocchigames.dragonboat.entities;

import java.util.List;

import org.gnocchigames.dragonboat.util.GameStructure;
import org.gnocchigames.dragonboat.DragonBoatGame;
import org.gnocchigames.dragonboat.screens.RaceLegScreen;


public class AIBoat extends Boat{

    public Boat boat;

    public int a;
    public float[] check_x;
    public float[] check_y;

    public  double current_angle;
    public  double wanted_angle_rad;
    public  double wanted_angle_deg;
    public  double diff_angle;

    public  float current_x;
    public  float current_y;
    public  float[] x_coords;
    public  float[] y_coords;
    public  float dif_x;
    public  float dif_y;

    public AIBoat(DragonBoatGame game, RaceLegScreen parent, Boat_Type type, int lane, int leg_num){

        
        super(game, parent, type, lane);
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


    public float[] getXCoords(int lane_num, int race_leg, Boat_Type type){

        System.out.println(lane_num + " " + type + " " + race_leg);
        //Speed
        int a = lane_num*394;
        if (race_leg == 1){
            float[] x_coords = {0f+a, 50f+a, 330f+a, 50f+a, 320f+a, 50f+a, 340f+a, 50f+a, 330f+a, 50f+a, 50f+a, 50f+a, 
                330f+a, 50f+a, 330f+a, 75f+a, 75f+a, 192f+a, 75f+a, 75f+a, 330f+a, 75f+a, 330f+a, 100f+a, 50f+a, 330f+a, 192f+a, 50f+a, 50f+a};
            return x_coords;
        //Robust
        } else if (race_leg == 2){
            if (type == Boat.Boat_Type.FAST){
                float[] x_coords = {0f, 50f+a, 150f+a, 120f+a, 100f+a, 75f+a, 90f+a, 90f+a, 
                50f+a, 90f+a, 300f+a, 300f+a, 120f+a, 100f+a, 290f+a, 150f+a, 170f+a, 300f+a, 300f+a, 100f+a,
                100f+a, 70f+a};
                return x_coords;
            } else {
                float[] x_coords = {0f+a, 50f+a, 150f+a, 300f+a, 300f+a, 120f+a, 80f+a, 110f+a, 300f+a, 150f+a,
                    300f+a, 80f+a, 85f+a, 100f+a, 275f+a, 275f+a, 150+a, 100f+a, 90f+a, 200f+a, 110f+a, 90f+a, 90f+a,
                    180f+a, 300f+a, 100f+a, 90f+a, 300f+a, 300f+a, 150f+a, 300f+a, 100f+a, 100f+a, 300f+a,
                    300f+a, 150f+a, 300f+a, 300f+a, 95f+a, 85f+a, 300f+a, 150f+a, 90f+a, 140f+a, 80f+a, 80f+a};
                return x_coords;
            }
            
        }
        //Acc
        /**} else if (lane_num == 2){
            a = lane_num * 384;
            if (race_leg == 1){
                float[] x_coords = {0f, 30f+a, 350f+a, -10f+a, 390f+a, -50f+a, 500f+a, -100f+a, 600f+a, -250f+a, -250f+a, 500f+a, -250f+a, 192f+a, 800f+a};
                return x_coords;
            }
        //Maneuver
        } else if (lane_num == 3){
            a = lane_num * 384;
            if (race_leg == 1){
                float[] x_coords = {0f, 1300f, 1400f, 1300f};
                return x_coords;
            }
        //Default
        } else if (lane_num == 4){
            a = lane_num * 384;
            if (race_leg == 1){
                float[] x_coords = {0f, 1700f, 1650f, 1750f};
                return x_coords;
            }
        }*/
        return x_coords;
    }

    public float[] getYCoords(int lane_num, int race_leg, Boat_Type type){
        if (race_leg == 1){
            float[] y_coords = {0f, 500f, 1500f, 2500f, 3500f, 4500f, 5500f, 6300f, 7250f, 8250f, 9000f, 9750f, 
                10500f, 11500f, 12250f, 13000f, 13500f, 14000f, 14500f, 15000f, 15500f, 16500f, 17500f, 18000f, 18500f, 19500f, 20000f, 20500f, 21000f};
            return y_coords;
            
        //Robust
        } else if (race_leg == 2){
            if (type == Boat.Boat_Type.FAST){
                float[] y_coords = {0f, 500f, 1000f, 1500f, 1750f, 2000f, 2500f, 3250f, 3500f,
                    3750f, 4250f, 4500f, 5250f, 5750f, 6300f, 6750f, 6900f, 7500f, 8500f, 9500f,
                    10500f, 20000f};
                return y_coords;
            } else {
                float[] y_coords = {0f, 500f, 1000f, 1500f, 1800f, 2300f, 2650f, 3000f, 3500f, 3800f, 4500f,
                    5000f, 5450f,  5600f, 6000f, 6500f, 7000f, 7500f, 7600f, 7850f, 8400f, 8600f, 9600f,
                    10000f, 10500f, 11000f, 11600f, 12250f, 12750f, 13000f, 13300f, 14000f, 14750f, 15250f, 15500f,
                    16000f, 16500f, 17000f, 17400f, 17600f, 182500f, 18750f, 19250f, 20000f, 20400f, 21000f};
                return y_coords;
            }
            
        }
        //Acc
        /**} else if (((lane_num == 1) && (type == Boat_Type.ACCEL)) || ((lane_num == 3) && (type == Boat_Type.ACCEL))){
            if (race_leg == 1){
                float[] y_coords = {0f, 500f, 1750f, 3000f, 4250f, 5500f, 6500f, 7650f, 8650f, 9250f, 9750f, 10250f, 10750f, 11500f, 12250f};
                return y_coords;
            }
        //Maneuver
        } else if (lane_num == 3){
            if (race_leg == 1){
                float[] y_coords = {0f, 500f, 1000f, 2000f};
                return y_coords;
            }
        //Default
        } else if (lane_num == 4){
            if (race_leg == 1){
                float[] y_coords = {0f, 500f, 1000f, 2000f};
                return y_coords;
            }
        }*/
        return y_coords;
    }
}