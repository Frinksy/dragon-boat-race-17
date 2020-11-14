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
    public void update(float delta_time, List<Entity> entities) {
        //TODO
    }
}