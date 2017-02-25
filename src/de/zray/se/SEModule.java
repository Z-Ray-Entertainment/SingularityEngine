/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se;

/**
 *
 * @author vortex
 */
public abstract class SEModule {
    public SEWorld parrent;
    public SEModule(SEWorld parrent){
        this.parrent = parrent;
    }
    
    public abstract boolean shutdown();
    public abstract boolean update(float delta);
    public abstract boolean cleanUp();
}
