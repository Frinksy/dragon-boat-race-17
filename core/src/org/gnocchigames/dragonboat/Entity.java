package org.gnocchigames.dragonboat;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.gnocchigames.dragonboat.exceptions.IsNotDrawingException;
/**
 * Entity
 */
public abstract class Entity {

    public float pos_x;
    public float pos_y;
    public float height;
    public float width;
    public Sprite sprite;
    public float velocity_x;
    public float velocity_y;


    public abstract Boolean isCollided(List<Entity> entities);

    public abstract void applyCollision(Entity other);

    public abstract void remove();

    public abstract void draw(SpriteBatch batch) throws IsNotDrawingException;
}