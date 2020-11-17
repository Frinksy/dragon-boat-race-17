package org.gnocchigames.dragonboat;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Duck
 */
public class Duck extends Obstacle {

    public static enum DuckDirection {
        RIGHT, LEFT
    }

    private int speed;
    private DuckDirection direction;



    /**
     * Creates a Duck with the specified coordinates, speed and direction
     * @param x
     * @param y
     * @param speed
     * @param direction
     */
    public Duck (int x, int y, int speed, DuckDirection direction) {
        sprite = new Sprite(new Texture("duck.png"));
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
        sprite.scale(-0.9f);
        pos_x = x;
        pos_y = y;

        this.direction = direction;
        this.speed = 40;
        sprite.setCenter(pos_x, pos_y);

        hitbox = getBoundingPolygon();
    }
    
    /**
     * Creates a Duck with the specified coordinates and speed
     * @param x
     * @param y
     * @param speed
     */
    public Duck (int x, int y, int speed) {
        this(x, y, speed, DuckDirection.RIGHT);
    }

    /**
     * Creates a Duck with the specified coordinates
     * @param x
     * @param y
     */
    public Duck(int x, int y) {
        this(x, y, 20);
    }

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

    }

    private Boolean hasHitWall() {
        return pos_x <= 0 || pos_x > 1920;
    }

    private void changeDirection() {
        
        if (direction == DuckDirection.RIGHT && pos_x > 1920) {
            direction = DuckDirection.LEFT;
        }else if (direction == DuckDirection.LEFT && pos_x < 0) {
            direction = DuckDirection.RIGHT;
        }

        // flip the sprite
        sprite.flip(true, false);

    }

    
}