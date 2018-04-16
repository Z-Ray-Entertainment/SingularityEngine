/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.world;

import java.util.UUID;

/**
 *
 * @author vortex
 */
public class SEWorldID {
    public static enum EntityType {TYPE_ACTOR, TYPE_LIGHT};
    
    private UUID uuid;
    private int index;
    private EntityType entType;
    
    public SEWorldID(UUID uuid, int index, EntityType entType){
        this.uuid = uuid;
        this.index = index;
        this.entType = entType;
    }
    
    public UUID getUUID(){
        return uuid;
    }
    
    public int getIndex(){
        return index;
    }
    
    public EntityType getEntityType(){
        return entType;
    }
}
