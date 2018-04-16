/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.semesh;

import de.zray.se.world.Entity;
import javax.vecmath.Vector3d;

/**
 *
 * @author vortex
 */
public class Oriantation {
    private Vector3d pos = new Vector3d(0, 0, 0);
    private Vector3d rot = new Vector3d(0, 0, 0);
    private Vector3d scl = new Vector3d(0, 0, 0);
    private Entity parent;
    
    public Oriantation(Entity parent){
        initOrientation(parent, 0, 0, 0, 0, 0, 0, 1, 1, 1);
    }
    
    public Oriantation(Entity parent, float posX, float posY, float posZ){
        initOrientation(parent, posX, posY, posZ, 0, 0, 0, 1, 1, 1);
    }
    
    public Oriantation(Entity parent, float posX, float posY, float posZ, float rotX, float rotY, float rotZ){
        initOrientation(parent, posX, posY, posZ, rotX, rotY, rotZ, 1, 1, 1);
    }
    
    public Oriantation(Entity parent, float posX, float posY, float posZ, float rotX, float rotY, float rotZ, float scaleX, float scaleY, float scaleZ){
        initOrientation(parent, posX, posY, posZ, rotX, rotY, rotZ, scaleX, scaleY, scaleZ);
    }
    
    public double[] getPosition(){
        return new double[]{pos.x, pos.y, pos.z};
    }
    
    public Vector3d getPositionVec(){
        return pos;
    }
    
    public void setPosition(double x, double y, double z){
        pos = new Vector3d(x, y, z);
        if(parent != null){
            parent.setRefreshNeeded(true);
        }
    }
    
    public void setRotation(double x, double y, double z){
        rot = new Vector3d(x, y, z);
        if(parent != null){
            parent.setRefreshNeeded(true);
        }
    }
    
    public double[] getRotation(){
        return new double[]{rot.x, rot.y, rot.z};
    }
    
    public Vector3d getRotationVec(){
        return rot;
    }
    
    public void setScale(double x, double y, double z){
        scl = new Vector3d(x, y, z);
        if(parent != null){
            parent.setRefreshNeeded(true);
        }
    }
    
    public double[] getScale(){
        return new double[]{scl.x, scl.y, scl.z};
    }
    
    public Vector3d getScaleVec(){
        return scl;
    }
    
    private void initOrientation(Entity parent, double posX, double posY, double posZ, double rotX, double rotY, double rotZ, double scaleX, double scaleY, double scaleZ){
        pos = new Vector3d(posX, posY, posZ);
        rot = new Vector3d(rotX, rotY, rotZ);
        scl = new Vector3d(scaleX, scaleY, scaleZ);
        this.parent = parent;
    }
    
    public void forceNewParent(Entity parent){
        System.out.println("Ori: forced new Refreshable!");
        this.parent = parent;
    }
}
