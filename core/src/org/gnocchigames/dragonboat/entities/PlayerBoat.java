package org.gnocchigames.dragonboat.entities;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import org.gnocchigames.dragonboat.DragonBoatGame;
import org.gnocchigames.dragonboat.screens.RaceLegScreen;

public class PlayerBoat extends Boat{
    
    public PlayerBoat(DragonBoatGame game, RaceLegScreen parent, Boat.Boat_Type type) {
        super(game, parent, type, 2);
    }

    /**
     * Get keyboard input and translate them into
     * function calls for player movement.
     * Is called in update() method
     */
    public void handleKeys() {

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.turnLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.turnRight();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.accelerate();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.decelerate();
        }


    }


    /**
     * Update the state of the boat.
     * Should be called every frame.
     */
    @Override
    public void update(float delta_time, List<Entity> entities) {
        
        super.update(delta_time, entities);

        handleKeys();
    }

    @Override
    public String getName() {
        return "Player 1";
    }
}
