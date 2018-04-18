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
    private final WorldID sewID;
    private final Actor actor;
    
    public ReCacheSceneData(WorldID sewID, Actor actor){
        this.actor = actor;
        this.sewID = sewID;
    }
    
    public final WorldID getID(){
        return sewID;
    }
    
    public final Actor getActor(){
        return actor;
    }
}
