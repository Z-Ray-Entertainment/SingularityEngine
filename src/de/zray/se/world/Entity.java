/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.world;

import de.zray.se.graphics.semesh.Oriantation;
import de.zray.se.logger.SELogger;

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
    
    public void setParent(DistancePatch parent){
        this.parent = parent;
    }
    
    public Oriantation getOrientation(){
        return orientation;
    }
    
    public void setOrientation(Oriantation oriantation){
        this.orientation = oriantation;
        this.orientation.forceNewParent(this);
    }
    
    public void setRefreshNeeded(boolean b) {
        if(b){
            refreshNeeded = b;
            if(parent != null){
                parent.setRefreshNeeded(b);
            }
        }
    }
    
    public void setWorldID(WorldID id){
        this.id = id;
        //System.out.println("[Entity]: New WorldID "+id.getUUID()+" "+id.getIndex());
    }
    
    public WorldID getWorldID(){
        return id;
    }
    
    public boolean isRefreshNedded(){
        return refreshNeeded;
    }
}
