package org.gnocchigames.dragonboat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import org.gnocchigames.dragonboat.DragonBoatGame;
import org.gnocchigames.dragonboat.screens.RaceLegScreen;

/**
 * Rock
 */
public class Rock extends Obstacle {

    /**
     * Creates a Rock at the specified location
     * @param parent
     * @param x
     * @param y
     */
    public Rock(DragonBoatGame game, RaceLegScreen parent, int x, int y) {
        
        this.game = game;
        this.parent = parent;
        this.pos_x = x;
        this.pos_y = y;
        
        // Set up sprite
        String[] filenames = {"rock/rock0.png", "rock/rock1.png", "rock/rock2.png", "rock/rock3.png"};
        int index = (int)(Math.random() * filenames.length);
        Texture texture = parent.game.texture_store.map.get(filenames[index]);

        this.sprite = new Sprite(texture);
        this.sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
        this.sprite.scale(-0.75f);
        this.sprite.setCenter(pos_x, pos_y);

        this.hitbox = getBoundingPolygon();

    }

    @Override
    public void applyCollision(Entity other) {
        // Do nothing
    }
    
}
