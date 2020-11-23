package org.gnocchigames.dragonboat.screens;

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
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import org.gnocchigames.dragonboat.DragonBoatGame;

import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/** Settings screen
 * SettingsScreen is instantiated by DragonBoatGame
 */

public class SettingsScreen extends ScreenAdapter{

    private DragonBoatGame parent;
    private Stage stage;

    private Label title;
    private Label sound_enabled_label;
    private Label sound_volume_label;

    public SettingsScreen(DragonBoatGame game) {

        //constructor creates a new stage 
        parent = game;       
    }

    @Override
    public void show(){   
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        // Gets called when screen pops up
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true); shows padding and borders
        stage.addActor(table);

        /**
         *  sets parent.skin_store.get(style) for text, buttons etc. 
         * easily changable with a load of free skins
         * https://github.com/czyzby/gdx-skins
         * copy paste in skin file to assets and change link bellow too x.json 
         */

        Skin skin = parent.skin_store.get("clean-crispy/clean-crispy-ui.json"); 
        
        /**
         * creates a new slider for our sound volume
         * and a listener to find out when changed
         */
        final Slider volume_slider = new Slider(0f, 1f, 0.1f, false, skin);
        volume_slider.setValue(parent.getSettings().getSoundVolume());
        volume_slider.addListener(new EventListener(){
  		@Override
		public boolean handle(Event event) {
  			parent.getSettings().setSoundVolume(volume_slider.getValue());
                return false;
	        }
        });

        /**
         * creates a new checkbox for our sound volume to check/ set enabled
         * and a listener to find out when changed
         */
        final CheckBox enabled_checkbox = new CheckBox(null, skin);
        enabled_checkbox.setChecked(parent.getSettings().getSoundEnabled());
        enabled_checkbox.addListener(new EventListener() {
   	    @Override
	    public boolean handle(Event event) {
       	    boolean enabled = enabled_checkbox.isChecked();
       	    parent.getSettings().setSoundEnabled(enabled);
       	    return false;
	        }
        });

        /**
         * adds a new button that returns user to the main menu
         */
        final TextButton return_button = new TextButton("Back", skin);
        return_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(DragonBoatGame.MENU);
            }
        });

        // text label actors to be used in table
        title = new Label("SETTINGS", skin);
        title.setFontScale(1.25f);
        sound_volume_label = new Label("Sound Volume" , skin);
        sound_enabled_label = new Label("Sound Enabled", skin);


        /**
         * a table consisting of a 2 columns and multiple rows
         * left column holds labels and right sliders and checkboxes
         * title and return_button span both columns
         */
        table.add(title).colspan(2);
        table.row().pad(10, 0, 0, 10);
        table.add(sound_volume_label);
        table.add(volume_slider);
        table.row().pad(10, 0, 0, 10);
        table.add(sound_enabled_label);
        table.add(enabled_checkbox);
        table.row().pad(10, 0, 0, 10);
        table.add(return_button).colspan(2).uniformX();
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
        // called when closed to save memory

    }
}