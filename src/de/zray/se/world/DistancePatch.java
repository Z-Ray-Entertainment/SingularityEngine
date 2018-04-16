/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.world;

import de.zray.se.Settings;
import de.zray.se.graphics.LightSource;
import de.zray.se.graphics.semesh.Oriantation;
import de.zray.se.logger.SELogger;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.vecmath.Vector3d;

/**
 *
 * @author vortex
 */
public class DistancePatch implements Refreshable{
    private UUID uuid = UUID.randomUUID();
    private DistancePatch parent;
    private World parentWorld;
    private int level;
    private List<DistancePatch> subPatches = new LinkedList<>();
    private double pos[] = new double[3];
    private List<Entity> ents = new LinkedList<>();
    private List<Integer> freeEnts = new LinkedList<>();
    private boolean refreshNeeded = false;
    
    public DistancePatch(int level, double pos[]){
        this(null, level, pos);
    }
    
    public DistancePatch(DistancePatch parent, int level, double pos[]){
        this.level = level;
        this.parent = parent;
        calcPosition(pos);
    }
    
    @Override
    public void setRefreshNeeded(boolean b) {
        if(b){
            refreshNeeded = true;
            if(parent != null){
                parent.setRefreshNeeded(b);
            }
        }
    }
    
    public void refresh(){
        if(refreshNeeded){
            for(DistancePatch dp : subPatches){
                dp.refresh();
            }
            if(isLowestDistancePatch()){
                for(int i = 0; i < ents.size(); i++){
                    if(ents.get(i) != null){
                        double pos[] = ents.get(i).getPositionArray();
                        if(!isInside(pos[0], pos[1], pos[2])){
                            System.out.println("Actor left DP!");
                            Entity tmp = ents.get(i);
                            removeEntity(tmp.getSEWorldID());
                            sortActor(tmp);
                        }
                    }
                }
            }
            refreshNeeded = false;
        }
    }
    
    public void resortEntity(Entity ent){
        for(DistancePatch dp : subPatches){
            WorldID id = dp.addActor(actor);
            if(id != null){
                return;
            }
        }
        sortActor(actor);
    }
    
    public void sortActor(Actor act){
        System.out.println("Sorting Actor");
        if(parent != null){
            System.out.println("Sending to parent");
            parent.resortEntity(act);
        } else if(parentWorld != null){
            System.out.println("Sending to world");
            parentWorld.addSEActor(act);
        } else {
            SELogger.get().dispatchMsg(this, SELogger.SELogType.ERROR, new String[]{"DistanceOatch without parent!"}, false);
        }
    }
    
    public void setParentWorld(World world){
        this.parentWorld = world;
    }
    
    public WorldID addActor(Actor actor){
        Oriantation ori = actor.getOrientation();
        if(isInside(ori.getPosition()[0], ori.getPosition()[1], ori.getPosition()[2])){
            if(isLowestDistancePatch()){
                return addFreeActor(actor);
            } else {
                for(DistancePatch dp : subPatches){
                    WorldID seid = dp.addActor(actor);
                    if(seid != null){
                        return seid;
                    }
                }
                WorldID tmpID = createAndAddSubPatch(actor);
                if(tmpID == null){
                    SELogger.get().dispatchMsg(this, SELogger.SELogType.ERROR, new String[]{"SEWorldID is null but should not be null! :("}, false);
                }
                return tmpID;
            }
        }
        return null;
    }
    
    private WorldID addFreeActor(Actor actor){
        double pos[] = actor.getOrientation().getPosition();
        if(isInside(pos[0], pos[1], pos[2])){
            if(!freeActors.isEmpty()){
                int slot = freeActors.get(0);
                freeActors.remove(slot);
                actors.set(slot, actor);
                actor.setSEWorldID(new WorldID(uuid, slot));
                actor.setParentDistancePatch(this);
                return actor.getSEWorldID();
            }
            else{
                actors.add(actor);
                actor.setParentDistancePatch(this);
                actor.setSEWorldID(new WorldID(uuid, actors.size()-1));
                return actor.getSEWorldID();
            }
        }
        return null;
    }
    
    public boolean removeEntity(WorldID seWorldID){
        if(uuid.compareTo(seWorldID.getUUID()) == 0){
            int index = seWorldID.getIndex();
            if(index == ents.size()-1){
                ents.remove(index);
            } else{
                ents.set(index, null);
                freeActors.add(index);
            }
        }
        return false;
    }
    
    public WorldID addLightSource(LightSource src){
        Oriantation ori = src.getOrientation();
        if(isInside(ori.getPosition()[0], ori.getPosition()[1], ori.getPosition()[2])){
            if(isLowestDistancePatch()){
                return addFreeLightSource(src);
            } else {
                if(subPatches.isEmpty()){
                    DistancePatch sub = new DistancePatch(this.level+1, ori.getPosition());
                    subPatches.add(sub);
                    return sub.addLightSource(src);
                } else {
                    for(DistancePatch dp : subPatches){
                        WorldID seid = dp.addLightSource(src);
                        if(seid != null) {
                            return seid;
                        }
                    }
                }
            }
        }
        return null;
    }
    
    private WorldID addFreeLightSource(LightSource src){
        double pos[] = src.getOrientation().getPosition();
        if(isInside(pos[0], pos[1], pos[2])){
            if(!freeLights.isEmpty()){
                int slot = freeLights.get(0);
                freeLights.remove(slot);
                lights.set(slot, src);
                return new WorldID(uuid, slot);
            }
            else{
                lights.add(src);
                return new WorldID(uuid, lights.size()-1);
            }
        }
        return null;
    }
    
    public boolean removeLightSource(WorldID seWorldID){
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
    
    public List<Actor> getActorts(){
        if(isLowestDistancePatch()){
            return actors;
        } else {
            List<Actor> tmp = new LinkedList<>();
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
    
    private WorldID createAndAddSubPatch(Actor actor){
        DistancePatch sub = new DistancePatch(this, this.level+1, actor.getOrientation().getPosition());
        subPatches.add(sub);
        WorldID seid = sub.addActor(actor);
        Vector3d subPos = new Vector3d(sub.getPostion());
        System.out.println("New DP at "+subPos.toString()+" for "+actor.getOrientation().getPositionVec().toString());
        return seid;
    }
}