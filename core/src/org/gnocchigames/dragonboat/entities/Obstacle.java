package org.gnocchigames.dragonboat.entities;

import java.util.List;

/**
 * Base class for all obstacles
 */
public abstract class Obstacle extends Entity {

    public int health_damage;

    public int speed_damage;

    @Override
    public abstract void applyCollision(Entity other);

    @Override
    public void remove() {
        parent.removeEntity(this);
    }
    
    
    @Override
    public void update(float delta_time, List<Entity> entities) {
        //TODO
    }
}