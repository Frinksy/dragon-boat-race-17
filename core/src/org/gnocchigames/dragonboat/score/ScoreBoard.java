package org.gnocchigames.dragonboat.score;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import org.gnocchigames.dragonboat.DragonBoatGame;
import org.gnocchigames.dragonboat.entities.Boat;

public class ScoreBoard {
    

    public Map<String, Long> times;
    public Map<String, Boat> boats;
    public DragonBoatGame game;

    public ScoreBoard(DragonBoatGame game, List<Boat> players) {
        this.game = game;
        this.times = new HashMap<String, Long>();
        this.boats = new HashMap<String, Boat>();

        for (Boat boat : players) {
            this.times.put(boat.getName(), 0l);
            this.boats.put(boat.getName(), boat);
        }
    }

    public void addPlayers(List<Boat> players) {
        for (Boat boat : players) {
            if (!this.times.containsKey(boat.getName())) {
                this.times.put(boat.getName(), 0l);
            }
            this.boats.put(boat.getName(), boat);
        }
    }

    public void computeRoundEndScores() {
        for (String player : times.keySet()) {
            Boat boat = boats.get(player);
            long time = boat.end_time - boat.start_time;
            long total_time = this.times.get(player);
            total_time += time;
            this.times.put(player, total_time);
        }
    }

    public Map<String, String> getFormattedTimes() {
        Map<String, String> output = new HashMap<String, String>();

        for (String player : times.keySet()) {
            
            Duration duration = Duration.ofMillis(times.get(player));
            String formatted_time = String.format(
                "%02d:%02d.%03d",
                duration.toMinutes(),
                duration.toMillis()%60000 / 1000,
                duration.toMillis()%1000
                );
            output.put(player, formatted_time);
        }

        return output;
    }

    public Table drawScoreTable() {
        
        Table table = new Table();
        table.setFillParent(false);

        Skin skin = new Skin(Gdx.files.internal("clean-crispy/clean-crispy-ui.json"));
        
        table.add(new Label("Boat", skin));
        table.add(new Label("Total Time", skin));

        Map<String, String> formatted_times = getFormattedTimes();

        for (String player : formatted_times.keySet()) {
            
            table.row().pad(10, 5, 0, 5);
            
            Label name_label = new Label(player, skin);
            name_label.setColor(boats.get(player).colour);
    
            table.add(name_label);
            table.add(new Label(formatted_times.get(player), skin));
            

        } 



        return table;

    }


}
