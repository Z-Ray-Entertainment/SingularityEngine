/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.semesh;

import de.zray.se.world.Refreshable;
import javax.vecmath.Vector3d;

/**
 *
 * @author vortex
 */
public class SEOriantation {
    private Vector3d pos = new Vector3d(0, 0, 0);
    private Vector3d rot = new Vector3d(0, 0, 0);
    private Vector3d scl = new Vector3d(0, 0, 0);
    private Refreshable refreshable;
    
    public SEOriantation(Refreshable ref){
        initOrientation(0, 0, 0, 0, 0, 0, 1, 1, 1);
        refreshable = ref;
    }
    
    public SEOriantation(Refreshable ref, float posX, float posY, float posZ){
        initOrientation(posX, posY, posZ, 0, 0, 0, 1, 1, 1);
    }
    
    public SEOriantation(Refreshable ref, float posX, float posY, float posZ, float rotX, float rotY, float rotZ){
        initOrientation(posX, posY, posZ, rotX, rotY, rotZ, 1, 1, 1);
    }
    
    public SEOriantation(Refreshable ref, float posX, float posY, float posZ, float rotX, float rotY, float rotZ, float scaleX, float scaleY, float scaleZ){
        initOrientation(posX, posY, posZ, rotX, rotY, rotZ, scaleX, scaleY, scaleZ);
    }
    
    public double[] getPosition(){
        return new double[]{pos.x, pos.y, pos.z};
    }
    
    public Vector3d getPositionVec(){
        return pos;
    }
    
    public void setPosition(double x, double y, double z){
        pos = new Vector3d(x, y, z);
        if(refreshable != null){
            refreshable.setRefreshNeeded(true);
        }
    }
    
    public void setRotation(double x, double y, double z){
        rot = new Vector3d(x, y, z);
        if(refreshable != null){
            refreshable.setRefreshNeeded(true);
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
        if(refreshable != null){
            refreshable.setRefreshNeeded(true);
        }
    }
    
    public double[] getScale(){
        return new double[]{scl.x, scl.y, scl.z};
    }
    
    public Vector3d getScaleVec(){
        return scl;
    }
    
    private void initOrientation(double posX, double posY, double posZ, double rotX, double rotY, double rotZ, double scaleX, double scaleY, double scaleZ){
        pos = new Vector3d(posX, posY, posZ);
        rot = new Vector3d(rotX, rotY, rotZ);
        scl = new Vector3d(scaleX, scaleY, scaleZ);
    }
    
    public void forceNewRefreshable(Refreshable ref){
        System.out.println("Ori: forced new Refreshable!");
        this.refreshable = ref;
    }
}
