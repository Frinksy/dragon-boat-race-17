package org.gnocchigames.dragonboat;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;


/**
 * Boat
 * Class which implements a boat
 * It is not designed to be used directly,
 * instead please use PlayerBoat or AIBoat
 */
public class Boat extends Entity{

    public static enum Boat_Type {
        FAST, HARD, ACCEL, MANOEUVREABLE, DEFAULT
    }

    public int speed_stat;
    public int acceleration_stat;
    public int manoeuverability_stat;
    public int robustness_stat;
    public float tiredness_factor;
    public int direction;
    public int current_health;
    public float current_penalty;

    private Boolean in_lane;

    private static final float MAX_BACKWARDS_SPEED = -20;
    private static final float MIN_TIRED_SPEED = 20;
    private static final float MIN_TIREDNESS_FACTOR = 0.1f;

    private static final float VELOCITY_CONSTANT = 2;


    public Boat(Boat_Type type) {

        
        
        Texture boat_texture = new Texture("boat.png"); // TODO: Do not hardcode file name
        this.sprite = new Sprite(boat_texture);
        this.sprite.setOrigin(this.sprite.getWidth()/2, this.sprite.getHeight()/2);
        this.sprite.scale(-0.25f);

        this.pos_x = 0;
        this.pos_y = 0;
        this.direction = 0;
        this.current_health = 100;
        this.current_penalty = 0;
        this.velocity = 1;

        setStats(type);


        this.in_lane = true; // We assume the boat starts in lane
        this.tiredness_factor = 1f;

        this.hitbox = getBoundingPolygon();


    }

    @Override
    public Polygon getBoundingPolygon() {

         float [] vertices = {
            0, 0,
            0, 100,
            20, 120,
            30, 120,
            50, 100,
            50, 0

        };

        Polygon output = new Polygon(vertices);
        output.scale(-0.25f);
        output.setOrigin(24, 60);
        output.setPosition(pos_x, pos_y);
        output.setRotation(direction);

        return output;
    }


    /**
     * Set the stats for the boat
     * @param type the preset for the boat stats
     */
    final private void setStats(Boat_Type type) {
        switch (type) {
            case FAST:
                this.acceleration_stat = 70;
                this.speed_stat = 100;
                this.manoeuverability_stat = 50;
                this.robustness_stat = 40;
                break;
            case HARD:
                this.acceleration_stat = 60;
                this.speed_stat = 80;
                this.manoeuverability_stat = 40;
                this.robustness_stat = 100;
                break;
            case ACCEL:
                this.acceleration_stat = 100;
                this.speed_stat = 80;
                this.manoeuverability_stat = 70;
                this.robustness_stat = 40;
                break;
            case MANOEUVREABLE:
                this.acceleration_stat = 80;
                this.speed_stat = 75;
                this.manoeuverability_stat = 100;
                this.robustness_stat = 20;
                break;
            default:
                this.acceleration_stat = 50;
                this.speed_stat = 50;
                this.manoeuverability_stat = 50;
                this.robustness_stat = 50;
                break;
        }
    }
    
    /**
     * Update the boat's properties and stats
     * Should be called every frame
     */
    @Override
    public void update(float delta_time, List<Entity> entities) {

        double rad_angle = this.direction * (Math.PI / 180);

        double delta_y = Math.cos(rad_angle) * velocity * delta_time * VELOCITY_CONSTANT;
        double delta_x = -Math.sin(rad_angle) * velocity * delta_time * VELOCITY_CONSTANT;

        this.pos_x += delta_x;
        this.pos_y += delta_y;

        this.sprite.setPosition(this.pos_x, this.pos_y);
        this.hitbox.setPosition(this.pos_x, this.pos_y);
        this.hitbox.setRotation(this.direction);

        // DEBUG
        //System.out.printf("Current velocity: %f\r", this.velocity);
        //System.out.printf("Current tiredness: %f\r", this.tiredness_factor);

        
        
        if (isCollided(entities)) {
            System.out.println("Boat collided " + this);
            applyCollision(null);
        } else {
            System.out.println("Boat not collided " + this);
        }
        // apply water resistance
        velocity *= 0.995;
        
    }


    /**
     * Make the boat go faster
     * Changes speed based off of the boat's current stats
     */
    public void accelerate() {

        float accel = Math.max(this.acceleration_stat * MIN_TIREDNESS_FACTOR ,this.acceleration_stat * this.tiredness_factor);
        changeSpeed(accel);
        getTired();
    
    }

    /**
     * Slow the boat down
     */
    public void decelerate() {
        changeSpeed(-100);
    }

    /**
     * Private convenience function to apply the change in speed
     * in speed from accellerate() and decelerate()
     * @param value the acceleration value to be applied (units.s^-2)
     */
    private void changeSpeed(float value) {

        this.velocity += value * Gdx.graphics.getDeltaTime();

        float current_max_speed = Math.max(this.speed_stat * this.tiredness_factor, MIN_TIREDNESS_FACTOR * MIN_TIRED_SPEED);

        if (this.velocity > current_max_speed) {
            

            this.velocity = this.speed_stat * this.tiredness_factor;
        }

        if (this.velocity < Boat.MAX_BACKWARDS_SPEED) {
            this.velocity = Boat.MAX_BACKWARDS_SPEED;
        }


    }

    /**
     * Turn the boat left
     */
    public void turnLeft() {
        changeDirection(1);
    }

    /**
     * Turn the boat right
     */
    public void turnRight() {
        changeDirection(-1);
    }

    /**
     * Private convenience function to apply the turning of the boat
     * Called by turnLeft() and turnRight();
     * @param diff the angle difference in degrees
     */
    private void changeDirection(int diff) {
        int angleDiff = this.direction + diff * this.manoeuverability_stat / 50; 
        this.direction = Math.floorMod(angleDiff, 360);
        this.sprite.setRotation(this.direction);
    }

    private Boolean isZeroHP() {
        return current_health > 0;
    }

    private void getTired() {
        tiredness_factor = tiredness_factor - (0.005f * tiredness_factor * Gdx.graphics.getDeltaTime());
    }

    private Boolean checkInLane() {
        //TODO
        return false;
    }
    

    @Override
    public void applyCollision(Entity other) {
        
        double rad_angle = (180 + this.direction) * Math.PI / 180;

        double delta_y = Math.cos(rad_angle) * velocity * Gdx.graphics.getDeltaTime() * VELOCITY_CONSTANT;
        double delta_x = -Math.sin(rad_angle) * velocity * Gdx.graphics.getDeltaTime() * VELOCITY_CONSTANT;

        pos_x += delta_x;
        pos_y += delta_y;

        this.sprite.setPosition(pos_x, pos_y);
        this.hitbox.setPosition(pos_x, pos_y);
        
        
    }

    @Override
    public void remove() {
        //TODO
    }


}