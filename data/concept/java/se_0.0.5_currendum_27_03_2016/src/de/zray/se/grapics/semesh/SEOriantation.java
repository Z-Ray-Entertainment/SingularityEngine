/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.grapics.semesh;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author vortex
 */
public class SEOriantation {
    private Vector3f pos = new Vector3f(0, 0, 0);
    private Vector3f rot = new Vector3f(0, 0, 0);
    private Vector3f scl = new Vector3f(0, 0, 0);
    
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
    
    public void apply(){
        glTranslatef(pos.x, pos.y, pos.z);
        glRotatef(rot.x, 1, 0, 0);
        glRotatef(rot.y, 0, 1, 0);
        glRotatef(rot.z, 0, 0, 1);
        glScalef(scl.x, scl.y, scl.z);
    }
    
    public float[] getPosition(){
        return new float[]{pos.x, pos.y, pos.z};
    }
    
    public Vector3f getPositionVec(){
        return pos;
    }
    
    public void setPosition(float x, float y, float z){
        pos = new Vector3f(x, y, z);
    }
    
    public void setRotation(float x, float y, float z){
        rot = new Vector3f(x, y, z);
    }
    
    public void setScale(float x, float y, float z){
        scl = new Vector3f(x, y, z);
    }
    
    private void initOrientation(float posX, float posY, float posZ, float rotX, float rotY, float rotZ, float scaleX, float scaleY, float scaleZ){
        pos = new Vector3f(posX, posY, posZ);
        rot = new Vector3f(rotX, rotY, rotZ);
        scl = new Vector3f(scaleX, scaleY, scaleZ);
    }
}
