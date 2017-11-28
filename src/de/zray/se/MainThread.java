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
    private static double delta = 0, timeBeforeAct = 0, fpsUpdate = 0;
    private static int fps = 0, countedFrames;
    
    private RenderBackend backend;
    private SEWorld currentWorld;
    
    private static void updateDelta(){
        delta =  getTimeInMs() - timeBeforeAct;
        timeBeforeAct = getTimeInMs();
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
        return delta/100;
    }
    
    public static final double getDeltaInMs(){
        return delta;
    }
          
    public static final double getTimeInMs(){
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
        currentWorld.setRenderBackend(backend);
    }
    
    public void loop() throws IOException{
        Thread loop = new Thread(() -> {
            while(!backend.closeRequested()){
                if(!backend.isInited()){
                    backend.init();
                }
                if(currentWorld != null){
                    currentWorld.act(getDeltaInSec());
                }
                if(backend.isReady()){
                    backend.setCurrentWorld(currentWorld);
                    switch(Settings.get().debug.debugMode){
                        case DEBUG_AND_OBJECTS :
                            backend.renderWorld();
                            backend.renderDebug();
                            break;
                        case DEBUG_OFF :
                            backend.renderWorld();
                            break;
                        case DEBUG_ON :
                            backend.renderDebug();
                            break;
                    }
                    
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
