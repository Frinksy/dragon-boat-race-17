package org.gnocchigames.dragonboat;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.gnocchigames.dragonboat.exceptions.IsNotDrawingException;

/**
 * Boat
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

    public Boat() {
        Texture boat_texture = new Texture("boat.png"); // TODO: Do not hardcode file name
        this.sprite = new Sprite(boat_texture);
        this.pos_x = 0;
        this.pos_y = 0;
        this.direction = 90;
        this.current_health = 100;
        this.current_penalty = 0;
        this.velocity = 1;
    }

    public void update() {
        /**
         * Update the boat's properties and stats
         */

        double rad_angle = this.direction * (Math.PI / 180);

        double delta_x = Math.cos(rad_angle) * velocity;
        double delta_y = Math.sin(rad_angle) * velocity;

        this.pos_x += delta_x;
        this.pos_y += delta_y;

        this.sprite.setPosition(this.pos_x, this.pos_y);
        

    }

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


    @Override
    public void draw(SpriteBatch batch) throws IsNotDrawingException {
        /**
         * 
         */
        if (! batch.isDrawing()) {
            throw new IsNotDrawingException("SpriteBatch is not currently between begin and end!");
        }else {
            batch.draw(this.sprite, this.pos_x, this.pos_y);
        }

    }

}