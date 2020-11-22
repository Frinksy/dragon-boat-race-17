package org.gnocchigames.dragonboat.entities;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import org.gnocchigames.dragonboat.DragonBoatGame;
import org.gnocchigames.dragonboat.screens.RaceLegScreen;

/**
 * Duck
 */
public class Duck extends Obstacle {

    public static enum DuckDirection {
        RIGHT, LEFT
    }

    private int speed;
    private DuckDirection direction;
    private int lane;


    
    /**
     * Creates a Duck with the specified coordinates, speed and direction
     * @param parent the parent screen
     * @param x
     * @param y
     * @param speed
     * @param direction
     * @param lane
     */
    public Duck (DragonBoatGame game, RaceLegScreen parent, int x, int y, int speed, DuckDirection direction, int lane) {
        
        this.game = game;
        this.parent = parent;
        this.lane = lane;
        
        sprite = new Sprite(parent.game.texture_store.map.get("duck.png"));
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
        sprite.scale(-0.9f);
        if (direction == DuckDirection.LEFT) {
            sprite.flip(true, false);
        }
        pos_x = x;
        pos_y = y;
        
        this.direction = direction;
        this.speed = 40;
        sprite.setCenter(pos_x, pos_y);
        
        hitbox = getBoundingPolygon();
    }
    
    /**
     * Creates a Duck with the specified coordinates, speed and direction
     * @param parent the parent screne
     * @param x
     * @param y
     * @param speed
     * @param direction
     */
    public Duck (DragonBoatGame game, RaceLegScreen parent, int x, int y, int speed, DuckDirection direction) {
        this(game, parent, x, y, speed, direction, -1);
        
    }

    /**
     * Creates a Duck with the specified coordinates and speed
     * @param parent the parent screen
     * @param x
     * @param y
     * @param speed
     */
    public Duck (DragonBoatGame game, RaceLegScreen parent, int x, int y, int speed) {
        this(game, parent, x, y, speed, DuckDirection.RIGHT);
    }

    /**
     * Creates a Duck with the specified coordinates
     * @param parent the parent screen
     * @param x
     * @param y
     */
    public Duck(DragonBoatGame game, RaceLegScreen parent, int x, int y) {
        this(game, parent, x, y, 20);
    }

    /**
     * Updates the duck, moves it
     * @param delta_time the time since the last frame
     * @param entities the list of entities in the game
     */
    @Override
    public void update(float delta_time, List<Entity> entities) {

        // Move the duck

        float delta_x = 0;

        switch (direction) {
            case RIGHT:
                delta_x = speed * delta_time;
                break;
            case LEFT:
                delta_x -= speed * delta_time;
                break;
        }

        pos_x += delta_x;

        // Update sprite and hitbox position
        sprite.translateX(delta_x);
        hitbox.translate(delta_x, 0);

        if (hasHitWall()) {
            changeDirection();
        }

        // Collision
        // if (isCollided(entities)) {
        //     System.out.println("Collided");
        //     applyCollision(null);
        // }

    }

    /**
     * Checks if Duck has reached either side of the screen
     * @return true if the Duck has reached either side of the screen, else false
     */
    private Boolean hasHitWall() {
        if (lane < 0 || lane > 4) {
        return pos_x <= 0 || pos_x > 1920;
        }
        return pos_x <= 384*lane || pos_x >= 384*(lane+1);
    }

    /**
     * Flips the Duck and changes its direction
     */
    private void changeDirection() {
        
        // if not assigned to a lane
        if (lane < 0 || lane > 4) {
            if (direction == DuckDirection.LEFT && pos_x < 0) {
                direction = DuckDirection.RIGHT;
                sprite.flip(true, false);
            } else if (direction == DuckDirection.RIGHT && pos_x > 1920) {
                direction = DuckDirection.LEFT;
                sprite.flip(true, false);
            }
        }
        // if assigned to a lane
        else {
            if (direction == DuckDirection.LEFT && pos_x < lane * 384) {
                direction = DuckDirection.RIGHT;
                sprite.flip(true, false);
            } else if (direction == DuckDirection.RIGHT && pos_x > (lane+1) * 384) {
                direction = DuckDirection.LEFT;
                sprite.flip(true, false);
            }
        }
        

    }

    @Override
    public void applyCollision(Entity other) {
        if (other instanceof Boat) {
            this.remove();
        }
    }

    
}