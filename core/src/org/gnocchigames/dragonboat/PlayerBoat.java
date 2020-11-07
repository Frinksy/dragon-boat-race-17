package org.gnocchigames.dragonboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerBoat extends Boat{
    
    public PlayerBoat() {
        super();
    }

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
            this.deccelerate();
        }


    }


    public void update() {
        super.update();

        handleKeys();
    }
}
