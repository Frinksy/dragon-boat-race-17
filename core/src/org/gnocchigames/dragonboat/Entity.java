package org.gnocchigames.dragonboat;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

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
    public Polygon hitbox;
    
    public RaceLegScreen parent;

    /**
     * Checks if the entity has collided with other entities
     * @param entities the list of entities to check against
     * @return true if in collision, false otherwise
     */
    public  Boolean isCollided(List<Entity> entities) {
        for (Entity entity : entities) {
            if (Intersector.intersectPolygons(entity.hitbox, hitbox, null) || Intersector.intersectPolygons(hitbox, entity.hitbox, null)) {
                if (!entity.equals(this)) {
                    return true;
                }
            }
        }
        return false;
    };

    public abstract void applyCollision(Entity other);

    public abstract void remove();
    
    public abstract void update(float delta_time, List<Entity> entities);

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

    /**
     * Get the bounding polygon, around the entity
     * By default it is a rectangle encapsulating the sprite
     * Is implemented more specifically for inheriting classes
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