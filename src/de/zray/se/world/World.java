/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.world;

import de.zray.se.ai.SEAIWorld;
import de.zray.se.audio.SEAudioWorld;
import de.zray.se.graphics.Camera;
import de.zray.se.graphics.semesh.Mesh;
import de.zray.se.inputmanager.InputManager;
import de.zray.se.inputmanager.KeyMap;
import de.zray.se.logger.SELogger;
import de.zray.se.physics.SEBulletWorld;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author vortex
 */
public abstract class World {
    private SEAIWorld aiWorld;
    private SEBulletWorld bulletWorld;
    private SEAudioWorld audioWorld;
    private List<Camera> views = new LinkedList<>();
    private List<InputManager> inputManages = new LinkedList<>();
    private int currentCamera = -1;
    private List<Mesh> rendableMeshes = new LinkedList<>();
    private List<DistancePatch> distancePatches = new LinkedList<>();
    
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
    
    public final void addEntity(Entity ent){
        SELogger.get().dispatchMsg("World", SELogger.SELogType.INFO, new String[]{"Adding new Entity to World"}, false);
        if(distancePatches.isEmpty()){
            DistancePatch dp = new DistancePatch(this, 0, ent.getOrientation().getPosition());
            distancePatches.add(dp);
            dp.addEntity(ent);
        } else {
            for(DistancePatch d : distancePatches){
                d.addEntity(ent);
            }
            DistancePatch dp = new DistancePatch(this, 0, ent.getOrientation().getPosition());
            dp.addEntity(ent);
        }
    }
    
    public final List<Entity> getEntities(){
        List<Entity> collect = new LinkedList<>();
        distancePatches.forEach((dp) -> {
            collect.addAll(dp.getEntities());
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
            dp.getEntities().stream().filter((ent) -> (ent instanceof Actor)).forEachOrdered((ent) -> {
                ((Actor) ent).getSEAI().act(delta);
            });
        });
        if(audioWorld != null){
            audioWorld.setALListener(getCurrentCamera().getPosition());
            audioWorld.update(delta);
        }
        
        optimizeScene();
    }
    
    public void optimizeScene(){
        distancePatches.forEach((dp) -> {
            dp.refresh();
        });
        if(views != null && currentCamera >= 0 && views.get(currentCamera).propsWhereChanged()){
            collectRendableMeshes();            
        }
    }
    
    private void collectRendableMeshes(){
        rendableMeshes = new LinkedList<>();
        for(DistancePatch dp : distancePatches){
            
        }
    }
    
    public List<Mesh> getRendableMeshes(){
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

    public Iterable<Actor> getActors() {
        List<Actor> actors = new LinkedList<>();
        distancePatches.forEach((dp) -> {
            dp.getEntities().stream().filter((ent) -> (ent instanceof Actor)).forEachOrdered((ent) -> {
                actors.add((Actor) ent);
            });
        });
        return actors;
    }
}
