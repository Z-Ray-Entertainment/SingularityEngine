/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se;

import de.zray.se.ai.SEAIWorld;
import de.zray.se.audio.SEAudioWorld;
import de.zray.se.graphics.Camera;
import de.zray.se.graphics.LightSource;
import de.zray.se.graphics.semesh.SEMesh;
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
    private int currentCamera = -1;
    private List<SEMesh> rendableMeshes = new LinkedList<>();
    private List<LightSource> lights = new LinkedList<>();
    private List<Integer> emptyLightSlots = new LinkedList<>();
    
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
    
    public int addLightSource(LightSource src){
        if(!emptyLightSlots.isEmpty()){
            int slot = emptyLightSlots.get(0);
            lights.set(emptyLightSlots.get(0), src);
            emptyLightSlots.remove(0);
            return slot;
        }
        lights.add(src);
        return lights.size()-1;
    }
    
    public void removeLight(int index){
        if(index == lights.size()-1){
            lights.remove(index);
        }
        else {
            lights.set(index, null);
            emptyLightSlots.add(index);
        }
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
    
    public final void act(double delta){
        actors.forEach((actor) -> {
            if(actor != null){
                try{
                    actor.getSEAI().act(MainThread.getDeltaInSec());
                }
                catch(NullPointerException e){
                    //Null AI
                }
            }
        });
        if(audioWorld != null){
            audioWorld.setALListener(getCurrentCamera().getPosition());
            audioWorld.update(delta);
        }
        
        optimizeScene();
    }
    
    private void optimizeScene(){
        if(views != null && currentCamera >= 0 && views.get(currentCamera).propsWhereChanged()){
            collectRendableMeshes();            
        }
    }
    
    private void collectRendableMeshes(){
        rendableMeshes = new LinkedList<>();
        for(SEActor actor : actors){
            
        }
    }
    
    public List<SEMesh> getRendableMeshes(){
        return rendableMeshes;
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
