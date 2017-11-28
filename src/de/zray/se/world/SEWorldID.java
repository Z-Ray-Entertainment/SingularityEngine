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
    private UUID uuid;
    private int index;
    
    public SEWorldID(UUID uuid, int index){
        this.uuid = uuid;
        this.index = index;
    }
    
    public UUID getUUID(){
        return uuid;
    }
    
    public int getIndex(){
        return index;
    }
}
