package org.gnocchigames.dragonboat;

import java.util.List;


/**
 * Obstacle
 */
public class Obstacle extends Entity {

    public int health_damage;

    public int speed_damage;


    public void applyCollision(Entity other) {
        this.remove();
    }

    public void remove() {
        parent.removeEntity(this);
    }
    
    
    @Override
    public void update(float delta_time, List<Entity> entities) {
        //TODO
    }
}