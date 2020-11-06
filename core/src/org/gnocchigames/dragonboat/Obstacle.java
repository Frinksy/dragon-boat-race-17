package org.gnocchigames.dragonboat;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.gnocchigames.dragonboat.exceptions.IsNotDrawingException;

/**
 * Obstacle
 */
public class Obstacle extends Entity {

    public int health_damage;

    public int speed_damage;


    public Boolean isCollided(List<Entity> entities) {
        //TODO
        return false;
    }

    public void applyCollision(Entity other) {
        //TODO
    }

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

    @Override
    public void update() {
        //TODO
    }
}