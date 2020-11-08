package org.gnocchigames.dragonboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerBoat extends Boat{
    
    public PlayerBoat() {
        super();
    }

    /**
     * Get keyboard input and translate them into
     * function calls for player movement.
     * Is called in update() method
     */
    public void handleKeys() {

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.turnLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.turnRight();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.accelerate();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.decelerate();
        }


    }


    /**
     * Update the state of the boat.
     * Should be called every frame.
     */
    public void update() {
        
        super.update();

        handleKeys();
    }
}
