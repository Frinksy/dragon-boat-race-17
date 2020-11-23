package org.gnocchigames.dragonboat.util;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;

public class TextureStore {
    
    public Map<String, Texture> map;

    public TextureStore () {

        map = new HashMap<String, Texture>();


        // Add boat textures
        String [] colours = {"blue", "brown", "pink", "dark", "green", "grey", "pink", "yellow"};

        for (int i = 0; i < colours.length; i++) {
            for (int j = 0; j < 5; j++) {
                String path = "boats/" + colours[i] + "/frame-" + j + ".png";
                map.put(path, new Texture(path));
            }
        }


        map.put("duck.png", new Texture("duck.png"));
        map.put("tree_log.png", new Texture("tree_log.png"));
        map.put("rock/rock0.png", new Texture("rock/rock0.png"));
        map.put("rock/rock1.png", new Texture("rock/rock1.png"));
        map.put("rock/rock2.png", new Texture("rock/rock2.png"));
        map.put("rock/rock3.png", new Texture("rock/rock3.png"));
        map.put("podium.png", new Texture("podium.png"));
        map.put("buoy.png", new Texture("buoy.png"));
        map.put("water_tile.png", new Texture("water_tile.png"));
        map.put("finish.png", new Texture("finish.png"));
        map.put("game_over.png", new Texture("game_over.png"));

    }


}
