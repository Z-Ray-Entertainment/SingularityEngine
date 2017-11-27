/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.physics;

import de.zray.se.world.SEActor;

/**
 *
 * @author Vortex Acherontic
 */
public class SEBulletObject {
    private SEActor parrentActor;
    
    public SEBulletObject(SEActor parrent){
        this.parrentActor = parrent;
    }
}
