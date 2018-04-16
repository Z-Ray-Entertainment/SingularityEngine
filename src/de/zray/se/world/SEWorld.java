/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.world;

import de.zray.se.MainThread;
import de.zray.se.ai.SEAIWorld;
import de.zray.se.audio.SEAudioWorld;
import de.zray.se.exceptions.UnknownEntityException;
import de.zray.se.graphics.Camera;
import de.zray.se.graphics.LightSource;
import de.zray.se.graphics.semesh.SEMesh;
import de.zray.se.inputmanager.InputManager;
import de.zray.se.inputmanager.KeyMap;
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
    private List<Camera> views = new LinkedList<>();
    private List<InputManager> inputManages = new LinkedList<>();
    private int currentCamera = -1;
    private List<SEMesh> rendableMeshes = new LinkedList<>();
    private List<DistancePatch> distancePatches = new LinkedList<>();
    
    public final void addInputManager(InputManager manager){
        inputManages.add(manager);
    }
    
    public final void hanldeKeyInputs(int key, KeyMap.MODE mode){
        for(InputManager man : inputManages){
            switch(mode){
                case PRESSED :
                    //System.out.println("PRESSED "+key);
                    man.keyPressed(key);
                    break;
                case RELEASED :
                    //System.out.println("RELEASED "+key);
                    man.keyReleased(key);
                    break;
                case TIPED :
                    //System.out.println("TIPED "+key);
                    man.keyTiped(key);
                    break;
            }
        }
    }
    
    public List<LightSource> getClosestLightsToActiveCamera(int amount){
        List<LightSource> srcs = new LinkedList<>();
        distancePatches.forEach((dp) -> {
            srcs.addAll(dp.getLights());
        });
        return srcs;
    }
    
    public final SEWorldID addEntity(SEEntity entity) throws UnknownEntityException{
        if(distancePatches.isEmpty()){
            DistancePatch dp = new DistancePatch(0, entity.getOrientation().getPosition());
            dp.setParentWorld(this);
            double posAct[] = entity.getOrientation().getPosition();
            distancePatches.add(dp);
            return dp.addEntity(entity);
        } else {
            for(DistancePatch d : distancePatches){
                SEWorldID seWorldID = d.addEntity(entity);
                if(seWorldID != null){
                    return seWorldID;
                }
            }
            DistancePatch dp = new DistancePatch(0, entity.getOrientation().getPosition());
            return dp.addEntity(entity);
        }
    }
    
    public final List<SEActor> getActors(){
        List<SEActor> collect = new LinkedList<>();
        distancePatches.forEach((dp) -> {
            collect.addAll(dp.getActorts());
        });
        return collect;
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
        distancePatches.forEach((DistancePatch dp) -> {
            dp.getActorts().stream().filter((actor) -> (actor != null)).forEachOrdered((actor) -> {
                try{
                    actor.getSEAI().act(MainThread.getDeltaInSec());
                }
                catch(NullPointerException e){
                    //Null AI
                }
            });
        });
        if(audioWorld != null){
            audioWorld.setALListener(getCurrentCamera().getPosition());
            audioWorld.update(delta);
        }
        
        optimizeScene();
    }
    
    public void optimizeScene(){
        collectRefreshData();
        reCacheSceneData();
        if(views != null && currentCamera >= 0){
            collectRendableMeshes();            
        }
    }
    
    private void collectRefreshData(){
        distancePatches.forEach((dp) -> {
            dp.refresh();
        });
        
    }
    
    private void reCacheSceneData(){
        distancePatches.forEach((dp) -> {
            dp.reCache();
        });
    }
    
    private void collectRendableMeshes(){
        rendableMeshes = new LinkedList<>();
        for(DistancePatch dp : distancePatches){
            
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
    
    public List<DistancePatch> getDistancePatched(){
        List<DistancePatch> tmp = new LinkedList<>();
        tmp.addAll(distancePatches);
        distancePatches.forEach((dp) -> {
            tmp.addAll(dp.getSubPatches());
        });
        
        return tmp;
    }
}