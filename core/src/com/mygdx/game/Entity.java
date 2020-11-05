package com.mygdx.game;

import java.util.List;

/**
 * Entity
 */
public abstract class Entity {

    public float pos_x;
    public float pos_y;
    public float height;
    public float width;
    public Image sprite;
    public float velocity_x;
    public float velocity_y;


    public abstract Boolean isCollided(List<Entity> entities);

    public abstract void applyCollision(Entity other);

    public abstract void remove();
}