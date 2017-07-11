/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se;

import de.zray.se.ai.SEAIWorld;
import de.zray.se.audio.SEAudioWorld;
import de.zray.se.grapics.Camera;
import de.zray.se.physics.SEBulletWorld;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author vortex
 */
public abstract class SEWorld {
    private SEAIWorld aiWorld;
    private SEBulletWorld bulletWorld;
    private SEAudioWorld audioWorld;
    private List<SEActor> actors = new LinkedList<>();
    private List<Camera> views = new LinkedList<>();
    private double delta = 0, timeBeforeAct, fpsUpdate = 0;
    private int currentCamera = -1, fps = 0, countedFrames;
    
    public final double getDelta(){
        return delta;
    }
    
    public final void updateDelta(){
        delta =  getTimeInSec() - timeBeforeAct;
        timeBeforeAct = getTimeInSec();
        calcFPS(delta);
    }
    
    public final void addSEActor(SEActor actor){
        actors.add(actor);
    }
    
    public final List<SEActor> getActors(){
        return actors;
    }
    
    public final Camera getCurrentCamera(){
        if(currentCamera != -1){
            return views.get(currentCamera);
        }
        return null;
    }
    
    public final void act(){
        actors.forEach((actor) -> {
            if(actor != null){
                try{
                    actor.getSEAI().act(delta);
                }
                catch(NullPointerException e){
                    //Null AI
                }
            }
        });
        updateDelta();
    }
    
    public final double getTimeInSec(){
        return System.nanoTime()*Math.pow(10, -9);
    }
    
    public final int getFPS(){
        return fps;
    }
    
    private final void calcFPS(double delta){
        fpsUpdate += delta;
        countedFrames++;
        if(fpsUpdate >= 1){
            fps = countedFrames;
            countedFrames = 0;
            fpsUpdate = 0;
        }
    }
    
    public final SEAIWorld getAIWorld(){
        return aiWorld;
    }
    
    public final void setAIWorld(SEAIWorld aiWorld){
        this.aiWorld = aiWorld;
    }
    
    public final void setBulletWorld(SEBulletWorld bullet){
        this.bulletWorld = bullet;
    }
    
    public final SEBulletWorld getBulletWorld(){
        return bulletWorld;
    }
    
    public final void setAudioWorld(SEAudioWorld audio){
        this.audioWorld = audio;
    }
    
    public final SEAudioWorld getAudioWorld(){
        return audioWorld;
    }
    
    public abstract void init();
}
