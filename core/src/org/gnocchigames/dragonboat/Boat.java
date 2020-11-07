package org.gnocchigames.dragonboat;

import java.util.List;

import com.badlogic.gdx.Gdx;
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
        this.sprite.setOrigin(this.sprite.getWidth()/2, this.sprite.getHeight()/2);
        this.pos_x = 0;
        this.pos_y = 0;
        this.direction = 0;
        this.current_health = 100;
        this.current_penalty = 0;
        this.velocity = 1;
    }

    public void update() {
        /**
         * Update the boat's properties and stats
         */

        double rad_angle = this.direction * (Math.PI / 180);

        double delta_y = Math.cos(rad_angle) * velocity;
        double delta_x = -Math.sin(rad_angle) * velocity;

        this.pos_x += delta_x;
        this.pos_y += delta_y;

        this.sprite.setPosition(this.pos_x, this.pos_y);
        

    }

    public void accelerate() {
        //TODO: actually implement this
        this.changeSpeed(1);
    }

    public void deccelerate() {
        //TODO: actually implement this
        this.changeSpeed(-1);
    }

    private void changeSpeed(int value) {
        //TODO: actually implement this
        this.velocity += value * Gdx.graphics.getDeltaTime();
    }

    public void turnLeft() {
        changeDirection(1);
    }

    public void turnRight() {
        changeDirection(-1);
    }

    private void changeDirection(int diff) {
        this.direction = Math.floorMod(this.direction + diff, 360);
        
        System.out.println("Current direction is: " + this.direction);
        System.out.println("Current sprite rotation is: " + this.sprite.getRotation());
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
            this.sprite.setRotation(this.direction);
            this.sprite.draw(batch);

        }

    }

}