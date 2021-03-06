package org.gnocchigames.dragonboat.util;

import com.badlogic.gdx.graphics.OrthographicCamera;

import org.gnocchigames.dragonboat.entities.Boat;

/**
 * GameCamera
 */
public class GameCamera extends OrthographicCamera{
    
    public float current_y;

    /**
     * Create a GameCamera instance.
     */
    public GameCamera () {
        super();
        current_y = 0;
    }

    @Override
    public void translate(float x, float y) {
        super.translate(x, y);
        this.current_y += y;
    }

    /**
     * Move the camera to follow the boat
     * @param boat the boat to follow
     */
    public void followBoat(Boat boat) {
        if (boat.pos_y < current_y + viewportHeight*0.1f) {
            translate(0, boat.pos_y - (current_y + viewportHeight*0.1f));
        }else if (boat.pos_y + boat.sprite.getHeight() > current_y + viewportHeight * 0.40f) {
            translate(0, (boat.pos_y + boat.sprite.getHeight()) - (current_y + viewportHeight * 0.40f));
        }
    }

}
