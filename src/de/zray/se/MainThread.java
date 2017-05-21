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
    private RenderBackend backend;
    private SEWorld currentWorld;
    
    public void setRenderBackend(RenderBackend backend){
        this.backend = backend;
    }
    
    public void switchWorld(SEWorld world){
        this.currentWorld = world;
    }
    
    public void loop() throws IOException{
        if(!backend.isInited()){
            backend.init();
        }
        if(currentWorld != null){
            currentWorld.act();
        }
        backend.renderWorld(currentWorld.getDeltaInMS());
    }
    
    public void shutdown(){
        backend.shutdown();
        System.exit(0);
    }
    
    public SEWorld getCurrentWorld(){
        return currentWorld;
    }
}
