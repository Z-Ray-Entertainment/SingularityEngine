/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.renderbackend.opengl;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;

/**
 *
 * @author vortex
 */
public class OpenGLRenderData {
    private int vboID, displayList;
    public int vboSize;
    
    public void setDisplayList(int id){
        displayList = id;
    }
    
    public int getDisplayList(){
        return displayList;
    }
    
    public void setVBOID(int index){
        vboID = index;
    }
    
    public int getVBOID(){
        return vboID;
    }
    
    public void setVBOSize(int size){
        vboSize = size;
    }
    
    public int getVBOSize(){
        return vboSize;
    }
    
    public void destroy(){
        glDeleteBuffers(vboID);
    }
}
