package org.gnocchigames.dragonboat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import org.gnocchigames.dragonboat.DragonBoatGame;
import org.gnocchigames.dragonboat.screens.RaceLegScreen;

/**
 * TreeLog obstacle
 */
public class TreeLog extends Obstacle {

    private Texture texture;
    
    /**
     * Create a TreeLog at the desired position
     * @param game
     * @param parent
     * @param x
     * @param y
     */
    public TreeLog(DragonBoatGame game, RaceLegScreen parent, int x, int y) {
        this.game = game;
        this.parent = parent;
        this.pos_x = x;
        this.pos_y = y;

        // Set up sprite
        this.texture = parent.game.texture_store.map.get("tree_log.png");
        this.sprite = new Sprite(texture);
        this.sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
        this.sprite.scale(-0.75f);
        this.sprite.setCenter(pos_x, pos_y);
        this.hitbox = getBoundingPolygon();
    }

    @Override
    public void applyCollision (Entity other) {
        if (other instanceof Boat) {
            remove();
        }
        remove();
    }

}
