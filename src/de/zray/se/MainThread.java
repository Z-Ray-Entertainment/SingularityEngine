/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se;

import de.zray.se.world.SEWorld;
import de.zray.se.renderbackend.RenderBackend;
import java.io.IOException;

/**
 *
 * @author vortex
 */
public class MainThread {
    private static double fpsUpdate = 0;
    private static long delta = 0, timeBeforeAct = 0;
    private static int fps = 0, countedFrames;
    
    private RenderBackend backend;
    private SEWorld currentWorld;
    
    private static void updateDelta(){
        delta =  getTimeInMillis() - timeBeforeAct;
        timeBeforeAct = getTimeInMillis();
        calcFPS(delta);
    }
    
    private static void calcFPS(double delta){
        fpsUpdate += delta;
        countedFrames++;
        if(fpsUpdate >= 1000){
            fps = countedFrames;
            countedFrames = 0;
            fpsUpdate = 0;
        }
    }
    
    public static final double getDeltaInSec(){
        return delta/100f;
    }
    
    public static final double getDeltaInMs(){
        return delta;
    }
          
    public static final long getTimeInMillis(){
        return System.currentTimeMillis();
    }
    
    public static final int getFPS(){
        return fps;
    }
    
    public void setRenderBackend(RenderBackend backend){
        this.backend = backend;
    }
    
    public void switchWorld(SEWorld world){
        currentWorld = world;
    }
    
    public void loop() throws IOException{
        Thread loop = new Thread(() -> {
            while(!backend.closeRequested()){
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
                updateDelta();
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
    
    public SEWorld getCurrentWorld(){
        return currentWorld;
    }
}
