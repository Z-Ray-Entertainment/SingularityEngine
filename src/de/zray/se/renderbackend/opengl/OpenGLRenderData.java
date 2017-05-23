/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.renderbackend.opengl;

/**
 *
 * @author vortex
 */
public class OpenGLRenderData {
    private long vbo;
    
    public void setVBO(long index){
        vbo = index;
    }
    
    public long getVBO(){
        return vbo;
    }
}
