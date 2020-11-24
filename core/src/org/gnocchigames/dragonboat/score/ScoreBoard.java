package org.gnocchigames.dragonboat.score;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import org.gnocchigames.dragonboat.DragonBoatGame;
import org.gnocchigames.dragonboat.entities.Boat;
import org.gnocchigames.dragonboat.util.GameStructure;

/**
 * ScoreBoard to store all the times of the players. 
 * Also provides functions to draw scoreboard.
 */
public class ScoreBoard {
    
    public static int FINISH_HEIGHT = 20000;

    public Map<String, Long> times;
    public Map<String, Boat> boats;
    public List<String> eliminated_boats;
    public List<String> disqualified_boats;
    public DragonBoatGame game;
    private List<String> podium_names;

    /**
     * Create a ScoreBoard instance
     * @param game the DragonBoatGame instance to which the ScoreBoard is tied 
     * @param players the intial list of players on the ScoreBoard
     */
    public ScoreBoard(DragonBoatGame game, List<Boat> players) {
        this.game = game;
        this.times = new HashMap<String, Long>();
        this.boats = new HashMap<String, Boat>();
        this.eliminated_boats = new ArrayList<String>();
        this.disqualified_boats = new ArrayList<String>();

        // Add default podium names
        podium_names = new ArrayList<String>();
        podium_names.add("Speedy");
        podium_names.add("Defaulty");
        podium_names.add("Robusty");

        for (Boat boat : players) {
            this.times.put(boat.getName(), 0l);
            this.boats.put(boat.getName(), boat);
        }

    }

    /**
     * Add players to the ScoreBoard
     * @param players the list of players to add
     */
    public void addPlayers(List<Boat> players) {
        for (Boat boat : players) {
            if (!this.times.containsKey(boat.getName())) {
                this.times.put(boat.getName(), 0l);
            }
            this.boats.put(boat.getName(), boat);
        }
    }
    
    /**
     * Compute the new scores for all the boats. 
     * Should be called at the end of the leg to update the times.
     */
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

    /**
     * Get the formatted strings of the boats' times
     * @return a Map where keys are names and values are formatted strings
     */
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

    /**
     * Get the ScoreBoard as a Table
     * @return the Table of the Scoreboard
     */
    public Table drawScoreTable() {
        
        Table table = new Table();
        table.setFillParent(false);

        Skin skin = game.skin_store.get("clean-crispy/clean-crispy-ui.json");
        
        table.add(new Label("Boat", skin));
        table.add(new Label("Total Time", skin));

        Map<String, String> formatted_times = getFormattedTimes();


        for (String player : getOrderedNames()) {
            
            table.row().pad(10, 5, 0, 5);
            
            Label name_label = new Label(player, skin);
            name_label.setColor(boats.get(player).colour);
    
            table.add(name_label);
            table.add(new Label(formatted_times.get(player), skin));
            

        } 



        return table;

    }

    /**
     * Eliminate boats according to the rules of the game.  
     * @param next_leg the next leg that is going to be played
     */
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
                break;
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

                for (String name : times.keySet()) {
                    times.put(name, 0l);
                }

                break;
            default:
                // Do nothing
                break;
        }
    }

    /**
     * Get the top 3 names in order to be displayed on the podium
     * @return the ordered list of the top 3 names
     */
    public List<String> getPodiumNames() {

        /*If the game hasn't been played yet,
          we may as well just give 3 random names
          to make the podium look like people have already
          played
         */

        if (times.size() == 0) 
        {
            return podium_names;
        }
        

        List<String> output = new ArrayList<String>();

        List<Long> time_list = new ArrayList<Long>();
        time_list.addAll(times.values());

        time_list.sort(null);

        for (Long time : time_list) {
            for (String name : times.keySet()) {
                if (times.get(name) == time) {
                    output.add(name);
                }
            }
        }

        podium_names = output;

        return podium_names;
    }

    /**
     * Reset the ScoreBoard
     */
    public void resetAll() {
        this.times = new HashMap<String, Long>();
        this.boats = new HashMap<String, Boat>();
        this.eliminated_boats = new ArrayList<String>();
        this.disqualified_boats = new ArrayList<String>();
    }

    /**
     * Get the list of names in order of their position 
     * in the scoreboard.
     * @return the ordered list of boats
     */
    private List<String> getOrderedNames() {

        List<String> ordered_players = new ArrayList<String>();
        List<Long> ordered_times = new ArrayList<Long>();

        for (String name : times.keySet()) {
            if (!eliminated_boats.contains(name)){
                ordered_times.add(times.get(name));
            }
        }
        ordered_times.sort(null);
        Set<String> available_names = new HashSet<String>(times.keySet());
        for (Long time : ordered_times) {
            for (String name : available_names) {
                if (times.get(name) == time) {
                    ordered_players.add(name);
                    available_names.remove(name);
                    break;
                }
            }
        }
        ordered_players.addAll(available_names);


        return ordered_players;
    }

}
