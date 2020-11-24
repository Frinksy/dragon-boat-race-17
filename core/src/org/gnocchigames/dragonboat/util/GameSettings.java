package org.gnocchigames.dragonboat.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * GamesSettings class holds the values about the settings
 * and a way to call/ change them
 */
public class GameSettings{


    private static final String sound_volume = "sound";
    private static final String sound_enabled = "sound.enabled";
    private static final String settings_name = "DBGSettings";

    /**
     * Get the settings
     * @return the settings
     */
    public Preferences getSettings(){
        // sets it so screens cannot directly run or change this class only this class can run it
        return Gdx.app.getPreferences(settings_name);
    }

    /**
     * Get the sound volume
     * @return the sound volume
     */
    public float getSoundVolume(){
        return getSettings().getFloat(sound_volume, 0.5f);
    }
    /**
     * Set the sound volume
     * @param volume the sound volume
     */
    public void setSoundVolume(float volume){
        getSettings().putFloat(sound_volume, volume);
        getSettings().flush();
    }

    /**
     * Is the sound enabled
     * @return true if the sound is enabled, false otherwise
     */
    public boolean getSoundEnabled(){
        return getSettings().getBoolean(sound_enabled, true);
    }

    /**
     * Set if the sound is enabled or not
     * @param is_enabled true if the sound should be enabled, false otherwise
     */
    public void setSoundEnabled(boolean is_enabled){
        getSettings().putBoolean(sound_enabled, is_enabled);
        getSettings().flush();
    }

}