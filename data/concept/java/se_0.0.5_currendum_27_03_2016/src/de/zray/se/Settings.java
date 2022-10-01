/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se;

import java.util.Date;

/**
 *
 * @author vortex
 */
public class Settings {
    private static Settings SETTINGS = new Settings();
    public static enum DebugMode{DEBUG_ON, DEBUG_AND_OBJECTS, DEBUG_OFF};
    
    public static Settings get(){
        return SETTINGS;
    }
    
    public static void set(Settings set){
        SETTINGS = set;
    }
    
    public String title = "SingularityEngine", version = "0.0.5 Currendum";
    public String logfile = System.getProperty("user.home")+"/.se/loggs/SE-Log"+new Date().toString()+".log";
    public DebugMode debugMode = DebugMode.DEBUG_OFF;
    public SoundSettings soundSettings = new SoundSettings();
    public ViewSettings viewSettings = new ViewSettings();
    public WindowSettings winSettings = new WindowSettings();
    
    /**
     * Takes a version string and compares it with the interlnal version String (TODO)
     * @param extVersion
     * Returns true if the interla version ist higher than the extrenal
     * @return 
     */
    public boolean checkVersion(String extVersion){
        String sepWordsExternel[] = extVersion.split(" ");
        String sepWordsInternal[] = version.split(" ");
        
        return true;
    }
    
    public class WindowSettings{
        public int resX = 800, resY = 600;
        public boolean fullscreen = false, displayRezisable = true;
    }
    
    public class ViewSettings{
        public float nearClip = 0.1f, farClip = 100, fov = 75;
    }
    
    public class SoundSettings{
        public float volume = 1, pitch = 1, sfxVolume = 1, sfxPitch = 1;
    }
}
