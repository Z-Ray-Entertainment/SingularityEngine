/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.world;

import de.zray.se.Settings;
import de.zray.se.graphics.LightSource;
import de.zray.se.graphics.semesh.SEOriantation;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author vortex
 */
public class DistancePatch {
    private UUID uuid = UUID.randomUUID();
    private DistancePatch parent;
    private int level;
    private List<DistancePatch> subPatches = new LinkedList<>();
    private double pos[] = new double[3];
    private List<SEActor> actors = new LinkedList<>();
    private List<LightSource> lights = new LinkedList<>();
    private List<Integer> freeActors = new LinkedList<>(), freeLights = new LinkedList<>();
    
    public DistancePatch(int level, double pos[]){
        this(null, level, pos);
    }
    
    public DistancePatch(DistancePatch parent, int level, double pos[]){
        this.level = level;
        this.parent = parent;
        calcPosition(pos);
    }
    
    public SEWorldID addActor(SEActor actor){
        SEOriantation ori = actor.getOrientation();
        if(isInside(ori.getPosition()[0], ori.getPosition()[1], ori.getPosition()[2])){
            if(isLowestDistancePatch()){
                System.out.println("I'm the lowest with: "+level);
                return addFreeActor(actor);
            } else {
                if(subPatches.isEmpty()){
                    DistancePatch sub = new DistancePatch(this.level+1, ori.getPosition());
                    subPatches.add(sub);
                    return sub.addActor(actor);
                } else {
                    for(DistancePatch dp : subPatches){
                        SEWorldID seid = dp.addActor(actor);
                        if(seid != null){
                            return seid;
                        }
                    }
                    DistancePatch sub = new DistancePatch(this.level+1, ori.getPosition());
                    subPatches.add(sub);
                    return sub.addActor(actor);
                }
            }
        }
        return null;
    }
    
    private SEWorldID addFreeActor(SEActor actor){
        double pos[] = actor.getOrientation().getPosition();
        if(isInside(pos[0], pos[1], pos[2])){
            if(!freeActors.isEmpty()){
                int slot = freeActors.get(0);
                freeActors.remove(slot);
                actors.set(slot, actor);
                System.out.println("Added new Actor!");
                return new SEWorldID(uuid, slot);
            }
            else{
                actors.add(actor);
                System.out.println("Added new Actor!");
                return new SEWorldID(uuid, actors.size()-1);
            }
        }
        return null;
    }
    
    public boolean removeActor(SEWorldID seWorldID){
        if(uuid.compareTo(seWorldID.getUUID()) == 0){
            int index = seWorldID.getIndex();
            if(index == actors.size()-1){
                actors.remove(index);
            } else{
                actors.set(index, null);
                freeActors.add(index);
            }
        }
        return false;
    }
    
    public SEWorldID addLightSource(LightSource src){
        SEOriantation ori = src.getOrientation();
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
                        SEWorldID seid = dp.addLightSource(src);
                        if(seid != null) {
                            return seid;
                        }
                    }
                }
            }
        }
        return null;
    }
    
    private SEWorldID addFreeLightSource(LightSource src){
        double pos[] = src.getOrientation().getPosition();
        if(isInside(pos[0], pos[1], pos[2])){
            if(!freeLights.isEmpty()){
                int slot = freeLights.get(0);
                freeLights.remove(slot);
                lights.set(slot, src);
                return new SEWorldID(uuid, slot);
            }
            else{
                lights.add(src);
                return new SEWorldID(uuid, lights.size()-1);
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
        this.pos[0] = (Math.round((pos[0]/edgeLength))*edgeLength/2);
        this.pos[1] = (Math.round((pos[1]/edgeLength))*edgeLength/2);
        this.pos[2] = (Math.round((pos[2]/edgeLength))*edgeLength/2);
    }
    
    public boolean isInside(double x, double y, double z){
        int edgeLength = Settings.get().scene.dpSizes[level];
        
        if(!isBetween(pos[0], pos[0]+edgeLength/2, x)){
            if(!isBetween(pos[0]-edgeLength/2, pos[0], x)){
                return false;
            }
        } else if(!isBetween(pos[1], pos[1]+edgeLength/2, y)){
            if(!isBetween(pos[1]-edgeLength/2, pos[1], y)){
                return false;
            }
        } else if(!isBetween(pos[2], pos[2]+edgeLength/2, z)){
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
}
