package org.gnocchigames.dragonboat;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.gnocchigames.dragonboat.exceptions.IsNotDrawingException;

/**
 * Boat
 * Class which implements a boat
 * It is not designed to be used directly,
 * instead please use PlayerBoat or AIBoat
 */
public class Boat extends Entity{

    public int speed_stat;
    public int acceleration_stat;
    public int manoeuverability_stat;
    public int robustness_stat;
    public float tiredness_factor;
    public int direction;
    public int current_health;
    public float current_penalty;

    private Boolean in_lane;

    // TODO: modify constructor 
    //       to use different stats presets
    //       (potentially use an Enum)

    public Boat() {
        Texture boat_texture = new Texture("boat.png"); // TODO: Do not hardcode file name
        this.sprite = new Sprite(boat_texture);
        this.sprite.setOrigin(this.sprite.getWidth()/2, this.sprite.getHeight()/2);
        this.pos_x = 0;
        this.pos_y = 0;
        this.direction = 0;
        this.current_health = 100;
        this.current_penalty = 0;
        this.velocity = 1;
    }
    
    /**
     * Update the boat's properties and stats
     * Should be called every frame
     */
    public void update() {

        double rad_angle = this.direction * (Math.PI / 180);

        double delta_y = Math.cos(rad_angle) * velocity;
        double delta_x = -Math.sin(rad_angle) * velocity;

        this.pos_x += delta_x;
        this.pos_y += delta_y;

        this.sprite.setPosition(this.pos_x, this.pos_y);
        
    }


    /**
     * Make the boat go faster
     * Changes speed based off of the boat's current stats
     */
    public void accelerate() {
        //TODO: actually implement this
        this.changeSpeed(1);
    }

    /**
     * Slow the boat down
     */
    public void decelerate() {
        //TODO: actually implement this
        this.changeSpeed(-1);
    }

    /**
     * Private convenience function to apply the change in speed
     * in speed from accellerate() and decelerate()
     * @param value the acceleration value to be applied (units.s^-2)
     */
    private void changeSpeed(int value) {
        //TODO: actually implement this
        this.velocity += value * Gdx.graphics.getDeltaTime();
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
        this.direction = Math.floorMod(this.direction + diff, 360);
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
    
    @Override
    public  Boolean isCollided(List<Entity> entities){
        //TODO
        return false;

    }

    @Override
    public void applyCollision(Entity other) {
        //TODO
    }

    @Override
    public void remove() {
        //TODO
    }


    /**
     * Add the sprite to the drawing queue (SpriteBatch)
     * Should be called every frame
     * @param batch the SpriteBatch to draw to
     */
    @Override
    public void draw(SpriteBatch batch) throws IsNotDrawingException {
        if (! batch.isDrawing()) {
            throw new IsNotDrawingException("SpriteBatch is not currently between begin and end!");
        }else {
            this.sprite.setRotation(this.direction);
            this.sprite.draw(batch);

        }

    }

}