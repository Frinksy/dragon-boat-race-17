package org.gnocchigames.dragonboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/** Main menu screen
 * MainMenuScreen is instantiated by DragonBoatGame
 */
public class MainMenuScreen extends ScreenAdapter{

    private DragonBoatGame parent;
    private Stage stage;
    private Label title;
    

    public MainMenuScreen(DragonBoatGame game) {
        
        parent = game;
    }


    @Override
    public void show() {

        // Gets called when the screen pops up
        //constructor creates a new stage 
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage); 

        
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true); shows padding and borders
        stage.addActor(table);

        /**
         *  sets new skin(style) for text, buttons etc. 
         * easily changable with a load of free skins
         * https://github.com/czyzby/gdx-skins
         * copy paste in skin file to assets and change link bellow too x.json 
         */
        Skin skin = new Skin(Gdx.files.internal("clean-crispy/clean-crispy-ui.json"));

        // creates labels and buttons actors to be used in the stage
        title = new Label("DRAGON BOAT GAME", skin);
        title.setFontScale(1.25f);
        final TextButton controls = new TextButton("Controls", skin);
        final TextButton boat_select = new TextButton("Boat Select", skin);
        final TextButton settings = new TextButton("Settings", skin);
        final TextButton exit = new TextButton("Exit", skin);
        // test button
        final TextButton podium = new TextButton("Podium (Test)", skin);

        // adds listners to the buttons for when they are clicked and screens to change to
        controls.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                parent.changeScreen(DragonBoatGame.CONTROLS);
            }
        });

        boat_select.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                parent.changeScreen(DragonBoatGame.BOAT_SELECT);
            }
        });

        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(DragonBoatGame.SETTINGS);
            }
        });
        
        exit.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                Gdx.app.exit();
            }
        });

        // button to test podium screen
        podium.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(DragonBoatGame.PODIUM);
            }
        });
        

        // creates a table and populates it with our labels/buttons
        table.add(title);
        table.row().pad(10, 0, 0, 0);
        table.add(controls).fillX().uniformX();
        table.row().pad(10, 0, 0, 0);
        table.add(boat_select).fillX().uniformX();
        table.row().pad(10, 0, 0, 0);
        table.add(settings).fillX().uniformX();
        table.row().pad(10, 0, 0, 0);
        table.add(exit).fillX().uniformX();
        table.row().pad(10, 0, 0, 0);
        table.add(podium).fillX().uniformX();

        
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

    public void resize(int width, int height) {
        // also called ever frame to keep menu centred
        stage.getViewport().update(width, height, true);

    }

    public void pause(){

    }
    
    public void resume(){
    }
    

    @Override
    public void hide() {
        // Gets called when game switches to another screen
        // We can probably do without this, only using dispose()
    }

    @Override
    public void dispose() {
        // called when closed to save memory
    }

}
