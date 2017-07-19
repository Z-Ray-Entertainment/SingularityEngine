/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.renderbackend.opengl;

import static org.lwjgl.opengl.GL11.glDeleteLists;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;

/**
 *
 * @author vortex
 */
public class OpenGLRenderData {
    private int rDataCacheID = -1;
    private int displayList = -1, displayListWired = -1;
    private int diffID = -1, specID = -1, dispID = -1, paraID = -1, bumpID = -1;
    
    public void setRenderDataCacheID(int cacheID){
        this.rDataCacheID = cacheID;
    }
    
    public int getRenderDataCacheID(){
        return rDataCacheID;
    }
    
    public void setDiffuseTextureID(int id){
        diffID = id;
    }
    
    public int getDiffuseTextureID(){
        return diffID;
    }
    
    public void setDisplayList(int id){
        displayList = id;
    }
    
    public int getDisplayList(){
        return displayList;
    }
    
    public void setDisplayListWired(int id){
        displayListWired = id;
    }
    
    public int getDisplayListWired(){
        return displayListWired;
    }
    
    public void destroy(RenderDataCacheEntry vboCache){
        glDeleteBuffers(vboCache.vboID);
        glDeleteBuffers(vboCache.vboIDWired);
        glDeleteLists(displayList, 1);
        glDeleteLists(displayListWired, 1);
    }
}
