/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se;

import de.zray.se.world.World;
import de.zray.se.renderbackend.RenderBackend;
import de.zray.se.utils.TimeTaken;
import java.io.IOException;

/**
 *
 * @author vortex
 */
public class MainThread {
    private static double fpsUpdate = 0;
    private static double delta = 0;//, timeBeforeAct = System.nanoTime();
    private static int fps = 0, countedFrames;
    private boolean firstCycle = true;
    
    private RenderBackend backend;
    private World currentWorld;
    
    public static final double getDeltaInSec(){
        return delta/1000000000.;
    }
    
    public static final double getDeltaInMs(){
        return delta/1000000.;
    }
    
    public static final int getFPS(){
        return fps;
    }
    
    public boolean setRenderBackend(RenderBackend backend){
        if(backend.featureTest()){
            this.backend = backend;
            return true;
        }
        return false;
    }
    
    public void switchWorld(World world){
        currentWorld = world;
        firstCycle = true;
    }
    
    public void loop() throws IOException{
        Thread loop = new Thread(() -> {
            TimeTaken timeTaken;
            while(!backend.closeRequested()){
                timeTaken = new TimeTaken(true);
                if(!backend.isInited()){
                    backend.init();
                }
                if(currentWorld != null){
                    currentWorld.act(getDeltaInSec());
                    currentWorld.optimizeScene();
                }
                if(backend.isReady()){
                    backend.setCurrentWorld(currentWorld);
                    backend.renderWorld(Settings.get().debug.debugMode);
                    
                }
                if(firstCycle){
                    timeTaken = new TimeTaken(true);
                    firstCycle = false;
                }
                delta = timeTaken.endInNano();
            }
            shutdown();
        });
        loop.start();
    }
    
    public void shutdown(){
        if(currentWorld.getAudioWorld() != null){
            currentWorld.getAudioWorld().shutdown();
        }
        backend.shutdown();
        System.exit(0);
    }
    
    public World getCurrentWorld(){
        return currentWorld;
    }
}
