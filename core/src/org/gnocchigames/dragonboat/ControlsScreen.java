package org.gnocchigames.dragonboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class ControlsScreen extends ScreenAdapter{

    private DragonBoatGame parent;
    private Stage stage;

    private Label title;
    private Label acc_key;
    private Label acc_label;
    private Label dec_key;
    private Label dec_label;
    private Label left_key;
    private Label left_label;
    private Label right_key;
    private Label right_label;

    public ControlsScreen(DragonBoatGame game) {

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

        final TextButton return_button = new TextButton("Back", skin);
        return_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(DragonBoatGame.MENU);
            }
        });

        title = new Label("Controls", skin);
        acc_label = new Label("Accelerate:", skin);
        dec_label = new Label("Decelerate:", skin);
        left_label = new Label("Turn Left:", skin);
        right_label = new Label("Turn Right:", skin);

        acc_key = new Label("Up Arrow", skin);
        dec_key = new Label("Down Arrow", skin);
        left_key = new Label("Left Arrow", skin);
        right_key = new Label("Right Arrow", skin);    

        table.add(title).colspan(2);
        table.row().pad(20, 10, 0, 10);
        table.add(acc_label);
        table.add(acc_key);
        table.row().pad(10, 10, 0, 10);
        table.add(dec_label);
        table.add(dec_key);
        table.row().pad(10, 10, 0, 10);
        table.add(right_label);
        table.add(right_key);
        table.row().pad(10, 10, 0, 10);
        table.add(left_label);
        table.add(left_key);
        table.row().pad(20, 5, 0, 5);
        table.add(return_button).colspan(2);

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

    }
}