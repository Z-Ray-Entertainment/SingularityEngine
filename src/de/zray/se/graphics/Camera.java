/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics;

import de.zray.se.EngineSettings;
import org.joml.Vector3d;
import org.joml.Vector3f;

/**
 *
 * @author vortex
 */
public class Camera {

    public static enum CameraMode {
        PERSPECTIVE, ORTHOGRAPHIC
    };

    public static enum ViewMode {
        THIRDPERSON, EGO
    };

    private CameraMode camMode = CameraMode.PERSPECTIVE;
    private Vector3f pos = new Vector3f(0, 1, 0), rot = new Vector3f(0, 0, 0);
    private float near = EngineSettings.get().view.nearClip, far = EngineSettings.get().view.farClip, ratio = 1, fov = 50;
    private String camName = "Camera";
    private ViewMode viewMode = ViewMode.THIRDPERSON;
    private boolean rotLocks[] = {false, false, false}, posLocks[] = {false, false, false};
    private Vector3d lookAt = new Vector3d();
    private boolean propsChanged = false;

    public boolean propsWhereChanged(){
        return propsChanged;
    }
    
    public void elizabeth(){
        propsChanged = true;
    }
    
    public void setLookAt(Vector3d lookAt){
        this.lookAt = lookAt;
    }
    
    public Vector3d getLookAt(){
        return lookAt;
    }
    
    public void setClips(float near, float far){
        this.near = near;
        this.far = far;
    }
    
    public float getNear(){
        return near;
    }
    
    public float getFar(){
        return far;
    }
    
    public float getFOV(){
        return fov;
    }
    
    public CameraMode getCameraMode(){
        return camMode;
    }
    
    public void setViewMode(ViewMode mode) {
        this.viewMode = mode;
    }

    public ViewMode getViewMode() {
        return viewMode;
    }

    public void setCameraName(String name) {
        camName = name;
    }

    public String getCameraName() {
        return camName;
    }

    public void setPosition(float x, float y, float z) {
        pos = new Vector3f(x, y, z);
    }

    public void setRotation(float x, float y, float z) {
        rot = new Vector3f(x, y, z);
    }

    public Vector3f getPosition() {
        return pos;
    }

    public Vector3f getRotation() {
        return rot;
    }

    public void setPerspectiveRendering(boolean enabled) {
        if (enabled) {
            camMode = CameraMode.PERSPECTIVE;
        } else {
            camMode = CameraMode.ORTHOGRAPHIC;
        }
    }
    
    public boolean[] getRotationLocks(){
        return rotLocks;
    }
    
    public double getDistance(Vector3d point){
        Vector3d distVec = new Vector3d(pos.x - point.x, pos.y - point.y, pos.z - point.z);
        return distVec.length();
    }
    
    /**
     * Determines if a point is in the cameras view range
     * TODO: Determine if the point is in the cameras frustum
     * @param point
     * @return 
     */
    public boolean isVisable(Vector3d point){
        double distance = getDistance(point);
        
        if(distance < far || distance > near){
            return inFrusutm(point);
        }       
        return false;
    }
    
    private boolean inFrusutm(Vector3d point){
        return true;
    }
}
