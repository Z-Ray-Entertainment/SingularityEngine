/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.physics;

import de.zray.se.world.Actor;

/**
 *
 * @author Vortex Acherontic
 */
public class SEBulletObject {
    private Actor parrentActor;
    
    public SEBulletObject(Actor parrent){
        this.parrentActor = parrent;
    }
}
