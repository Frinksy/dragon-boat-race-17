package org.gnocchigames.dragonboat.entities;

import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

import org.gnocchigames.dragonboat.DragonBoatGame;
import org.gnocchigames.dragonboat.exceptions.IsNotDrawingException;
import org.gnocchigames.dragonboat.screens.RaceLegScreen;

/**
 * Base class for all entities in the game
 */
public abstract class Entity {

    public float pos_x;
    public float pos_y;
    public float height;
    public float width;
    public Sprite sprite;
    public float velocity;
    public Polygon hitbox;

    public RaceLegScreen parent;
    public DragonBoatGame game;

    /**
     * Checks if the entity has collided with other entities
     * @param entities the list of entities to check against
     * @return true if in collision, false otherwise
     */
    public Boolean isCollided(List<Entity> entities) {
        for (Entity entity : entities) {
            if (Intersector.intersectPolygons(entity.hitbox, hitbox, null) || Intersector.intersectPolygons(hitbox, entity.hitbox, null)) {
                if (!entity.equals(this)) {
                    return true;
                }
            }
        }
        return false;
    };

    /**
     * Check if the entity has collided with other entities, 
     * and with which one.
     * @param entities the entities to check against
     * @return (true, entity) if in collision with entity, (false, null) otherwise
     */
    public SimpleEntry<Boolean, Entity> isCollidedWith(List<Entity> entities) {
        for (Entity entity : entities) {
            if (Intersector.intersectPolygons(entity.hitbox, hitbox, null) || Intersector.intersectPolygons(hitbox, entity.hitbox, null)) {
                if (!entity.equals(this)) {
                    return new SimpleEntry<Boolean, Entity>(true, entity);
                }
            }
        }
        return new SimpleEntry<Boolean, Entity>(false, null);
    }

    /**
     * Apply the collision effects
     * @param other the entity which has been collided with
     */
    public abstract void applyCollision(Entity other);

    /**
     * Remove the entity
     */
    public abstract void remove();
    
    /**
     * Update the entity's state
     * @param delta_time the time since the last update
     * @param entities the entities to update against
     */
    public abstract void update(float delta_time, List<Entity> entities);

    /**
     * Add the sprite to the drawing queue (SpriteBatch) <br>
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

    /**
     * Get the bounding polygon, around the entity <br>
     * By default it is a rectangle encapsulating the sprite <br>
     * Is implemented more specifically for inheriting classes <br>
     * @return the bounding polygon of the
     */
    public Polygon getBoundingPolygon() {

        Rectangle rect = this.sprite.getBoundingRectangle();

        float [] vertices = {
            rect.x, rect.y,
            rect.x + rect.width, rect.y,
            rect.x + rect.width, rect.y + rect.height,
            rect.x, rect.y + rect.height
        };

        Polygon output = new Polygon(vertices);
        return output;

    }
    


}