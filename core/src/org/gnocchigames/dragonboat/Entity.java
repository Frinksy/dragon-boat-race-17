package org.gnocchigames.dragonboat;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.gnocchigames.dragonboat.exceptions.IsNotDrawingException;
/**
 * Entity
 * Base class for all entities in the game
 */
public abstract class Entity {

    public float pos_x;
    public float pos_y;
    public float height;
    public float width;
    public Sprite sprite;
    public float velocity;


    public abstract Boolean isCollided(List<Entity> entities);

    public abstract void applyCollision(Entity other);

    public abstract void remove();

    /**
     * Add the sprite to the drawing queue (SpriteBatch)
     * Should be called every frame
     * @param batch the SpriteBatch to draw to
     */
    public void draw(SpriteBatch batch) throws IsNotDrawingException {
        if (! batch.isDrawing()) {
            throw new IsNotDrawingException("SpriteBatch is not currently between begin and end!");
        }else {
            this.sprite.draw(batch);
        }
    };

    public abstract void update(float delta_time);
}