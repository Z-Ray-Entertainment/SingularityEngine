/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.ai;

import de.zray.se.world.Actor;
import de.zray.se.world.World;

/**
 *
 * @author Vortex Acherontic
 */
public abstract class SEAI {
    public Actor parentActor;
    public World parentWorld;
    
    public SEAI(World world, Actor actor, SEAIWorld aiMod){
        this.parentWorld = world;
        this.parentActor = actor;
        aiMod.addAI(this);
    }
    
    public final Actor getActor(){
        return parentActor;
    }
    
    public void setActor(Actor actor){
        this.parentActor = actor;
    }
    
    public abstract void act(double delta);
}
