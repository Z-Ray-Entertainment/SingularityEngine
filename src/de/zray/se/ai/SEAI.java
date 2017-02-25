/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.ai;

import de.zray.se.SEActor;
import de.zray.se.SEWorld;

/**
 *
 * @author Vortex Acherontic
 */
public abstract class SEAI {
    public SEActor parrentActor;
    public SEWorld parrentWorld;
    
    public SEAI(SEWorld world, SEActor actor, SEAIModule aiMod){
        this.parrentWorld = world;
        this.parrentActor = actor;
        aiMod.addAI(this);
    }
    
    public abstract void act(float delta);
}
