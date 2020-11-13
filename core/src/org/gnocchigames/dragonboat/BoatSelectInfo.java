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
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/** Main menu screen
 * MainMenuScreen is instantiated by DragonBoatGame
 */

public class BoatSelectInfo{

    private static Label title;
    private static Label speed_label;
    private static ProgressBar speed_num;
    private static Label acceleration_label;
    private static ProgressBar acceleration_num;
    private static Label manouverability_label;
    private static ProgressBar manouverability_num;
    private static Label robustness_label;
    private static ProgressBar robustness_num;


    public static Table drawTable(Boat.Boat_Type type){

        Boat boat = new Boat(Boat.Boat_Type.FAST);

        Table table = new Table();
        table.setFillParent(false);        

        Skin skin = new Skin(Gdx.files.internal("clean-crispy/clean-crispy-ui.json"));

        speed_label = new Label ("Speed", skin);
        acceleration_label = new Label ("Acceleration", skin);
        manouverability_label = new Label ("Maneuverability", skin);
        robustness_label = new Label ("Robustness", skin);
        speed_num = new ProgressBar(0f, 100f, 1f, false, skin);
        speed_num.setValue(boat.speed_stat);
        acceleration_num = new ProgressBar(0f, 100f, 1f, false, skin);
        acceleration_num.setValue(boat.acceleration_stat); 
        manouverability_num = new ProgressBar(0f, 100f, 1f, false, skin);
        manouverability_num.setValue(boat.manoeuverability_stat);
        robustness_num = new ProgressBar(0f, 100f, 1f, false, skin);
        robustness_num.setValue(boat.robustness_stat);

        table.add(speed_label);
        table.add(speed_num);
        table.row().pad(10, 0, 0, 5);
        table.add(acceleration_label);
        table.add(acceleration_num);
        table.row().pad(10, 0, 0, 5);
        table.add(manouverability_label);
        table.add(manouverability_num);
        table.row().pad(10, 0, 0, 5);
        table.add(robustness_label);
        table.add(robustness_num);

        return table;
    }

    //private Boat fast = new Boat(Boat.Boat_Type.FAST);
    //private Boat accel = new Boat(Boat.Boat_Type.ACCEL);
    //private Boat manoeuverability = new Boat(Boat.Boat_Type.MANOEUVREABLE);
    //private Boat hard = new Boat(Boat.Boat_Type.HARD);

    // replace with a checkbox
}