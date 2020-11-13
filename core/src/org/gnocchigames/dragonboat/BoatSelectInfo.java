/**package org.gnocchigames.dragonboat;

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
/**
public class BoatSelectInfo{

    public static enum Boat_Type {
        FAST, HARD, ACCEL, MANOEUVREABLE
    }

    private Label title;
    private Label speed_label;
    private Label speed_num;
    private Label acceleration_label;
    private Label acceleration_num;
    private Label manouverability_stat;
    private Label manouverability_num;
    private Label robustness_stat;
    private Label robustness_num;

    //private Boat fast = new Boat(Boat.Boat_Type.FAST);
    //private Boat accel = new Boat(Boat.Boat_Type.ACCEL);
    //private Boat manoeuverability = new Boat(Boat.Boat_Type.MANOEUVREABLE);
    //private Boat hard = new Boat(Boat.Boat_Type.HARD);

    // replace with a checkbox
    private Label check;

    

    

    public Integer BoatSelectInfo(Boat_Type type){
        
        Boat boat = setBoatType(type);

        Table table = new Table();
        table.setFillParent(true);
        
        Skin skin = new Skin(Gdx.files.internal("clean-crispy/clean-crispy-ui.json"));

        title = new Label ("Fast", skin);
        speed_label = new Label ("Speed", skin);
        speed_num = new Label (Integer.toString(boat.speed_stat), skin);
        acceleration_label = new Label ("Acceleration", skin);        
        acceleration_num = new Label(Integer.toString(boat.acceleration_stat), skin);

        table.add(title).colspan(2);
        table.row().pad(10, 0, 0, 0);
        table.add(speed_label);
        table.add(speed_num);
        table.row().pad(10, 0, 0 ,0);
        table.add(acceleration_label);
        table.add(acceleration_num);
        table.row().pad(10, 0, 0, 0);
        table.add(check).colspan(2);

        return speed_num;
        


    }

    private Boat setBoatType(Boat_Type type){
        switch(type){
            case FAST:
                this.Boat = new Boat(Boat.Boat_Type.FAST);
                return boat;
                break;
            case HARD:
                Boat boat = new Boat(Boat.Boat_Type.HARD);
                return boat;
                break;
            case ACCEL:
                Boat boat = new Boat(Boat.Boat_Type.ACCEL);
                return boat;
                break;
            case MANOEUVREABLE:
                Boat boat = new Boat(Boat.Boat_Type.MANOEUVREABLE);
                return boat;
                break;
            default:
                Boat boat = new Boat(Boat.Boat_Type.MANOEUVREABLE);
                return boat;
                break;
        } 
        
    }



}
*/