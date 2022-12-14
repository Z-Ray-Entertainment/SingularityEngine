/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.world;

import de.zray.se.EngineSettings;
import de.zray.se.graphics.Camera;
import de.zray.se.graphics.LightSource;
import de.zray.se.logger.SELogger;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author vortex
 */
public class DistancePatch {
    private UUID uuid = UUID.randomUUID();
    private DistancePatch parent;
    private int level;
    private ArrayList<DistancePatch> subPatches = new ArrayList<>();
    private ArrayList<DistancePatch> subPatchesToBeAdded = new ArrayList<>();
    private double pos[] = new double[3];
    private ArrayList<Entity> ents = new ArrayList<>();
    private ArrayList<Integer> freeEnts = new ArrayList<>(), distancePatchesToBeDeleted = new ArrayList<>();
    private boolean refreshNeeded = false;
    
    public DistancePatch(DistancePatch parent, int level, double pos[]){
        this.parent = parent;
        initDistancePatch(level, pos);
    }
    
    private void initDistancePatch(int level, double pos[]){
        this.level = level;
        calcPosition(pos);
    }
    
    public void setRefreshNeeded(boolean b) {
        if(b){
            refreshNeeded = true;
            if(parent != null){
                parent.setRefreshNeeded(b);
            }
        }
    }
    
    public boolean addEntity(Entity ent){
        double pos[] = ent.getOrientation().getPosition();
        if(isInside(pos[0], pos[1], pos[2])){
            //System.out.println("[DP "+level+"]: Entity is inside");
            if(isLowestDistancePatch()){
                //System.out.println("[DP "+level+"]: Lowest DP");
                if(!addEntityToFreeSlot(ent)){
                    ents.add(ent);
                    ent.setWorldID(new WorldID(uuid, ents.size()-1));
                    ent.setParent(this);
                    return true;
                }
            } else {
                if(subPatches.isEmpty()){
                    //System.out.println("[DP "+level+"]: No sub patches, creating new");
                    createAndAddtoNewSubPatch(ent);
                    subPatches.addAll(subPatchesToBeAdded);
                    return true;
                } 
                else {
                    for(DistancePatch sub : subPatches){
                        if(sub.isInside(pos[0], pos[1], pos[2])){
                            //System.out.println("[DP "+level+"]: Found matching sub patch, adding Entity");
                            sub.addEntity(ent);
                            return true;
                        }
                    }
                    return createAndAddtoNewSubPatch(ent);
                }
            }
        } else if(parent != null){
            //System.out.println("[DP "+level+"]: Entity is not inside, sending to parent DP");
            return parent.addEntity(ent);
        } else {
            SELogger.get().dispatchMsg("DistancePatch", SELogger.SELogType.ERROR, new String[]{"Unable to sort Entiy in DisntacePatch level:"+level}, true);
        }
        return false;
    }
    
    private boolean addEntityToFreeSlot(Entity ent){
        if(freeEnts.size() > 0){
            ent.setWorldID(new WorldID(uuid, freeEnts.get(0)));
            ent.setParent(this);
            ents.set(freeEnts.get(0), ent);
            freeEnts.remove(0);
            return true;
        }
        return false;
    }
    
    public ArrayList<Entity> getEntities(){
        if(!isLowestDistancePatch()){
            ArrayList<Entity> allEnts = new ArrayList<>();
            subPatches.forEach((sub) -> {
                allEnts.addAll(sub.getEntities());
            });
            return allEnts;
        } else {
            return ents;
        }
    }
    
    public boolean removeEntity(WorldID id){
        if(isLowestDistancePatch()){
            if(id.getUUID().compareTo(uuid) == 0){
                int index = id.getIndex();
                if(index == ents.size()-1){
                    ents.remove(index);
                    return true;
                } else {
                    freeEnts.add(index);
                    ents.set(index, null);
                    return true;
                }
            }
        } else {
            for(DistancePatch sub : subPatches){
                if(sub.removeEntity(id)){
                    return true;
                }
            }
        }
        return false;
    }
    
    public void refresh(){
        if(refreshNeeded){
            if(isLowestDistancePatch()){
                for(int i = 0; i < ents.size(); i++){
                    if(ents.get(i) != null && ents.get(i).isRefreshNedded()){
                        double pos[] = ents.get(i).getOrientation().getPosition();
                        if(!isInside(pos[0], pos[1], pos[2])){
                            //System.out.println("[DP "+level+"]: Entity left dp");
                            Entity tmp = ents.get(i);
                            removeEntity(tmp.getWorldID());
                            parent.addEntity(tmp);
                        }
                    }
                }
            }
            for(int i = 0; i < subPatches.size(); i++){
                DistancePatch dp = subPatches.get(i);
                dp.refresh();
                if(dp.isEmpty()){
                    //System.out.println("[DP "+level+"]: Found empty DP index: "+i);
                    distancePatchesToBeDeleted.add(i);
                }
            }
            if(subPatchesToBeAdded != null || !subPatchesToBeAdded.isEmpty()){
                subPatches.addAll(subPatchesToBeAdded);
                //System.out.println("[DP "+level+"]: Added "+subPatchesToBeAdded.size()+" new sub patches");
                subPatchesToBeAdded.clear();
            }
            for(int i = distancePatchesToBeDeleted.size()-1; i >= 0; i--){
                subPatches.remove(distancePatchesToBeDeleted.get(i).intValue());
            }
            distancePatchesToBeDeleted.clear();
            refreshNeeded = false;
        }
    }
    
    private boolean createAndAddtoNewSubPatch(Entity ent){
        //System.out.println("[DP "+level+"]: Building new sub patch");
        DistancePatch sub = new DistancePatch(this, level+1, ent.getOrientation().getPosition());
        subPatchesToBeAdded.add(sub);
        return sub.addEntity(ent);
    }
    
    private void calcPosition(double pos[]){
        if(level > -1){
            int edgeLength = EngineSettings.get().scene.dpSizes[level];
            this.pos[0] = (Math.round((pos[0]/edgeLength))*edgeLength);
            this.pos[1] = (Math.round((pos[1]/edgeLength))*edgeLength);
            this.pos[2] = (Math.round((pos[2]/edgeLength))*edgeLength);
        }
        /*System.out.println("[DP "+level+"]: Pos: "+this.pos[0]+" "+this.pos[1]+" "+this.pos[2]);
        System.out.println("=> for pos: "+pos[0]+" "+pos[1]+" "+pos[2]);
        System.out.println("=> edgeLenght: "+edgeLength);*/
    }
    
    public boolean isInside(double x, double y, double z){
        if(level > -1){
            double edgeLength = EngineSettings.get().scene.dpSizes[level];
        
            if(!isBetween(pos[0], pos[0]+edgeLength/2., x)){
                if(!isBetween(pos[0]-edgeLength/2., pos[0], x)){
                    return false;
                }
            }
            if(!isBetween(pos[1], pos[1]+edgeLength/2., y)){
                if(!isBetween(pos[1]-edgeLength/2., pos[1], y)){
                    return false;
                }
            }
            if(!isBetween(pos[2], pos[2]+edgeLength/2., z)){
                if(!isBetween(pos[2]-edgeLength/2., pos[2], z)){
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean isBetween(double start, double end, double val){
        return val <= end && val >= start;
    }
    
    private boolean isLowestDistancePatch(){
        if(level > -1){
            return level == EngineSettings.get().scene.dpSizes.length-1;
        }
        return false;
    }
    
    public ArrayList<DistancePatch> getSubPatches(){
        ArrayList<DistancePatch> subs = new ArrayList<>();
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
        return EngineSettings.get().scene.dpSizes[level];
    }
    
    public int getLevel(){
        return level;
    }
    
    public boolean isEmpty(){
        if(isLowestDistancePatch()){
            return ents.isEmpty();
        } else {
            for(DistancePatch sub : subPatches){
                if(!sub.isEmpty()){
                    return false;
                }
            }
            return true;
        }
    }
    
    public ArrayList<Actor> getVisibleActors(Camera curCam){
        ArrayList<Entity> visibleEnts = getVisibleEntities(curCam);
        ArrayList<Actor> visibleActors = new ArrayList<>();
        for(Entity ent : visibleEnts){
            if(ent instanceof Actor){
                visibleActors.add((Actor) ent);
            }
        }
        return visibleActors;
    }
    
    public ArrayList<LightSource> getVisibleLights(Camera curCam){
        ArrayList<Entity> visibleEnts = getVisibleEntities(curCam);
        ArrayList<LightSource> visibleLights = new ArrayList<>();
        for(Entity ent : visibleEnts){
            if(ent instanceof LightSource){
                visibleLights.add((LightSource) ent);
            }
        }
        return visibleLights;
    }
    
    public ArrayList<Entity> getVisibleEntities(Camera curCam){
        return getEntities();
    }
}