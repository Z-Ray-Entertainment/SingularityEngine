/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.grapics.semesh;

import javax.vecmath.Vector3d;

/**
 *
 * @author vortex
 */
public class SEOriantation {
    private Vector3d pos = new Vector3d(0, 0, 0), memPos;
    private Vector3d rot = new Vector3d(0, 0, 0), memRot;
    private Vector3d scl = new Vector3d(0, 0, 0), memScl;
    private boolean wasChanged = false;
    
    public SEOriantation(){
        initOrientation(0, 0, 0, 0, 0, 0, 1, 1, 1);
    }
    
    public SEOriantation(float posX, float posY, float posZ){
        initOrientation(posX, posY, posZ, 0, 0, 0, 1, 1, 1);
    }
    
    public SEOriantation(float posX, float posY, float posZ, float rotX, float rotY, float rotZ){
        initOrientation(posX, posY, posZ, rotX, rotY, rotZ, 1, 1, 1);
    }
    
    public SEOriantation(float posX, float posY, float posZ, float rotX, float rotY, float rotZ, float scaleX, float scaleY, float scaleZ){
        initOrientation(posX, posY, posZ, rotX, rotY, rotZ, scaleX, scaleY, scaleZ);
    }
    
    public boolean wasChanged(){
        return wasChanged;
    }
    
    public void apply(){
        chaged();
    }
    
    private void chaged(){
        if(pos.x != memPos.x){
            wasChanged = true;
        }
        else if(pos.y != memPos.y){
            wasChanged = true;
        }
        else if(pos.z != memPos.z){
            wasChanged = true;
        }
        else if(rot.x != memRot.x){
            wasChanged = true;
        }
        else if(rot.y != rot.y){
            wasChanged = true;
        }
        else if(rot.z != memRot.z){
            wasChanged = true;
        }
        else if(scl.x != memScl.x){
            wasChanged = true;
        }
        else if(scl.y != memScl.y){
            wasChanged = true;
        }
        else if(scl.z != memScl.z){
            wasChanged = false;
        }
        wasChanged = false;
        
        memPos.x = pos.x;
        memPos.y = pos.y;
        memPos.z = pos.z;
        
        memRot.x = rot.x;
        memRot.y = rot.y;
        memRot.z = rot.z;
        
        memScl.x = scl.x;
        memScl.y = scl.y;
        memScl.z = scl.z;
    }
    
    public double[] getPosition(){
        return new double[]{pos.x, pos.y, pos.z};
    }
    
    public Vector3d getPositionVec(){
        return pos;
    }
    
    public void setPosition(double x, double y, double z){
        pos = new Vector3d(x, y, z);
    }
    
    public void setRotation(double x, double y, double z){
        rot = new Vector3d(x, y, z);
    }
    
    public void setScale(double x, double y, double z){
        scl = new Vector3d(x, y, z);
    }
    
    private void initOrientation(double posX, double posY, double posZ, double rotX, double rotY, double rotZ, double scaleX, double scaleY, double scaleZ){
        pos = new Vector3d(posX, posY, posZ);
        rot = new Vector3d(rotX, rotY, rotZ);
        scl = new Vector3d(scaleX, scaleY, scaleZ);
        memPos = pos;
        memRot = rot;
        memScl = scl;
    }
}
