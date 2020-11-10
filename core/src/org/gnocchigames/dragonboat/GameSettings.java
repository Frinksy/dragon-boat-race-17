package org.gnocchigames.dragonboat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Games Settings class holds the values about the settings
 * and a way to call/ change them
 */

public class GameSettings{


    private static final String sound_volume = "sound";
    private static final String sound_enabled = "sound.enabled";
    private static final String settings_name = "DBGSettings";

    protected Preferences getSettings(){
        // sets it so screens cannot directly run or change this class only this class can run it
        return Gdx.app.getPreferences(settings_name);
    }

    // getters and setters for the different settings
    public float getSoundVolume(){
        return getSettings().getFloat(sound_volume, 0.5f);
    }

    public void setSoundVolume(float volume){
        getSettings().putFloat(sound_volume, volume);
        getSettings().flush();
    }

    public boolean getSoundEnabled(){
        return getSettings().getBoolean(sound_enabled, true);
    }

    public void setSoundEnabled(boolean is_enabled){
        getSettings().putBoolean(sound_enabled, is_enabled);
        getSettings().flush();
    }

}