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

public class BoatSelectScreen extends ScreenAdapter{

    private DragonBoatGame parent;
    private Stage stage;

    private Label title;
    private Label boat_name;
    private Label boat_num;

    public BoatSelectScreen(DragonBoatGame game) {
        parent = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
    }

    @Override
    public void show(){

        Table table_big = new Table();
        table_big.setFillParent(true);
        stage.addActor(table_big);

        Skin skin = new Skin(Gdx.files.internal("clean-crispy/clean-crispy-ui.json"));

        final TextButton return_button = new TextButton("Back", skin);
        return_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(DragonBoatGame.MENU);
            }
        });

        title = new Label("Boat Select", skin);
        boat_name = new Label("Speedy", skin);


        

        table_big.add(title).colspan(2);
        table_big.row().pad(10, 0, 0, 10);
        //table_big.add(table_small("Test", "1"));
        //table_big.add(table_small("Hello", "2"));
        table_big.row().pad(10, 0, 0, 10);
        table_big.add(return_button).colspan(2);



    }

    /**
    public Table table_small(String name, String number){

        Skin skin = new Skin(Gdx.files.internal("clean-crispy/clean-crispy-ui.json"));

        boat_name = new Label(name, skin);
        boat_num = new Label(number, skin);

        Table table_small = new Table();
        table_small.setFillParent(false);
        stage.addActor(table_small);

        table_small.add(boat_name).colspan(2);
        table_small.row().pad(10, 0, 0, 0);
        table_small.add("Boat Number");
        table_small.add(boat_num);

        return table_small;

    }*/

    @Override
    public void render(float delta_time){

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
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