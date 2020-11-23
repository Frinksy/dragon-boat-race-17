package org.gnocchigames.dragonboat.util;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

/**
 * SkinStore
 * Centralised storage for Skin objects
 */
public class SkinStore implements Disposable {

    private Map<String, Skin> map;


    public SkinStore() {
        map = new HashMap<String, Skin>();
        map.put("clean-crispy/clean-crispy-ui.json", new Skin(Gdx.files.internal("clean-crispy/clean-crispy-ui.json")));
    }
    
    public Skin get(String path) {
        return map.get(path);
    }

    public void dispose() {
        for (Skin skin : map.values()) {
            skin.dispose();
        }
    }

}
