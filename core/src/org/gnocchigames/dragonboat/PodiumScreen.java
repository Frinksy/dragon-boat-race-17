package org.gnocchigames.dragonboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PodiumScreen extends ScreenAdapter{

    private DragonBoatGame parent;
    private Stage stage;

    public PodiumScreen(DragonBoatGame game) {

        parent = game;        
    }

    @Override
    public void show(){
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
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
        stage.dispose();
    }
}