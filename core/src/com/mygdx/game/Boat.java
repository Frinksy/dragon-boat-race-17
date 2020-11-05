package com.mygdx.game;

/**
 * Boat
 */
public class Boat extends Entity{

    public int speed_stat;
    public int acceleration_stat;
    public int maneauverability_stat;
    public int robustness_stat;
    public float tiredness_factor;
    public int direction;
    public int current_health;
    private Boolean in_lane;
    public float current_penalty;


    public void accelerate() {
        //TODO
    }

    public void deccelerate() {
        //TODO
    }

    private void changeSpeed(int value) {
        //TODO
    }

    public void turnLeft() {
        //TODO
    }

    public void turnRight() {
        //TODO
    }

    private void changeDirection() {
        //TODO
    }

    private Boolean isZeroHP() {
        //TODO
        return false;
    }

    private void getTired() {
        //TODO
    }

    private Boolean checkInLane() {
        //TODO
        return false;
    }
    
}