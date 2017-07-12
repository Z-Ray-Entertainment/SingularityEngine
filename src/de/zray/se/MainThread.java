/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se;

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
        delta =  getTimeInSec() - timeBeforeAct;
        timeBeforeAct = getTimeInSec();
        calcFPS(delta);
    }
    
    private static void calcFPS(double delta){
        fpsUpdate += delta;
        countedFrames++;
        if(fpsUpdate >= 1){
            fps = countedFrames;
            countedFrames = 0;
            fpsUpdate = 0;
            System.out.println("FPS: "+fps);
        }
    }
    
    public static final double getDelta(){
        return delta;
    }
          
    public static final double getTimeInSec(){
        return System.nanoTime()*Math.pow(10, -9);
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
                    currentWorld.act(getDelta());
                }
                if(backend.isReady()){
                    backend.setCurrentWorld(currentWorld);
                    backend.renderWorld(getDelta());
                    updateDelta();
                }
            }
            shutdown();
        });
        loop.start();
    }
    
    public void shutdown(){
        currentWorld.getAudioWorld().shutdown();
        backend.shutdown();
        System.exit(0);
    }
    
    public SEWorld getCurrentWorld(){
        return currentWorld;
    }
}
