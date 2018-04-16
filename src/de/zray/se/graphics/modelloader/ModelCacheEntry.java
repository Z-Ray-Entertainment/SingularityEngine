/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.modelloader;

/**
 *
 * @author vortex
 */
public class ModelCacheEntry {
    private int meshDataID = -1;
    private String file = "";
    
    public ModelCacheEntry(String file, int meshDataID){
        this.file = file;
        this.meshDataID = meshDataID;
    }
    
    public int compareFile(String requestedFile){
        if(file != null && !file.isEmpty()){
            return file.equals(requestedFile) ? meshDataID : -1;
        }
        return -1;
    }
}
