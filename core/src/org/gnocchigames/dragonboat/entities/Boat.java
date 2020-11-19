package org.gnocchigames.dragonboat.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;

import org.gnocchigames.dragonboat.screens.RaceLegScreen;


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
    public float direction;
    public int current_health;
    public float current_penalty;
    public Color colour;

    public int lane_number;

    private Boolean in_lane;
    private long time_of_last_collision;
    private List<Texture> textures;

    private long last_frame_time;
    private int current_frame_index;

    private static final float MAX_BACKWARDS_SPEED = -20;
    private static final float MIN_TIRED_SPEED = 20;
    private static final float MIN_TIREDNESS_FACTOR = 0.1f;

    private static final float VELOCITY_CONSTANT = 2;

    private static final int LANE_WIDTH = 1920 / 5;

    /**
     * Creates a boat of specified type and in the specified assigned lane
     * @param parent the parent screen
     * @param type
     * @param lane
     */
    public Boat(RaceLegScreen parent, Boat_Type type, int lane) {

        this.parent = parent;
        
        // Set up sprite
        // TODO: Do not hardcode file name
        this.textures = new ArrayList<Texture>();
        for (int i = 0; i < 5; i++) {
            this.textures.add(new Texture("boats/boat_brown-" + i + ".png"));
        }
        this.sprite = new Sprite(textures.get(0));
        this.sprite.setOrigin(this.sprite.getWidth()/2, this.sprite.getHeight()/2);
        this.sprite.scale(-0.25f);
        this.last_frame_time = System.currentTimeMillis();
        this.current_frame_index = 0;
        

        // Set initial position
        this.lane_number = lane;
        this.pos_x = 384 * lane + 192;
        this.pos_y = 0;
        this.sprite.setCenter(pos_x, pos_y);

        // Set direction
        this.direction = 0;
        this.sprite.setRotation(direction);


        // Set stats
        this.current_health = 100;
        this.current_penalty = 0;
        this.velocity = 1;

        setStats(type);

        this.in_lane = true; // We assume the boat starts in lane
        this.time_of_last_collision = System.currentTimeMillis();
        this.tiredness_factor = 1f;

        // Set hitbox
        this.hitbox = getBoundingPolygon();

    }

    /**
     * Creates a boat of defined type and in default lane, lane zero
     * @param parent the parent screen
     * @param type
     */
    public Boat(RaceLegScreen parent,  Boat_Type type) {
        this(parent, type, 0);

    }

    /**
     * Gets the bounding polygon around the boat
     */
    @Override
    public Polygon getBoundingPolygon() {

        // float [] vertices = {
        //     0, 0,
        //     0, 100,
        //     20, 120,
        //     30, 120,
        //     50, 100,
        //     50, 0

        // };

        float [] vertices = {
            100, 200,
            136, 136,
            136, 10,
            127, 0,
            72, 0,
            64, 10,
            64, 136
        };

        Polygon output = new Polygon(vertices);
        output.scale(-0.25f);
        output.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
        output.setPosition(sprite.getX(), sprite.getY());
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
                colour = new Color(Color.PINK);
                this.colour = (colour);
                break;
            case HARD:
                this.acceleration_stat = 60;
                this.speed_stat = 80;
                this.manoeuverability_stat = 40;
                this.robustness_stat = 100;
                colour = new Color(Color.GREEN);
                this.colour = (colour);
                break;
            case ACCEL:
                this.acceleration_stat = 100;
                this.speed_stat = 80;
                this.manoeuverability_stat = 70;
                this.robustness_stat = 40;
                colour = new Color(Color.CYAN);
                this.colour = (colour);
                break;
            case MANOEUVREABLE:
                this.acceleration_stat = 80;
                this.speed_stat = 75;
                this.manoeuverability_stat = 100;
                this.robustness_stat = 20;
                colour = new Color(Color.YELLOW);
                this.colour = (colour);
                break;
            default:
                this.acceleration_stat = 50;
                this.speed_stat = 50;
                this.manoeuverability_stat = 50;
                this.robustness_stat = 50;
                colour = new Color(Color.BROWN);
                this.colour = (colour);
                break;
        }
    }
    
    /**
     * Update the boat's properties and stats
     * Should be called every frame
     */
    @Override
    public void update(float delta_time, List<Entity> entities) {

        // Math for boat movement
        double rad_angle = direction * (Math.PI / 180);

        double delta_y = Math.cos(rad_angle) * velocity * delta_time * VELOCITY_CONSTANT;
        double delta_x = -Math.sin(rad_angle) * velocity * delta_time * VELOCITY_CONSTANT;

        // Apply boat movement
        pos_x += delta_x;
        pos_y += delta_y;
        sprite.translate((float)delta_x, (float)delta_y);
        hitbox.translate((float)delta_x, (float)delta_y);

        this.hitbox.setRotation(this.direction);        
        
        // Do collision
        // if (isCollided(entities)) {
        //     applyCollision(null);
        // }

        // Apply water resistance
        velocity *= 0.995;


        // Do the sprite animation
        if (System.currentTimeMillis() - last_frame_time > 10000 / Math.abs((float)velocity)) {
            last_frame_time = System.currentTimeMillis();
            if (velocity > 0) {
                current_frame_index+=1;
            }else {
                current_frame_index-=1;
            }
            if (current_frame_index > 4) {
                current_frame_index = 0;
            }else if (current_frame_index < 0) {
                current_frame_index = 4;
            }
            sprite.setTexture(textures.get(current_frame_index));
        }

        
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

        float old_angle = direction;

        float angleDiff = this.direction + (diff * this.manoeuverability_stat / 50f); 
        if (angleDiff >= 360) {
            angleDiff -= 360;
        }else if (angleDiff < 0) {
            angleDiff += 360;
        }
        this.direction = angleDiff;
        this.sprite.setRotation(this.direction);
        this.hitbox.setRotation(this.direction);

        // check if rotation resulted in a collision
        if (isCollided(parent.getEntities())) {
            this.direction = old_angle;
        }

    }

    private Boolean isZeroHP() {
        return current_health > 0;
    }

    private void getTired() {
        tiredness_factor = tiredness_factor - (0.005f * tiredness_factor * Gdx.graphics.getDeltaTime());
    }

    private Boolean checkInLane() {
        return (pos_x < (lane_number + 1) * LANE_WIDTH) && (pos_x > lane_number * LANE_WIDTH);
    }
    

    @Override
    public void applyCollision(Entity other) {
        
        double rad_angle = (180 + this.direction) * Math.PI / 180;

        double delta_y = Math.cos(rad_angle) * velocity * Gdx.graphics.getDeltaTime() * VELOCITY_CONSTANT;
        double delta_x = -Math.sin(rad_angle) * velocity * Gdx.graphics.getDeltaTime() * VELOCITY_CONSTANT;

        pos_x += delta_x;
        pos_y += delta_y;

        this.sprite.translate((float) delta_x, (float) delta_y);
        this.hitbox.translate((float) delta_x, (float) delta_y);


        // reduce velocity to 0
        velocity = 0;

        // reduce health according to robustness
        // TODO: balance damage calculation

        if (System.currentTimeMillis() - time_of_last_collision > 100) {
            current_health -= robustness_stat / 10f;
            time_of_last_collision = System.currentTimeMillis();
        }
        if (current_health < 0) {
            current_health = 0;
        }
        
        

        
    }

    @Override
    public void remove() {
        //TODO
    }


}