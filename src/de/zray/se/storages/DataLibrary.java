/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.storages;

import de.zray.se.logger.SELogger;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @author vortex
 */
public class DataLibrary {
    private static DataLibrary dataLib;
    
    public static DataLibrary get(){
        if(dataLib == null){
            dataLib = new DataLibrary();
        }
        return dataLib;
    }
    
    private HashMap<String, String> assets;
    private boolean preventLog = false;

    
    public DataLibrary(){
        assets = new HashMap<>();
    }
    
    public void scanAssetDirectory(File path){
        if(path.isDirectory()){
            for(File f : path.listFiles()){
                if(f.isDirectory()){
                    scanAssetDirectory(f);
                } else if(f.isFile()){
                    preventLog = true;
                    if(getAsset(f.getName()) == null){
                        assets.put(f.getName(), f.getAbsolutePath());
                    } else {
                        SELogger.get().dispatchMsg(DataLibrary.class, SELogger.SELogType.WARNING, new String[]{"File already exists: "+f.getName()+" (Skipping)"}, false);
                    }
                    preventLog = false;
                }
            }
        }
    }
    
    public File getAsset(String fileName){
        String absolutePath = assets.get(fileName);
        if(absolutePath != null && !absolutePath.isEmpty()){
            return new File(absolutePath);
        } else {
            if(!preventLog){
                SELogger.get().dispatchMsg(DataLibrary.class, SELogger.SELogType.ERROR, new String[]{"File not found: "+fileName}, false);
            }
            return null;
        }
    }
    
    
    public int getNumberOfKnownAssets(){
        return assets.size();
    }
}
