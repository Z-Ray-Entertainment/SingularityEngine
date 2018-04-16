/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.world;

import de.zray.se.graphics.semesh.Oriantation;
import de.zray.se.logger.SELogger;
import javax.vecmath.Vector3d;

/**
 *
 * @author vortex
 */
public abstract class Entity{
    private Oriantation orientation;
    private WorldID id;
    private boolean refreshNeeded = false;
    private DistancePatch parent;
    
    public Entity(){
        orientation = new Oriantation(this);
    }
    
    public void setPostion(double x, double y, double z){
        orientation.setPosition(x, y, z);
    }
    
    public Vector3d getPositionVector(){
        return orientation.getPositionVec();
    }
    
    public double[] getPositionArray(){
        return orientation.getPosition();
    }

    public void setRefreshNeeded(boolean b) {
        refreshNeeded = b;
        if(parent != null){
            parent.setRefreshNeeded(b);
        }
    }
    
    public void setParentDP(DistancePatch parent){
        this.parent = parent;
        SELogger.get().dispatchMsg("Entity", SELogger.SELogType.INFO, new String[]{"Added Parent: "+id.getUUID()}, false);
    }
    
    public void setWorldID(WorldID id){
        this.id = id;
    }
    
    public WorldID getWorldID(){
        return id;
    }
    
    public boolean isRefreshNedded(){
        return refreshNeeded;
    }
}
