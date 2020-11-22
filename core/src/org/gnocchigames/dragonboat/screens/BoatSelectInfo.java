package org.gnocchigames.dragonboat.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

import org.gnocchigames.dragonboat.DragonBoatGame;
import org.gnocchigames.dragonboat.entities.Boat;


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
    private static Color colour;

    /**
     * Draw the boat select table to the screen
     */
    public static Table drawTable(DragonBoatGame game, Boat.Boat_Type type){

        // declare all tables, labels and progress bars to be displayed
        Boat boat = new Boat(game, null, type);

        Table table = new Table();
        table.setFillParent(false);        

        Skin skin = new Skin(Gdx.files.internal("clean-crispy/clean-crispy-ui.json"));

        speed_label = new Label ("Speed", skin);
        acceleration_label = new Label ("Acceleration", skin);
        manouverability_label = new Label ("Maneuverability", skin);
        robustness_label = new Label ("Robustness", skin);

        speed_num = new ProgressBar(0f, 100f, 1f, false, skin);
        speed_num.setValue(boat.speed_stat);
        speed_num.setColor(boat.colour);
        acceleration_num = new ProgressBar(0f, 100f, 1f, false, skin);
        acceleration_num.setValue(boat.acceleration_stat); 
        acceleration_num.setColor(boat.colour);
        manouverability_num = new ProgressBar(0f, 100f, 1f, false, skin);
        manouverability_num.setValue(boat.manoeuverability_stat);
        manouverability_num.setColor(boat.colour);
        robustness_num = new ProgressBar(0f, 100f, 1f, false, skin);
        robustness_num.setValue(boat.robustness_stat);
        robustness_num.setColor(boat.colour);

        // Draw the table containing all the labels and conresponding stats
        table.add(speed_label);
        table.add(speed_num);
        table.row().pad(10, 5, 0, 5);
        table.add(acceleration_label);
        table.add(acceleration_num);
        table.row().pad(10, 5, 0, 5);
        table.add(manouverability_label);
        table.add(manouverability_num);
        table.row().pad(10, 5, 0, 5);
        table.add(robustness_label);
        table.add(robustness_num);

        return table;
    }
}