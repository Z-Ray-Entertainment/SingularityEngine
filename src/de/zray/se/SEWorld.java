/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se;

import de.zray.se.ai.SEAIWorld;
import de.zray.se.audio.SEAudioWorld;
import de.zray.se.grapics.Camera;
import de.zray.se.inputmanager.InputManager;
import de.zray.se.inputmanager.KeyMap;
import de.zray.se.physics.SEBulletWorld;
import de.zray.se.renderbackend.RenderBackend;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author vortex
 */
public abstract class SEWorld {
    private RenderBackend backend;
    private SEAIWorld aiWorld;
    private SEBulletWorld bulletWorld;
    private SEAudioWorld audioWorld;
    private List<SEActor> actors = new LinkedList<>();
    private List<Camera> views = new LinkedList<>();
    private List<InputManager> inputManages = new LinkedList<>();
    private double delta = 0, timeBeforeAct, fpsUpdate = 0;
    private int currentCamera = -1, fps = 0, countedFrames;
    
    public void setRenderBackend(RenderBackend backend){
        this.backend = backend;
    }
    
    public RenderBackend getRenderBackend(){
        return backend;
    }
    
    public final void addInputManager(InputManager manager){
        inputManages.add(manager);
    }
    
    public final void hanldeKeyInputs(int key, KeyMap.MODE mode){
        for(InputManager man : inputManages){
            switch(mode){
                case PRESSED :
                    man.keyPressed(key);
                    break;
                case RELEASED :
                    man.keyReleased(key);
                    break;
                case TIPED :
                    man.keyTiped(key);
                    break;
            }
        }
    }
    
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
    
    public void setActiveCamera(int cam){
        currentCamera = cam;
    }
    
    public int addCamera(Camera cam){
        this.views.add(cam);
        return this.views.size()-1;
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
