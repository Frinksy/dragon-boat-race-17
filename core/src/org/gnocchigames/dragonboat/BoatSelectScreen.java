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
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class BoatSelectScreen extends ScreenAdapter{

    private Boat.Boat_Type type;

    private DragonBoatGame parent;
    private Stage stage;

    private Label title;
    private Label f_speed_label;
    private Label h_speed_label;
    private Label acceleration_label;
    private Label manouverability_label;
    private Label robustness_label;

    private Label f_title;
    private ProgressBar f_speed_num;
    private Label f_acceleration_num;
    private Label f_manouverability_num;
    private Label f_robustness_num;

    private Label h_title;
    private ProgressBar h_speed_num;
    private Label h_acceleration_num;
    private Label h_manouverability_num;
    private Label h_robustness_num;

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


        Table table_fast = new Table();
        table_fast = BoatSelectInfo.drawTable(Boat.Boat_Type.FAST);

        Table table_acceleration = new Table();
        table_acceleration = BoatSelectInfo.drawTable(Boat.Boat_Type.ACCEL);



        Boat boat_fast = new Boat(Boat.Boat_Type.FAST);
        Boat boat_hard = new Boat(Boat.Boat_Type.HARD);


        Skin skin = new Skin(Gdx.files.internal("clean-crispy/clean-crispy-ui.json"));

        final TextButton return_button = new TextButton("Back", skin);
        return_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(DragonBoatGame.MENU);
            }
        });

        title = new Label("Boat Select", skin);
        f_speed_label = new Label("Speed", skin);
        h_speed_label = new Label("Speed", skin);
        acceleration_label = new Label("Acceleration", skin);
        manouverability_label = new Label("Manouverability", skin);
        robustness_label = new Label("Robustness", skin);

        f_title = new Label("Fast", skin);
        f_speed_num = new ProgressBar(0f, 100f, 1f, false, skin);
        f_speed_num.setValue(boat_fast.speed_stat);

        h_title = new Label("Hard", skin);
        h_speed_num = new ProgressBar(0f, 100f, 1f, false, skin);
        h_speed_num.setValue(boat_hard.speed_stat);     
        
        table_big.add(title).colspan(2);
        table_big.row().pad(15, 50, 0, 50);
        table_big.add(f_title);
        table_big.add(h_title);
        table_big.row().pad(5, 50, 0, 50);
        table_big.add(table_fast);
        table_big.add(table_acceleration);
        //table_big.add(f_speed_label);
        //table_big.add(f_speed_num);
        //table_big.add(h_speed_label);
        //table_big.add(h_speed_num);
        table_big.row().pad(10, 0, 0, 10);
        //table_big.add(f_acceleration_label);
        table_big.add(return_button).colspan(4);



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