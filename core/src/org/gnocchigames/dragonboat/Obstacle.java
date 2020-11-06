package org.gnocchigames.dragonboat;

import java.util.List;

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
    
}