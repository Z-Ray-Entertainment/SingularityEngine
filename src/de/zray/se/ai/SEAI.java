/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.ai;

import de.zray.se.world.SEActor;
import de.zray.se.world.SEWorld;

/**
 *
 * @author Vortex Acherontic
 */
public abstract class SEAI {
    public SEActor parentActor;
    public SEWorld parentWorld;
    
    public SEAI(SEWorld world, SEActor actor, SEAIWorld aiMod){
        this.parentWorld = world;
        this.parentActor = actor;
        aiMod.addAI(this);
    }
    
    public final SEActor getActor(){
        return parentActor;
    }
    
    public void setActor(SEActor actor){
        this.parentActor = actor;
    }
    
    public abstract void act(double delta);
}
