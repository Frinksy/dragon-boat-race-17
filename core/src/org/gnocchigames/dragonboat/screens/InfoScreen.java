package org.gnocchigames.dragonboat.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import org.gnocchigames.dragonboat.DragonBoatGame;

/**
 * InfoScreen
 */
public class InfoScreen extends ScreenAdapter {
    
    private DragonBoatGame parent;
    private Stage stage;

    /**
     * Create an InfoScreen instance
     * @param parent the parent DragonBoatGame instance
     */
    public InfoScreen(DragonBoatGame parent) {
        this.parent = parent;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        Skin skin = parent.skin_store.get("clean-crispy/clean-crispy-ui.json");

        Label text = new Label("Welcome to DragonBoatRace.\n"
        + "The aim of the game is to get to the finish line before your opponents\n"
        + "The game consists of 3 legs, followed by a final.\n"
        + "The first leg is a practice; your result will not count towards qualifying for the final.\n"
        + "Your score comes from a combined total time of legs 2 & 3\n"
        + "To qualify for the final your score must be in the top 3 scores after leg 3\n"
        + "You paddle using the arrows or WASD.\n"
        + "Your health and tiredness are represented by green and cyan bars respectively\n"
        + "On legs 2 and 3, you may be eliminated if your health reduces to 0.\n"
        + "CAREFUL: the time spent outside of your lane is doubled!\n"
        + "Finally: whichever boat you choose, will be place in the middle lane\n"
        + "GOOD LUCK!"
        , skin);
        
        text.setFontScale(1.25f);
        text.setAlignment(2);
        
        TextButton button = new TextButton("Choose boat", skin);
        button.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                parent.changeScreen(DragonBoatGame.BOAT_SELECT);
            }
        });
        
        TextButton button_back = new TextButton("Back to menu", skin);
        button_back.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                parent.changeScreen(DragonBoatGame.MENU);
            }
        });
        table.add(text);
        table.row().pad(5, 5, 5, 5);
        table.add(button);
        table.row().pad(5, 5, 5, 5);
        table.add(button_back);
        
    }


    @Override
    public void render(float delta_time) {
        /**gets called every frame
         * resets screen then draws new frame to it
         */
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        // also called ever frame to keep menu centred
        stage.getViewport().update(width, height, true);

    }

}
