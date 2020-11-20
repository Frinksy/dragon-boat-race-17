package org.gnocchigames.dragonboat.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import org.gnocchigames.dragonboat.DragonBoatGame;
import org.gnocchigames.dragonboat.entities.Boat;
import org.gnocchigames.dragonboat.entities.Boat.Boat_Type;

import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class BoatSelectScreen extends ScreenAdapter{

    private static Boat.Boat_Type type;
    private String choice;

    private DragonBoatGame parent;
    private Stage stage;

    private Label title;
    private Label s_title;
    private Label a_title;
    private Label m_title;
    private Label r_title;
    private Label d_title;
    private Label choose_label;
    private Label pad_l;
    private Label pad_r;

    /**
     * Boat select screen class,
     * displays all the infomation about differnet boat types
     * allows the user to choose using a drop down menu
     * and start the game 
     */

    public BoatSelectScreen(DragonBoatGame game) {
        
        parent = game;
    }

    @Override
    public void show(){

        type = Boat_Type.DEFAULT;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // declares tables
        Table table_big = new Table();
        table_big.setFillParent(true);
        stage.addActor(table_big);

        // gets tables containing the boat stats from BoatSelectInfo class
        Table table_default = new Table();
        table_default = BoatSelectInfo.drawTable(Boat.Boat_Type.DEFAULT);
        Table table_fast = new Table();
        table_fast = BoatSelectInfo.drawTable(Boat.Boat_Type.FAST);
        Table table_acceleration = new Table();
        table_acceleration = BoatSelectInfo.drawTable(Boat.Boat_Type.ACCEL);
        Table table_maneuverable = new Table();
        table_maneuverable = BoatSelectInfo.drawTable(Boat.Boat_Type.MANOEUVREABLE);
        Table table_robust = new Table();
        table_robust = BoatSelectInfo.drawTable(Boat.Boat_Type.HARD);

        Skin skin = new Skin(Gdx.files.internal("clean-crispy/clean-crispy-ui.json"));

        // declares a drop down table and the listener that assigns the players boat type when fired
        final SelectBox<String> choose = new SelectBox<String>(skin);
        choose.setItems("Defaulty", "Speedy", "Accelerationy", "Maneuverabley", "Robusty");
        choose.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                choice = choose.getSelected();
                System.out.println(choice);
                if (choice == "Speedy"){
                    type = (Boat.Boat_Type.FAST);
                }else if (choice == "Accelerationy"){
                    type = (Boat.Boat_Type.ACCEL);
                }else if (choice == "Maneuverabley"){
                    type = (Boat.Boat_Type.MANOEUVREABLE);
                }else if (choice == "Robusty"){
                    type = (Boat.Boat_Type.HARD);
                }else{
                    type = Boat.Boat_Type.DEFAULT;
                }
            }
        });

        // declares buttons and the screens they should move to 
        final TextButton return_button = new TextButton("Back", skin);
        return_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(DragonBoatGame.MENU);
            }
        });

        final TextButton start = new TextButton("Start", skin);
        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(DragonBoatGame.RACE_LEG);

            }
        });

        // declares labels
        title = new Label("BOAT SELECT", skin);
        title.setFontScale(1.25f);
        s_title = new Label("Speedy", skin);
        s_title.setFontScale(1.15f);
        a_title = new Label("Accelerationy", skin);
        a_title.setFontScale(1.15f);
        m_title = new Label("Maneuverabley", skin);
        m_title.setFontScale(1.15f);
        r_title = new Label("Robusty", skin);
        r_title.setFontScale(1.15f);   
        d_title = new Label("Defaulty", skin);
        d_title.setFontScale(1.15f);
        choose_label = new Label("Choose a Boat:", skin);
        choose_label.setFontScale(1.15f);
        pad_l = new Label("", skin);
        pad_r = new Label("", skin);

        // adds all actors to the table in position
        table_big.add(title).colspan(4);
        table_big.row().pad(20, 50, 0, 50);
        table_big.add(d_title).colspan(4);
        table_big.row().pad(10, 50, 0, 50);
        table_big.add(table_default).colspan(4);
        table_big.row().pad(20, 50, 0, 50);
        table_big.add(s_title).colspan(2);
        table_big.add(a_title).colspan(2);
        table_big.row().pad(10, 50, 0, 50);
        table_big.add(table_fast).colspan(2);
        table_big.add(table_acceleration).colspan(2);
        table_big.row().pad(20, 50, 0, 50);
        table_big.add(m_title).colspan(2);
        table_big.add(r_title).colspan(2);
        table_big.row().pad(10, 50, 0, 50);
        table_big.add(table_maneuverable).colspan(2);
        table_big.add(table_robust).colspan(2);
        table_big.row().pad(20, 5, 0, 5);
        table_big.add(pad_l);
        table_big.add(choose_label);
        table_big.add(choose).fillX();
        table_big.add(pad_r);
        table_big.row().pad(10, 5, 0, 5);
        table_big.add(pad_l);
        table_big.add(start).fillX();
        table_big.add(return_button).fillX();
    }

    // allows other classes to call get what boat a user has chosen
    public static Boat.Boat_Type getBoat(){
        return type;
    }

    // refreshes the screen each frame
    @Override
    public void render(float delta_time){

        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
        stage.draw();
    }

    // recenters the actors each frame depending on the size of the application
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