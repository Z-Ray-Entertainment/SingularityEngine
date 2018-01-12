/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.world;

import de.zray.se.Settings;
import de.zray.se.exceptions.UnknownEntityException;
import de.zray.se.graphics.LightSource;
import de.zray.se.graphics.semesh.SEOriantation;
import de.zray.se.logger.SELogger;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.vecmath.Vector3d;

/**
 *
 * @author vortex
 */
public class DistancePatch implements Refreshable{
    private UUID uuid = UUID.randomUUID();
    private DistancePatch parentDP;
    private SEWorld parentWorld;
    private int level;
    private List<DistancePatch> subPatches = new LinkedList<>();
    private double pos[] = new double[3];
    private List<SEActor> actors = new LinkedList<>();
    private List<LightSource> lights = new LinkedList<>();
    private List<Integer> freeActors = new LinkedList<>(), freeLights = new LinkedList<>();
    private boolean refreshNeeded = false;
    
    private List<ReCacheSceneData> reCacheData = new LinkedList<>();
    
    public DistancePatch(int level, double pos[]){
        this(null, level, pos);
    }
    
    public DistancePatch(DistancePatch parent, int level, double pos[]){
        this.level = level;
        this.parentDP = parent;
        calcPosition(pos);
    }
    
    @Override
    public void setRefreshNeeded(boolean b) {
        if(b){
            refreshNeeded = true;
            if(parentDP != null){
                parentDP.setRefreshNeeded(b);
            }
        }
    }
    
    /**
     * Collects all Actors which left this or its subDPs and adds them
     * to te reCahceList
     */
    public void refresh(){
        if(refreshNeeded){
            subPatches.forEach((dp) -> {
                dp.refresh();
            });
            if(isLowestDistancePatch()){
                for(int i = 0; i < actors.size(); i++){
                    if(actors.get(i) != null){
                        double posActor[] = actors.get(i).getOrientation().getPosition();
                        if(!isInside(posActor[0], posActor[1], posActor[2])){
                            //System.out.println("Actor left DP!");
                            SEActor tmp = actors.get(i);
                            reCacheData.add(new ReCacheSceneData(tmp.getSEWorldID(), tmp));
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Removes all Actors from the correspondening DP and afterwards at them to the scene
     * to avoid DP changes while optimizing and coausing data missmatches
     */
    public void reCache(){
        if(reCacheData != null && !reCacheData.isEmpty()){
            if(subPatches.isEmpty() || subPatches == null){
                if(!reCacheData.isEmpty()){
                    reCacheData.stream().map((rcd) -> {
                        removeEntity(rcd.getID());
                        return rcd;
                    }).forEachOrdered((rcd) -> {
                        try {
                            sortActor(rcd.getActor());
                        } catch (UnknownEntityException ex) {
                            SELogger.get().dispatchMsg(this, SELogger.SELogType.ERROR, new String[]{ex.getMessage()}, refreshNeeded);
                            Logger.getLogger(DistancePatch.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            }
            refreshNeeded = false;
            reCacheData.clear();
        }
    }
    
    public void resortActor(SEActor actor) throws UnknownEntityException{
        for(DistancePatch dp : subPatches){
            SEWorldID id = dp.addEntity(actor);
            if(id != null){
                return;
            }
        }
        sortActor(actor);
    }
    
    public void sortActor(SEActor act) throws UnknownEntityException{
        System.out.println("Sorting Actor");
        if(parentDP != null){
            System.out.println("Sending to parent");
            parentDP.resortActor(act);
        } else if(parentWorld != null){
            System.out.println("Sending to world");
            parentWorld.addEntity(act);
        } else {
            SELogger.get().dispatchMsg(this, SELogger.SELogType.ERROR, new String[]{"DistanceOatch without parent!"}, false);
        }
    }
    
    public void setParentWorld(SEWorld world){
        this.parentWorld = world;
    }
    
    private SEWorldID addFreeActor(SEActor actor){
        double pos[] = actor.getOrientation().getPosition();
        if(isInside(pos[0], pos[1], pos[2])){
            if(!freeActors.isEmpty()){
                int slot = freeActors.get(0);
                freeActors.remove(slot);
                actors.set(slot, actor);
                actor.setSEWorldID(new SEWorldID(uuid, slot, SEWorldID.EntityType.TYPE_ACTOR));
                actor.setParent(this);
                return actor.getSEWorldID();
            }
            else{
                actors.add(actor);
                actor.setParent(this);
                actor.setSEWorldID(new SEWorldID(uuid, actors.size()-1, SEWorldID.EntityType.TYPE_ACTOR));
                return actor.getSEWorldID();
            }
        }
        return null;
    }
        
    public SEWorldID addEntity(SEEntity ent) throws UnknownEntityException{
        SEOriantation ori = ent.getOrientation();
        if(isInside(ori.getPosition()[0], ori.getPosition()[1], ori.getPosition()[2])){
            if(isLowestDistancePatch()){
                if(ent instanceof LightSource){
                    return addFreeLightSource((LightSource) ent);
                } else if(ent instanceof SEActor){
                    return addFreeActor((SEActor) ent);
                }
                throw new UnknownEntityException(ent);
            } else {
                if(subPatches.isEmpty()){
                    DistancePatch sub = new DistancePatch(this.level+1, ori.getPosition());
                    subPatches.add(sub);
                    return sub.addEntity(ent);
                } else {
                    for(DistancePatch dp : subPatches){
                        SEWorldID seid = dp.addEntity(ent);
                        if(seid != null) {
                            return seid;
                        }
                    }
                }
            }
        }
        return null;
    }
    
    public boolean removeEntity(SEWorldID seWorldID){
        if(uuid.compareTo(seWorldID.getUUID()) == 0){
            int index = seWorldID.getIndex();
            switch(seWorldID.getEntityType()){
                case TYPE_ACTOR :
                    if(uuid.compareTo(seWorldID.getUUID()) == 0){
                        if(index == actors.size()-1){
                            actors.remove(index);
                        } else{
                            actors.set(index, null);
                            freeActors.add(index);
                        }
                    }
                    return false;
                case TYPE_LIGHT :
                    if(index == lights.size()-1){
                        lights.remove(index);
                        return true;
                    } else{
                        lights.set(index, null);
                        freeLights.add(index);
                        return true;
                    }
            }
            
        }
        return false;
    }
    
    private SEWorldID addFreeLightSource(LightSource src){
        double pos[] = src.getOrientation().getPosition();
        if(isInside(pos[0], pos[1], pos[2])){
            if(!freeLights.isEmpty()){
                int slot = freeLights.get(0);
                freeLights.remove(slot);
                lights.set(slot, src);
                return new SEWorldID(uuid, slot, SEWorldID.EntityType.TYPE_LIGHT);
            }
            else{
                lights.add(src);
                return new SEWorldID(uuid, lights.size()-1, SEWorldID.EntityType.TYPE_LIGHT);
            }
        }
        return null;
    }
    
    public boolean removeLightSource(SEWorldID seWorldID){
        if(uuid.compareTo(seWorldID.getUUID()) == 0){
            int index = seWorldID.getIndex();
            if(index == lights.size()-1){
                lights.remove(index);
                return true;
            } else{
                lights.set(index, null);
                freeLights.add(index);
                return true;
            }
        }
        return false;
    }
    
    private void calcPosition(double pos[]){
        int edgeLength = Settings.get().scene.dpSizes[level];
        this.pos[0] = (Math.round((pos[0]/edgeLength))*edgeLength);
        this.pos[1] = (Math.round((pos[1]/edgeLength))*edgeLength);
        this.pos[2] = (Math.round((pos[2]/edgeLength))*edgeLength);
    }
    
    public boolean isInside(double x, double y, double z){
        int edgeLength = Settings.get().scene.dpSizes[level];
        
        if(!isBetween(pos[0], pos[0]+edgeLength/2, x)){
            if(!isBetween(pos[0]-edgeLength/2, pos[0], x)){
                return false;
            }
        }
        if(!isBetween(pos[1], pos[1]+edgeLength/2, y)){
            if(!isBetween(pos[1]-edgeLength/2, pos[1], y)){
                return false;
            }
        }
        if(!isBetween(pos[2], pos[2]+edgeLength/2, z)){
            if(!isBetween(pos[2]-edgeLength/2, pos[2], z)){
                return false;
            }
        }
        return true;
    }
    
    private boolean isBetween(double start, double end, double val){
        return val <= end && val >= start;
    }
    
    public List<SEActor> getActorts(){
        if(isLowestDistancePatch()){
            return actors;
        } else {
            List<SEActor> tmp = new LinkedList<>();
            subPatches.forEach((dp) -> {
                tmp.addAll(dp.getActorts());
            });
            return tmp;
        }
    }
    
    public List<LightSource> getLights(){
        if(isLowestDistancePatch()){
            return lights;
        } else {
            List<LightSource> tmp = new LinkedList<>();
            subPatches.forEach((dp) -> {
                tmp.addAll(dp.getLights());
            });
            return tmp;
        }
    }
    
    private boolean isLowestDistancePatch(){
        return level == Settings.get().scene.dpSizes.length-1;
    }
    
    public List<DistancePatch> getSubPatches(){
        List<DistancePatch> subs = new LinkedList<>();
        subs.addAll(subPatches);
        for(DistancePatch subsub : subPatches){
            subs.addAll(subsub.getSubPatches());
        }
        return subs;
    }
    
    public double[] getPostion(){
        return pos;
    }
    
    public int getEdgeLength(){
        return Settings.get().scene.dpSizes[level];
    }
    
    public int getLevel(){
        return level;
    }
    
    private SEWorldID createAndAddSubPatch(SEActor actor) throws UnknownEntityException{
        DistancePatch sub = new DistancePatch(this, this.level+1, actor.getOrientation().getPosition());
        subPatches.add(sub);
        SEWorldID seid = sub.addEntity(actor);
        Vector3d subPos = new Vector3d(sub.getPostion());
        System.out.println("New DP at "+subPos.toString()+" for "+actor.getOrientation().getPositionVec().toString());
        return seid;
    }
}