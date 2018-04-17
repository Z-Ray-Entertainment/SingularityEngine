/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.world;

/**
 *
 * @author vortex
 */
public class ReCacheSceneData {
    private final SEWorldID sewID;
    private final SEActor actor;
    
    public ReCacheSceneData(SEWorldID sewID, SEActor actor){
        this.actor = actor;
        this.sewID = sewID;
    }
    
    public final SEWorldID getID(){
        return sewID;
    }
    
    public final SEActor getActor(){
        return actor;
    }
}
