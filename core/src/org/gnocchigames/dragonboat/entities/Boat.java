package org.gnocchigames.dragonboat.entities;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import org.gnocchigames.dragonboat.DragonBoatGame;
import org.gnocchigames.dragonboat.screens.RaceLegScreen;


/**
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
    public Color colour;
    
    public int lane_number;
    
    private Boat_Type type;
    
    private Boolean in_lane;
    public long time_out_of_lane;
    private long last_out_of_lane_time;
    
    public long start_time;
    public long end_time;
    public Boolean done;

    private long time_of_last_collision;
    private List<Texture> textures;

    private long last_frame_time;
    private int current_frame_index;

    private static final float MAX_BACKWARDS_SPEED = -20;
    private static final float MIN_TIRED_SPEED = 20;
    private static final float MIN_TIREDNESS_FACTOR = 0.1f;

    private static final float VELOCITY_CONSTANT = 4;

    private static final int LANE_WIDTH = 1920 / 5;

    /**
     * Creates a boat of specified type and in the specified assigned lane
     * @param parent the parent screen
     * @param type the boat type
     * @param lane the boat's lane
     */
    public Boat(DragonBoatGame game, RaceLegScreen parent, Boat_Type type, int lane) {

        this.game = game;
        this.parent = parent;
        this.type = type;
        
        // Set up sprite
        this.textures = getTextures(type);
        this.sprite = new Sprite(textures.get(0));
        this.sprite.setOrigin(this.sprite.getWidth()/2, this.sprite.getHeight()/2);
        this.sprite.scale(-0.50f);
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
        this.velocity = 1;
        this.time_out_of_lane = 0;
        this.last_out_of_lane_time = 0;
        this.start_time = 0;
        this.end_time = 0;
        this.done = false;
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
     * @param type the boat's type
     */
    public Boat(DragonBoatGame game, RaceLegScreen parent,  Boat_Type type) {
        this(game, parent, type, 0);

    }

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

        // float [] vertices = {
        //     100, 200,
        //     136, 136,
        //     136, 10,
        //     127, 0,
        //     72, 0,
        //     64, 10,
        //     64, 136
        // };

        float [] vertices = {
            60, 0,
            47, 13,
            47, 204,
            102, 300,
            156, 204,
            156, 13,
            144, 0
        };

        Polygon output = new Polygon(vertices);
        output.scale(-0.50f);
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
                this.acceleration_stat = 35;
                this.speed_stat = 100;
                this.manoeuverability_stat = 40;
                this.robustness_stat = 80;
                this.colour = new Color(Color.PINK);
                break;
            case HARD:
                this.acceleration_stat = 60;
                this.speed_stat = 85;
                this.manoeuverability_stat = 40;
                this.robustness_stat = 100;
                this.colour = new Color(Color.GREEN);
                break;
            case ACCEL:
                this.acceleration_stat = 100;
                this.speed_stat = 90;
                this.manoeuverability_stat = 70;
                this.robustness_stat = 95;
                this.colour = new Color(Color.CYAN);
                break;
            case MANOEUVREABLE:
                this.acceleration_stat = 60;
                this.speed_stat = 90;
                this.manoeuverability_stat = 100;
                this.robustness_stat = 90;
                this.colour = new Color(Color.YELLOW);
                break;
            default:
                this.acceleration_stat = 85;
                this.speed_stat = 85;
                this.manoeuverability_stat = 85;
                this.robustness_stat = 95;
                this.colour = new Color(Color.BROWN);
                break;
        }
    }

    /**
     * Get the texture list for the boat
     * @param type the type of the boat
     * @return a list of ordered textures for the boat's animation
     */
    private List<Texture> getTextures(Boat_Type type) {
        String folder = new String();
        switch (type) {
            case FAST:
                folder = "pink";
                break;
            case HARD:
                folder = "green";
                break;
            case ACCEL:
                folder = "blue";
                break;
            case MANOEUVREABLE:
                folder = "yellow";
                break;    
            default:
                folder = "brown";
                break;
        }


        List<Texture> output = new ArrayList<Texture>();
        for (int i = 0; i < 5; i++) {
            output.add(
                //new Texture("boats/" + folder + "/frame-" + i + ".png")
                game.texture_store.map.get("boats/" + folder + "/frame-" + i + ".png")         
                //game.texture_store.map.get("duck.png")
                );
        }

        return output;

    }
    
    /**
     * Update the boat's properties and stats <br>
     * Should be called every frame
     * @param delta_time the time since the last update
     * @param entities the list of entities to check against
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

        // Check for in lane
        if (checkInLane() && !in_lane) {
            in_lane = true;
            time_out_of_lane += System.currentTimeMillis() - last_out_of_lane_time;
        }else if (!checkInLane() && in_lane) {
            in_lane = false;
            last_out_of_lane_time = System.currentTimeMillis();
        }

        
    }


    /**
     * Make the boat go faster.
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
     * Private convenience function to apply the turning of the boat. 
     * Called by turnLeft() and turnRight();
     * @param diff the angle difference in degrees
     */
    private void changeDirection(int diff) {

        float old_angle = direction;

        float angleDiff = this.direction + (diff * this.manoeuverability_stat / 50f); 
        if (angleDiff >= 360) {
            //angleDiff -= 360;
        }else if (angleDiff < 0) {
            //angleDiff += 360;
        }
        this.direction = angleDiff;
        this.sprite.setRotation(this.direction);
        this.hitbox.setRotation(this.direction);

        // check if rotation resulted in a collision
        if (isCollided(parent.getCollidableEntities())) {
            this.direction = old_angle;
        }

    }
    /**
     * Check if the boat is alive
     * @return true if above zero health, false otherwise
     */
    public Boolean isAlive() {
        return current_health > 0;
    }

    /**
     * Erodes the tiredness factor
     */
    private void getTired() {
        tiredness_factor = tiredness_factor - (0.005f * tiredness_factor * Gdx.graphics.getDeltaTime());
    }

    /**
     * Check if the boat is in its lane
     * @return true if the boat is in its lane, false otherwise
     */
    public Boolean checkInLane() {
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
            float damage = ((110-robustness_stat)/2f);
            current_health -= damage;
            time_of_last_collision = System.currentTimeMillis();
        }
        if (current_health < 0) {
            current_health = 0;
        }
        
    }
    /**
     * Start the race timer
     */
    public void startTimer() {
        start_time = System.currentTimeMillis();
    }

    /**
     * Stop the race timer
     * @return
     */
    public long stopTimer() {
        if (!done) {

            
            end_time = System.currentTimeMillis();
            if (!in_lane) {
                end_time += System.currentTimeMillis() - last_out_of_lane_time;
            }
            end_time += time_out_of_lane;

            done = true;

        }

        return end_time;
    }

    /**
     * Get the boat's time in the current leg. 
     * Should be called only when the leg is over
     * @return the boat's time in the leg
     */
    public long getLegTime() {
        return end_time - start_time;
    }

    /**
     * Get the current time of the boat in the race
     * @return the time of the boat in the race
     */
    public long getCurrentTime() {
        return System.currentTimeMillis() - start_time;
    }

    /**
     * Get the formatted string of the boat's current time
     * @return a formatted string of the boat's current time
     */
    public String getFormattedCurrentTime() {
        Duration duration = Duration.ofMillis(getCurrentTime());

        String output = String.format("%02d:%02d.%03d",
        duration.toMinutes(),
        duration.toMillis()%60000 / 1000,
        duration.toMillis()%1000);
        return output;
    }

    /**
     * Get the formatted string of the boat's current penalty
     * @return a formatted string of the boat's current penalty
     */
    public String getFormattedCurrentPenalty() {
        long penalty_time = time_out_of_lane;
        if (!checkInLane()) {
            penalty_time += System.currentTimeMillis() - last_out_of_lane_time;
        }
        
        Duration duration = Duration.ofMillis(penalty_time);
        String output = String.format("+%02d:%02d.%03d",
            duration.toMinutes(),
            duration.toMillis()%60000 / 1000,
            duration.toMillis()%1000
        );

        return output;
    }

    /**
     * Get the boat's angle
     * @return the boat's angle (0-360)
     */
    public double getDirection(){
        return direction;
    }

    /**
     * Get the boat's x position
     * @return the boat's x position
     */
    public float getX(){
        return pos_x;
    }

    /**
     * Get the boat's y position
     * @return the boat's y position
     */
    public float getY(){
        return pos_x;
    }

    /**
     * Get the name of the boat
     * @return the name of the boat
     */
    public String getName() {
        String name = new String();
        switch (type) {
            case FAST:
                name = "Speedy";
                break;
            case HARD:
                name = "Robusty";
                break;
            case ACCEL:
                name = "Acceleraty";
                break;
            case MANOEUVREABLE:
                name = "Manoeuvrabley";
                break;
            default:
                name = "Defaulty";
                break;
        }

        return name;
    }

    /**
     * Get a Label containing the boat's name
     * @param skin the skin to apply to the label
     * @return a label containing the boat's name
     */
    public Label getNameLabel(Skin skin) {

        Label output = new Label(getName(), skin);
        output.setColor(colour);
        return output;

    }

    @Override
    public void remove() {
        for (Texture texture : textures) {
            texture.dispose();
        }
    }
}