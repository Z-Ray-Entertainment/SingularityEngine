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
    
    public void loop(SEWorld world) throws IOException{
        currentWorld = world;
        if(!backend.isInited()){
            backend.init();
        }
        world.act();
        backend.renderWorld(world);
    }
    
    public void shutdown(){
        backend.shutdown();
        System.exit(0);
    }
    
    public SEWorld getCurrentWorld(){
        return currentWorld;
    }
}
