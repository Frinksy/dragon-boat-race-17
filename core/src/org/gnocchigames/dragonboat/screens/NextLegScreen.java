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

/**
 * NextLegScreen
 */
public class NextLegScreen extends ScreenAdapter{

    private DragonBoatGame parent;
    private Stage stage;

    private Label title;

    /**
     * Create a NextLegScreen instance
     * @param game the parent DragonBoatGame instance
     */
    public NextLegScreen(DragonBoatGame game) {
        parent = game;
    }

    @Override
    public void show(){
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table scores_table = parent.score_board.drawScoreTable();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        Skin skin = parent.skin_store.get("clean-crispy/clean-crispy-ui.json");

        title = new Label("RESULTS", skin);
        title.setFontScale(1.25f);

        final TextButton next = new TextButton("Next Leg", skin);
        next.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                parent.changeScreen(DragonBoatGame.RACE_LEG);
            }
        });
        table.add(title);
        table.row().pad(50, 0, 0, 0);
        table.add(scores_table);
        table.row().pad(50, 0, 0, 0);
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

    