/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.world;

import de.zray.se.graphics.semesh.Oriantation;
import javax.vecmath.Vector3d;

/**
 *
 * @author vortex
 */
public abstract class Entity implements Refreshable{
    private Oriantation orientation;
    private WorldID id;
    
    public Entity(){
        orientation = new Oriantation(this);
    }
    
    public void setPostion(double x, double y, double z){
        orientation.setPosition(x, y, z);
    }
    
    public Vector3d getPositionVector(){
        return orientation.getPositionVec();
    }
    
    public double[] getPositionArray(){
        return orientation.getPosition();
    }

    @Override
    public void setRefreshNeeded(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setWorldID(WorldID id){
        this.id = id;
    }
}
