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
            float[] x_coords = {0f+a, 50f+a, 330f+a, 50f+a, 75f+a, 320f+a, 50f+a, 340f+a, 50f+a, 330f+a, 50f+a, 50f+a, 50f+a, 
                330f+a, 50f+a, 330f+a, 75f+a, 75f+a, 192f+a, 75f+a, 75f+a, 330f+a, 75f+a, 330f+a, 100f+a, 50f+a, 330f+a, 192f+a, 50f+a, 50f+a};
            return x_coords;
            
        } else if (race_leg == 2){
            if (type == Boat.Boat_Type.FAST){
                float[] x_coords = {0f+a, 50f+a, 150f+a, 120f+a, 100f+a, 75f+a, 90f+a, 90f+a, 
                50f+a, 90f+a, 300f+a, 300f+a, 120f+a, 100f+a, 290f+a, 150f+a, 170f+a, 300f+a, 300f+a, 100f+a,
                100f+a, 100f+a, 300f+a, 300f+a, 200f+a, 300f+a, 140f+a, 120f+a, 120f+a, 300f+a, 150f+a, 100f+a, 250f+a, 
                130f+a, 200f+a, 300f+a, 200f+a, 300f+a, 160f+a, 100f+a, 100f+a};
                return x_coords;
            } else {
                float[] x_coords = {0f+a, 50f+a, 150f+a, 300f+a, 300f+a, 120f+a, 80f+a, 110f+a, 300f+a, 150f+a,
                    300f+a, 80f+a, 85f+a, 100f+a, 275f+a, 275f+a, 150+a, 100f+a, 90f+a, 200f+a, 110f+a, 90f+a, 90f+a,
                    180f+a, 300f+a, 100f+a, 90f+a, 300f+a, 300f+a, 150f+a, 300f+a, 100f+a, 100f+a, 300f+a,
                    300f+a, 150f+a, 300f+a, 300f+a, 95f+a, 85f+a, 300f+a, 150f+a, 75f+a, 75f+a, 75f+a, 75f+a};
                return x_coords;
            }
            
        } else if (race_leg == 3){
            if (type == Boat.Boat_Type.FAST){
                float[] x_coords = {0f+a, 100f+a, 160f+a, 120f+a, 160f+a, 120f+a, 150f+a, 175f+a, 300f+a, 300f+a,
                    250f+a, 275f+a, 200f+a, 250f+a, 300f+a, 130f+a, 160f+a, 100f+a, 160f+a, 100f+a, 150f+a, 100f+a,
                    200f+a, 350f+a, 350f+a, 150f+a, 300f+a, 350f+a, 350f+a, 120f+a, 350f+a, 350f+a, 350f+a, 350f+a};
                return x_coords;
            } else if (type == Boat.Boat_Type.HARD){
                float[] x_coords ={0f, 300f+a, 300f+a, 300f+a, 300f+a, 300f+a, 350f+a, 350f+a, 350f+a};
                return x_coords;
            } else {
                float[] x_coords = {0f+a, 75f+a, 150f+a, 75f+a, 150f+a, 75f+a, 180f+a, 200f+a, 250f+a, 175f+a,
                    250f+a, 150f+a, 250f+a, 165f+a, 280f+a, 270f+a, 150f+a, 150f+a, 50f+a, 150f+a, 50f+a,
                    125f+a, 50f+a, 50f+a, 150f+a, 150f+a, 250f+a, 150f+a, 250f+a, 150f+a, 250f+a, 150f+a,
                    140f+a, 65f+a, 150f+a, 60f+a, 150f+a, 60f+a, 150f+a, 30f+a, 150f+a, 150f+a, 250f+a,
                    150f+a, 275f+a, 150f+a, 250f+a, 150f+a, 150f+a};
                return x_coords;
            }
        } else {
            if (type == Boat.Boat_Type.HARD){
                float[] x_coords ={0f, 100f+a, 20f+a, 20f+a, 20f+a, 20f+a, 20f+a, 20f+a, 340f+a, 340f+a,
                    340f+a, 340f+a, 340f+a, 340f+a, 340f+a, 340f+a, 340f+a, 340f+a};
                return x_coords;
            } else if (type == Boat.Boat_Type.ACCEL){
                float[] x_coords ={0f, 200f+a, 300f+a, 170f+a, 100f+a, 170f+a, 100f+a, 180f+a, 300f+a, 180f+a,
                    300f+a, 330f+a, 170f+a, 170f+a, 100f+a, 170f+a, 100f+a, 170f+a, 100f+a, 170f+a,
                    100f+a, 170f+a, 275f+a, 185f+a, 330f+a, 330f+a, 180f+a, 100f+a, 180f+a, 100f+a,
                    335f+a, 340f+a, 340f+a, 340f+a, 160f+a, 170f+a};
                return x_coords;
            } else if (type == Boat.Boat_Type.MANOEUVREABLE){
                float[] x_coords ={0f, 175f+a, 280f+a, 160f+a, 80f+a, 160f+a, 80f+a, 160f+a, 280f+a,
                    150f+a, 80f+a, 140f+a, 80f+a, 150+a, 330f+a, 345f+a};
                return x_coords;
            } else {
                float[] x_coords = {0f+a, 50f+a, 330f+a, 50f+a, 75f+a, 320f+a, 50f+a, 340f+a, 50f+a, 330f+a, 50f+a, 50f+a, 50f+a, 
                330f+a, 50f+a, 330f+a, 75f+a, 75f+a, 192f+a, 75f+a, 75f+a, 330f+a, 75f+a, 330f+a, 100f+a, 50f+a, 330f+a, 192f+a, 50f+a, 50f+a};
                return x_coords;
            }
        }
    }

    public float[] getYCoords(int lane_num, int race_leg, Boat_Type type){
        if (race_leg == 1){
            float[] y_coords = {0f, 500f, 1500f, 2500f, 3000f, 3500f, 4500f, 5500f, 6300f, 7250f, 8250f, 9000f, 9750f, 
                10500f, 11500f, 12250f, 13000f, 13500f, 14000f, 14500f, 15000f, 15500f, 16500f, 17500f, 18000f, 18500f, 19500f, 20000f, 20500f, 21000f};
            return y_coords;
        
        } else if (race_leg == 2){
            if (type == Boat.Boat_Type.FAST){
                float[] y_coords = {0f, 500f, 1000f, 1500f, 1750f, 2000f, 2500f, 3250f, 3500f,
                    3750f, 4250f, 4500f, 5250f, 5750f, 6300f, 6750f, 6900f, 7500f, 8500f, 9500f,
                    10500f, 11500f, 12000f, 12500f, 13000f, 13500f, 14000f, 14500f, 15000f, 15500f, 16000f, 16500f, 17000f, 17500f, 
                    18000f, 18500f, 19000f, 19600f, 20000f, 20400f, 21000f};
                return y_coords;
            } else {
                float[] y_coords = {0f, 500f, 1000f, 1500f, 1800f, 2300f, 2650f, 3000f, 3500f, 3800f, 4500f,
                    5000f, 5450f,  5600f, 6000f, 6500f, 7000f, 7500f, 7600f, 7850f, 8400f, 8600f, 9600f,
                    10000f, 10500f, 11000f, 11600f, 12250f, 12750f, 13000f, 13300f, 14000f, 14750f, 15250f, 15500f,
                    16000f, 16500f, 17000f, 17400f, 17600f, 182500f, 18750f, 19250f, 20000f, 20400f, 21000f};
                return y_coords;
            }
            
        }   else if (race_leg == 3){
            if (type == Boat.Boat_Type.FAST){
                float[] y_coords = {0f, 400f, 900f, 1300f, 1900f, 2400f, 2700f, 3150f, 3400f, 3600f,
                    4000f, 4500f, 4900f, 5400f, 5900f, 6400f, 7050f, 7450f, 7950f, 8450f, 8950f, 9400f,
                    9700f, 10100f, 11500f, 12000f, 12450f, 12650f, 14800f, 15500f, 15800f, 16200f, 17000f, 21000f};
                return y_coords;
            } else if (type == Boat.Boat_Type.HARD){
                float[] y_coords ={0f, 500f, 5000f, 7500f, 10000f, 12500f, 15000f, 17500f, 21000f};
                return y_coords;
            } else {
                float[] y_coords = {0f, 400f, 900f, 1400f, 1900f, 2400f, 2800f, 3300f, 3500f, 4000f,
                    4400f, 4900f, 5400f, 5800f, 6200f, 6450f, 6800f, 7100f, 7500f, 8000f, 8300f,
                    8800f, 9200f, 9400f, 9700f, 10200, 10500, 11000, 11500, 12000f, 12500f, 13000f,
                    13250f, 13500f, 14000f, 14500f, 15000f, 15500f, 16000f, 16400f, 16900f, 17300f, 17500f,
                    18000f, 18500f, 19000f, 19500f, 20000f, 21000f};
                return y_coords;
            }
        } else {
            if (type == Boat.Boat_Type.HARD){
                float[] y_coords ={0f, 500f, 800f, 2000f, 4000f, 5000f, 6000f, 7500f, 8000f, 10000f, 110000f,
                    12500f, 14000f, 15000f, 16000f, 17500f, 19000f, 21000f};
                return y_coords;
            } else if ((type == Boat.Boat_Type.ACCEL)){
                float[] y_coords ={0f, 200f, 400f, 1175f, 1400f, 2175f, 2400f, 3175f, 3400f, 4100f,
                    4400f, 5400f, 5800f, 6100f, 6400f, 7100f, 7400f, 8100f, 8400f, 9100f,
                    9400f, 10150f, 10500f, 11150, 11500, 13700f, 14150f, 14400f, 15150f, 15400f,
                    15900f, 16500f, 18000f, 19300f, 19800f, 21000f};
                return y_coords;
            } else if ((type == Boat.Boat_Type.MANOEUVREABLE)){
                float[] y_coords ={0f, 200f, 400f, 1150f, 1400f, 2150f, 2400, 3150f, 3400f,
                    4150f, 4400f, 5150f, 5500f, 6150f, 6400f, 7800f};
                return y_coords;
            }else {
                float[] y_coords = {0f, 500f, 1500f, 2500f, 3000f, 3500f, 4500f, 5500f, 6300f, 7250f, 8250f, 9000f, 9750f, 
                10500f, 11500f, 12250f, 13000f, 13500f, 14000f, 14500f, 15000f, 15500f, 16500f, 17500f, 18000f, 18500f, 19500f, 20000f, 20500f, 21000f};
                return y_coords;
            }
        }
    }
}