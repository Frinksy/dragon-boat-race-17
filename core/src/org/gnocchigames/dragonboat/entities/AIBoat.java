package org.gnocchigames.dragonboat.entities;


public class AIBoat{

    public Boat boat;

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

    /**
     * The ai for our boat
     * takes a list of checkpoints and uses boat controls to turn/ move them to it
     */
    public static void AI(Boat boat, float[] x, float[] y){
        
        float[]check_x = x;
        float[]check_y = y;
        //float[] check_x = {0f, 500f, 500f, 750f};
        //float[] check_y = {0f, 500f, 1000f, 1500f};

        for (int i = 1; i < (check_y.length); i++){
            if ((current_y < check_y[i]) & (current_y >= check_y[i-1])){
                // get all currrent necessary boat stats
                current_x = boat.pos_x;
                current_y = boat.pos_y;
                current_angle = boat.getDirection();

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
                System.out.println(check_x + " " + current_x + " " + check_y + " " + current_y);
                //System.out.println(wanted_angle_deg + " " + current_angle + " " + diff_angle );
                System.out.println(i);
        
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
    }

    public static float[] getXCoords(int boat_num, int race_leg){

        System.out.println(boat_num + " " + race_leg);
        if ((boat_num == 1) & (race_leg == 1)){
            float[] x_coords = {0f, 500f, 500f, 750f};
            return x_coords;
        } else {

        }
        
        return x_coords;
    }

    public static float[] getYCoords(int boat_num, int race_leg){
        if ((boat_num == 1) & (race_leg == 1)){
            float[] y_coords = {0f, 500f, 1000f, 1250f};
            return y_coords;
        }
       
        return y_coords;
    }
}