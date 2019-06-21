/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se;

/**
 *
 * @author vortex
 */
public class EngineSettings {
    private static EngineSettings SETTINGS = new EngineSettings();
    public static enum DebugMode{DEBUG_ON, DEBUG_AND_OBJECTS, DEBUG_OFF};
    
    public static EngineSettings get(){
        return SETTINGS;
    }
    
    public final String engineName = "SingularityEngine", engineVersion = "0.0.8", engineSuffix = "Currendum";
    public final String defaultEngineTitle = engineName+" "+engineVersion+" "+engineSuffix;
    public String logPath = System.getProperty("user.home")+"/.se/logs/", logFile = "se.log";
    public String assetDirectory;
    public String windowTitle = defaultEngineTitle;
    public DebugSettings debug = new DebugSettings();
    public SoundSettings sound = new SoundSettings();
    public ViewSettings view = new ViewSettings();
    public WindowSettings window = new WindowSettings();
    public SceneSettings scene = new SceneSettings();
    
    /**
     * Takes a version string and compares it with the interlnal version String (TODO)
     * @param extVersion
     * Returns true if the interla version ist higher than the extrenal
     * @return 
     */
    public boolean checkVersion(String extVersion){
        return true;
    }
    
    public class SceneSettings{
        public int[] dpSizes = {1000, 100, 10, 1};
    }
    
    public class WindowSettings{
        public int resX = 800, resY = 600;
        public boolean fullscreen = false, displayRezisable = true;
    }
    
    public class ViewSettings{
        public float nearClip = 0.1f, farClip = 1000, fov = 75;
    }
    
    public class SoundSettings{
        public float volume = 1, pitch = 1, sfxVolume = 1, sfxPitch = 1;
    }
    
    public class DebugSettings{
        public DebugMode debugMode = DebugMode.DEBUG_OFF;
        public boolean showDistancePatches = true, showGrid = true,
                showWorldCoordSystem = true, showBoundingBoxes = true,
                showLocalCoordSystems = true, renderOnTop = true;
        public double gridSize = 1000, gridStep = 1;
    }
}
