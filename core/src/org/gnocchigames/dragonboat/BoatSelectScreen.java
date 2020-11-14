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
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class BoatSelectScreen extends ScreenAdapter{

    private Boat.Boat_Type type;

    private DragonBoatGame parent;
    private Stage stage;

    private Label title;
    private Label s_title;
    private Label a_title;
    private Label m_title;
    private Label r_title;
    private Label d_title;


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

        //Table table_default = new Table();
        //table_default = BoatSelectInfo.drawTable(Boat.Boat_Type.DEFAULT);
        Table table_fast = new Table();
        table_fast = BoatSelectInfo.drawTable(Boat.Boat_Type.FAST);
        Table table_acceleration = new Table();
        table_acceleration = BoatSelectInfo.drawTable(Boat.Boat_Type.ACCEL);
        Table table_maneuverable = new Table();
        table_maneuverable = BoatSelectInfo.drawTable(Boat.Boat_Type.MANOEUVREABLE);
        Table table_robust = new Table();
        table_robust = BoatSelectInfo.drawTable(Boat.Boat_Type.HARD);

        Skin skin = new Skin(Gdx.files.internal("clean-crispy/clean-crispy-ui.json"));

        final CheckBox choose_fast = new CheckBox(null, skin);
        final CheckBox choose_acceleration = new CheckBox(null, skin);
        final CheckBox choose_maneuverable = new CheckBox(null, skin);
        final CheckBox choose_robust = new CheckBox(null, skin);

        choose_fast.setChecked(false);
        choose_acceleration.setChecked(false);
        choose_maneuverable.setChecked(false);
        choose_robust.setChecked(false);

        choose_fast.addListener( new EventListener() {
   	    @Override
	    public boolean handle(Event event) {
            boolean enabled = choose_fast.isChecked();   
       	    return false;
	        }
        });

        choose_acceleration.addListener( new EventListener() {
        @Override
         public boolean handle(Event event) {
                boolean enabled = choose_acceleration.isChecked();
                return false;
             }
         });

         choose_maneuverable.addListener( new EventListener() {
        @Override
         public boolean handle(Event event) {
                boolean enabled = choose_maneuverable.isChecked();
                return false;
             }
         });

         choose_robust.addListener( new EventListener() {
        @Override
         public boolean handle(Event event) {
                boolean enabled = choose_robust.isChecked();
                return false;
             }
         });

        final TextButton return_button = new TextButton("Back", skin);
        return_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(DragonBoatGame.MENU);
            }
        });

        title = new Label("Boat Select", skin);
        s_title = new Label("Speedy", skin);
        a_title = new Label("Accelerationy", skin);
        m_title = new Label("Maneuverabley", skin);
        r_title = new Label("Robusty", skin);   
        d_title = new Label("Defaulty", skin);
        table_big.add(title).colspan(2);
        table_big.row().pad(20, 50, 0, 50);
        //table_big.add(d_title);
        //table_big.row().pad(10, 0, 0, 0);
        //table_big.add(table_default);
        table_big.row().pad(10, 50, 0, 50);
        table_big.add(s_title);
        table_big.add(a_title);
        table_big.row().pad(10, 50, 0, 50);
        table_big.add(table_fast);
        table_big.add(table_acceleration);
        table_big.row().pad(20, 50, 0, 50);
        table_big.add(m_title);
        table_big.add(r_title);
        table_big.row().pad(10, 50, 0, 50);
        table_big.add(table_maneuverable);
        table_big.add(table_robust);
        table_big.row().pad(20, 50, 0, 50);
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