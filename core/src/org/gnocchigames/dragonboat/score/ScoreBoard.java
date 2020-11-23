package org.gnocchigames.dragonboat.score;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
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
import org.gnocchigames.dragonboat.util.GameStructure;

public class ScoreBoard {
    
    public static int FINISH_HEIGHT = 20000;

    public Map<String, Long> times;
    public Map<String, Boat> boats;
    public List<String> eliminated_boats;
    public List<String> disqualified_boats;
    public DragonBoatGame game;

    public ScoreBoard(DragonBoatGame game, List<Boat> players) {
        this.game = game;
        this.times = new HashMap<String, Long>();
        this.boats = new HashMap<String, Boat>();
        this.eliminated_boats = new ArrayList<String>();
        this.disqualified_boats = new ArrayList<String>();

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

            if (!boat.isAlive()) {
                eliminated_boats.add(player);
            }
            long time = boat.end_time - boat.start_time;
            long total_time = this.times.get(player);
            total_time += time;

            if (boat.pos_y < FINISH_HEIGHT) {
                long delay = (long) ((long) (FINISH_HEIGHT - (int)boat.pos_y) / (boat.speed_stat * boat.tiredness_factor));
                total_time += delay*1000;
            }

            this.times.put(player, total_time);

        }
    }

    public Map<String, String> getFormattedTimes() {
        Map<String, String> output = new HashMap<String, String>();

        for (String player : times.keySet()) {
            if (eliminated_boats.contains(player)) {
                output.put(player, "DNF");
            } else {
                Duration duration = Duration.ofMillis(times.get(player));
                String formatted_time = String.format(
                    "%02d:%02d.%03d",
                    duration.toMinutes(),
                    duration.toMillis()%60000 / 1000,
                    duration.toMillis()%1000
                    );
                output.put(player, formatted_time);
            }
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

    public void eliminateBoats(GameStructure.Legs next_leg) {

        switch (next_leg) {
            case LEG_TWO:
                // Do nothing, everyone gets to retry
                for (String name : times.keySet()) {
                    times.put(name, 0l);
                }
                eliminated_boats = new ArrayList<String>();
                break;
            case LEG_THREE:
                // Eliminate dead boats
                for (String name : eliminated_boats) {
                    times.remove(name);
                    boats.remove(name);
                    disqualified_boats.add(name);
                }
            case LEG_FINAL:
                // Eliminate dead boats and get top three
                for (String name : eliminated_boats) {
                    boats.remove(name);
                    times.remove(name);
                    disqualified_boats.add(name);
                }
                List<Long> time_list = new ArrayList<Long>();
                for (Long time : times.values()) {
                    time_list.add(time);
                }
                time_list.sort(null);

                List<Long> top_three_times = time_list.subList(0, Math.min(time_list.size(), 3));

                List<String> boats_to_remove = new ArrayList<String>();

                for (String name : times.keySet()) {
                    if (!top_three_times.contains(times.get(name))) {
                        boats_to_remove.add(name);
                    }
                }

                for (String name : boats_to_remove) {
                    times.remove(name);
                    boats.remove(name);
                    disqualified_boats.add(name);
                }

                break;
            default:
                // Do nothing
                break;


            }
    }


}