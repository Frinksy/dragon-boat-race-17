package org.gnocchigames.dragonboat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import org.gnocchigames.dragonboat.screens.RaceLegScreen;

public class TreeLog extends Obstacle {

    private Texture texture;
    
    public TreeLog(RaceLegScreen parent, int x, int y) {
        this.parent = parent;
        this.pos_x = x;
        this.pos_y = y;

        // Set up sprite
        this.texture = new Texture("tree_log.png");
        this.sprite = new Sprite(texture);
        this.sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
        this.sprite.scale(-0.75f);
        this.sprite.setCenter(pos_x, pos_y);
        this.hitbox = getBoundingPolygon();
    }

    public void applyCollision (Entity other) {
        if (other instanceof Boat) {
            remove();
        }
        remove();
    }

    @Override
    public void remove() {
        texture.dispose();
        super.remove();
    }

}
