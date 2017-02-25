/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.grapics;

import de.zray.se.Settings;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author vortex
 */
public class Camera {
    public static enum CameraMode{PERSPECTIVE, ORTHOGRAPHIC};
    public static enum ViewMode{THIRDPERSON, EGO};
    
    private CameraMode camMode = CameraMode.PERSPECTIVE;
    private Vector3f pos = new Vector3f(0, 0, 0), rot = new Vector3f(0, 0, 0);
    private float near = Settings.get().view.nearClip, far = Settings.get().view.farClip, ratio = 1, fov = 50;
    private String camName = "Camera";
    private ViewMode viewMode = ViewMode.THIRDPERSON;
    private boolean rotLocks[] = {false, false, false}, posLocks[] = {false, false, false};
    
    public Camera(){
        calcRatio();
        ratio = ((float) Display.getWidth() / (float) Display.getHeight());
    }
    
    public void setViewMode(ViewMode mode){
        this.viewMode = mode;
    }
    
    public ViewMode getViewMode(){
        return viewMode;
    }
    
    public void setCameraName(String name){
        camName = name;
    }
    
    public String getCameraName(){
        return camName;
    }
    
    public void setPosition(float x, float y, float z){
        pos = new Vector3f(x, y, z);
    }
    
    public void setRotation(float x, float y, float z){
        rot = new Vector3f(x, y, z);
    }
    
    public Vector3f getPosition(){
        return pos;
    }
    
    public Vector3f getRotation(){
        return rot;
    }
    
    public void setPerspectiveRendering(boolean enabled){
        if(enabled){
            camMode = CameraMode.PERSPECTIVE;
        }
        else{
            camMode = CameraMode.ORTHOGRAPHIC;
        }
    }
    
    private float calcRatio(){
        if(Display.wasResized()){
            return ratio = ((float) Display.getWidth() / (float) Display.getHeight());
        }
        return ratio;
    }
    
    public void applyCamera(){
        glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
        switch(camMode){
            case ORTHOGRAPHIC :
                glOrtho(0, Display.getWidth(), Display.getHeight(), 0, near, far);
                break;
            case PERSPECTIVE :
                gluPerspective(fov, calcRatio(), near, far);
                break;
        }
        if(viewMode == ViewMode.THIRDPERSON){
            glTranslated(-pos.x, -pos.y, -pos.z);
            applyRotations();
        }
        else{
            applyRotations();
            glTranslated(-pos.x, -pos.y, -pos.z);
        }
        glMatrixMode(GL_MODELVIEW);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glLoadIdentity();
    }
    
    private void applyRotations(){
        if(!rotLocks[0]){
            glRotated(rot.x, 1, 0, 0);
        }
        if(!rotLocks[1]){
            glRotated(rot.y, 0, 1, 0);
        }
        if(!rotLocks[2]){
            glRotated(rot.z, 0, 0, 1);
        }
        
    }
}
