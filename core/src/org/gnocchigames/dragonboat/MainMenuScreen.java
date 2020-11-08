package org.gnocchigames.dragonboat;

import com.badlogic.gdx.ScreenAdapter;

/** Main menu screen
 * MainMenuScreen is instantiated by DragonBoatGame
 */
public class MainMenuScreen extends ScreenAdapter{

    private DragonBoatGame game;

    public MainMenuScreen(DragonBoatGame game) {
        this.game = game;
    }


    @Override
    public void show() {
        // Gets called when the screen pops up
    }

    @Override
    public void render(float delta_time) {
        // Gets called every frame

    }

    @Override
    public void hide() {
        // Gets called when game switches to another screen
        // We can probably do without this, only using dispose()
    }

    @Override
    public void dispose() {
        // Gets called when the screen is destroyed
        // to release all objects
    }

}
