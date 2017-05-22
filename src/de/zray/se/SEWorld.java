/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se;

import de.zray.se.grapics.Camera;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author vortex
 */
public class SEWorld {
    private List<SEActor> actors = new LinkedList<>();
    private List<Camera> views = new LinkedList<>();
    private double delta = 0, timeBeforeAct, fpsUpdate = 0;
    private int currentCamera = -1, fps = 0, countedFrames;
    
    public double getDelta(){
        return delta;
    }
    
    public void updateDelta(){
        delta =  getTimeInSec() - timeBeforeAct;
        timeBeforeAct = getTimeInSec();
        calcFPS(delta);
    }
    
    public void addSEActor(SEActor actor){
        actors.add(actor);
    }
    
    public SEActor getNextActor(){
        return null;
    }
    
    public Camera getCurrentCamera(){
        return views.get(currentCamera);
    }
    
    public void act(){
        actors.forEach((actor) -> {
            actor.getSEAI().act(delta);
        });
        updateDelta();
    }
    
    public double getTimeInSec(){
        return System.nanoTime()*Math.pow(10, -9);
    }
    
    public int getFPS(){
        return fps;
    }
    
    private void calcFPS(double delta){
        fpsUpdate += delta;
        countedFrames++;
        if(fpsUpdate >= 1){
            fps = countedFrames;
            countedFrames = 0;
            fpsUpdate = 0;
        }
    }
}
