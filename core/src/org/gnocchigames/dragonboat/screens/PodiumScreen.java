package org.gnocchigames.dragonboat.screens;

import java.util.List;

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

import org.gnocchigames.dragonboat.DragonBoatGame;

import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * PodiumScreen
 */
public class PodiumScreen extends ScreenAdapter{

    private DragonBoatGame parent;
    private Stage stage;

    private Label title;
    private Label first;
    private Label second;
    private Label third;
    private Label spacer;

    /**
     * Create a PodiumScreen instance
     * @param game the parent DragonBoatGame instance
     */
    public PodiumScreen(DragonBoatGame game) {

        parent = game;        
    }

    @Override
    public void show(){

        // Gets called when the screen pops up
        //constructor creates a new stage 
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skin = parent.skin_store.get("clean-crispy/clean-crispy-ui.json");

        // creates labels, images and buttons actors to be used in the stage

        Texture podium_texture = parent.texture_store.map.get("podium.png");
        Image podium = new Image(podium_texture);
        //podium.setOrigin(1);
        //stage.addActor(podium);

        // buttons and listeners
        final TextButton go_again = new TextButton("Play Again", skin);
        go_again.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                parent.score_board.resetAll();
                parent.changeScreen(DragonBoatGame.MENU);
            }
        });

        final TextButton exit = new TextButton("Exit", skin);
        exit.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                Gdx.app.exit();
            }
        });

        // text labels
        title = new Label("RESULTS", skin);
        title.setFontScale(1.25f);

        List<String> names = parent.score_board.getPodiumNames();

        first = new Label(names.get(0), skin);
        if (names.size()>1){
            second = new Label(names.get(1), skin);
        }else {
            second = new Label("", skin);
        }
        if (names.size()>2) {
            third = new Label(names.get(2), skin);
        }else {
            third = new Label("", skin);
        }
        spacer = new Label("", skin);

        // table layout
        table.add(title).colspan(3);
        table.row().pad(20, 0, 0, 0);
        table.add(first).colspan(3);
        table.row().pad(20, 75, 0, 50);
        table.add(second);
        table.add(spacer);
        table.add(third);
        table.row().pad(0, 0, 0, 0);
        table.add(podium).colspan(3);
        table.row().pad(10, 0, 0, 0);
        table.add(go_again).fillX();
        table.add(spacer);
        table.add(exit).fillX();
        
        //table.add()
    }

    @Override
    public void render(float delta_time){
        /**gets called every frame
         * resets screen then draws new frame to it
         */
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height){
        // also called ever frame to keep menu centred
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void hide(){

    }

    @Override
    public void dispose(){

    }
}