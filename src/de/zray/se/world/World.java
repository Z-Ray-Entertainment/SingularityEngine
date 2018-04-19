/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.world;

import de.zray.se.ai.SEAI;
import de.zray.se.ai.SEAIWorld;
import de.zray.se.audio.SEAudioWorld;
import de.zray.se.graphics.Camera;
import de.zray.se.graphics.LightSource;
import de.zray.se.graphics.semesh.Mesh;
import de.zray.se.inputmanager.InputManager;
import de.zray.se.inputmanager.KeyMap;
import de.zray.se.physics.SEBulletWorld;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vortex
 */
public abstract class World {
    private SEAIWorld aiWorld;
    private SEBulletWorld bulletWorld;
    private SEAudioWorld audioWorld;
    private List<Camera> views = new ArrayList<>();
    private List<InputManager> inputManages = new ArrayList<>();
    private int currentCamera = -1;
    private List<Mesh> rendableMeshes = new ArrayList<>();
    private DistancePatch master;
    
    public World(){
        master = new DistancePatch(null, -1, new double[]{0,0,0});
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
    
    public final void addEntity(Entity ent){
        master.addEntity(ent);
    }
    
    public final List<Entity> getEntities(){
        List<Entity> collect = new ArrayList<>();
        collect.addAll(master.getEntities());
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
        master.getEntities().stream().filter((ent) -> (ent instanceof Actor)).forEachOrdered((ent) -> {
            if(ent != null){
                SEAI ai = ((Actor) ent).getSEAI();
                if(ai != null){
                    ai.act(delta);
                }
            }
        });

        if(audioWorld != null){
            audioWorld.setALListener(getCurrentCamera().getPosition());
            audioWorld.update(delta);
        }
        
        optimizeScene();
    }
    
    public void optimizeScene(){
        master.refresh();
        if(views != null && currentCamera >= 0 && views.get(currentCamera).propsWhereChanged()){
            collectRendableMeshes();            
        }
    }
    
    private void collectRendableMeshes(){
        rendableMeshes = new ArrayList<>();
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
        return master.getSubPatches();
    }

    public List<Actor> getAllActors() {
        List<Actor> actors = new ArrayList<>();
        master.getEntities().stream().filter((ent) -> (ent instanceof Actor)).forEachOrdered((ent) -> {
            actors.add((Actor) ent);
        });
        return actors;
    }
    
    public List<Actor> getVisibleActors(){
        return master.getVisibleActors(views.get(currentCamera));
    }
    
    public List<LightSource> getAllLights(){
        return null;
    }
}
