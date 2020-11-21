package org.gnocchigames.dragonboat.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import org.gnocchigames.dragonboat.DragonBoatGame;

public class NextLegScreen extends ScreenAdapter{

    private DragonBoatGame parent;
    private Stage stage;

    public NextLegScreen(DragonBoatGame game) {
        parent = game;
    }

    @Override
    public void show(){
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        Skin skin = new Skin(Gdx.files.internal("clean-crispy/clean-crispy-ui.json"));

        final TextButton next = new TextButton("Next Leg", skin);
        next.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                parent.changeScreen(DragonBoatGame.RACE_LEG);
            }
        });

        table.add(next);

    }

    @Override
    public void render(float delta_time){
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
        stage.draw();

    }

    @Override
    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide(){

    }

    @Override
    public void dispose(){
        stage.dispose();
    }
}

    