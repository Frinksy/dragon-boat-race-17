package org.gnocchigames.dragonboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ControlsScreen extends ScreenAdapter{

    private DragonBoatGame parent;
    private Stage stage;

    public ControlsScreen(DragonBoatGame game) {
        parent = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta_time){

    }

    @Override
    public void resize(int width, int height){

    }

    @Override
    public void hide(){

    }

    @Override
    public void dispose(){

    }
}