/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se;

import de.zray.se.world.World;
import de.zray.se.renderbackend.RenderBackend;
import java.io.IOException;

/**
 *
 * @author vortex
 */
public class MainThread {
    private static double fpsUpdate = 0;
    private static long delta = 0, timeBeforeAct = System.nanoTime();
    private static int fps = 0, countedFrames;
    private boolean firstCycle = true;
    
    private RenderBackend backend;
    private World currentWorld;
    
    private static void updateDelta(){
        delta =  System.nanoTime() - timeBeforeAct;
        timeBeforeAct = System.nanoTime();
        calcFPS(getDeltaInSec());
    }
    
    private static void calcFPS(double delta){
        fpsUpdate += delta;
        countedFrames++;
        if(fpsUpdate >= 1){
            fps = countedFrames;
            countedFrames = 0;
            fpsUpdate = 0;
            //System.out.println("FPS: "+fps);
        }
    }
    
    public static final double getDeltaInSec(){
        return delta / 1000000000.0;
    }
    
    public static final double getDeltaInMs(){
        return delta;
    }
    
    public static final int getFPS(){
        return fps;
    }
    
    public void setRenderBackend(RenderBackend backend){
        this.backend = backend;
    }
    
    public void switchWorld(World world){
        currentWorld = world;
        firstCycle = true;
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
                if(firstCycle){
                    timeBeforeAct = System.nanoTime();
                    firstCycle = false;
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
    
    public World getCurrentWorld(){
        return currentWorld;
    }
}
