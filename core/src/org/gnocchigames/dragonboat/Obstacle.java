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

    private RaceLegScreen screen;


    public void applyCollision(Entity other) {
        this.remove();
    }

    public void remove() {
        screen.removeEntity(this);
    }
    
    
    @Override
    public void update(float delta_time, List<Entity> entities) {
        //TODO
    }
}